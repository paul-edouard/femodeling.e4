package de.femodeling.e4.server.service.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.dto.LockableEntityDTO;
import de.femodeling.e4.server.internal.context.ServerContextProvider;
import de.femodeling.e4.server.internal.model.LockableEntityServerImpl;
import de.femodeling.e4.server.service.dao.LockableEntityDAOIF;
import de.femodeling.e4.server.service.dao.LockableEntityDAOImpl;
import de.femodeling.e4.server.service.dao.RegisterServerDAOService;
import de.femodeling.e4.server.service.transform.LockTransformService;




public class LockRemoteService implements LockRemoteServiceIF {
	
	static final long serialVersionUID=1L;
	public static final String LOCKED_ENTITIES_LIST_KEY="LockedEntitiesList";
	private static Logger logger = Logger.getLogger(LockRemoteService.class);
	
	
	//private LockableEntityDAOImpl lockableEntityDAO;
	
	
	public LockRemoteService(){
		//BasicConfigurator.configure();
		logger.info("------->Lock Service started");
	}
	
	
	public void setConfigFiles(final Map<String, String> configFiles) {
		
		
		for(String key:configFiles.keySet()){
			
			if(key.equals(LOCKED_ENTITIES_LIST_KEY)){
				if(configFiles.get(key)!=null ){
					LockableEntityDAOIF lockableEntityDAO=new LockableEntityDAOImpl(configFiles.get(key));
					RegisterServerDAOService.INSTANCE.setLockableEntityDAO(lockableEntityDAO);
					//logger.info("Lock DAO Init!!!!!!!!!!!!!!!!!");
				}
				else{
					logger.warn("Cannot resolve of the User File List location");
				}
				
			}
		}
		
	}
	
	
	synchronized public LockableEntityDTO lockEntity( LockableEntityDTO ent){
		
		logger.info("Lock started from session:"+ServerContextProvider.getServerContext().getSessionId());
		LockableEntityDAOIF lockableEntityDAO=RegisterServerDAOService.INSTANCE.getLockableEntityDAO();
		
		String s_id=ServerContextProvider.getServerContext().getSessionId();
		LockableEntityServerImpl e=LockTransformService.transform(ent);
		e.setSessionId(s_id);
		LockableEntityServerImpl l=lockableEntityDAO.isEntityLocked(e);
		
		
		//the entity is already locked
		if(l!=null){
			if(s_id.equals(l.getSessionId()))return null;
			else return LockTransformService.transform(l);
		}
		
		//tries to locked the entity
		if(!lockableEntityDAO.lockEntity(e)){
			return ent;
		}
		else{
			//Send a message: the entity is locked!
			RegisterServerDAOService.INSTANCE.getMessageDAO().addUpdateMessage(e);
			return null;
		}
		//return LockableEntityServerProvider.INSTANCE.lockEntity(entityId, sessionId);
		
	}
	
	
	synchronized public  boolean unlockEntity(final LockableEntityDTO ent){
		
		logger.info("unlock started from session:"+ServerContextProvider.getServerContext().getSessionId());
		LockableEntityDAOIF lockableEntityDAO=RegisterServerDAOService.INSTANCE.getLockableEntityDAO();
		
		
		String s_id=ServerContextProvider.getServerContext().getSessionId();
		LockableEntityServerImpl e=LockTransformService.transform(ent);
		e.setSessionId(s_id);
		LockableEntityServerImpl l=lockableEntityDAO.isEntityLocked(e);
		
		//the entity is already locked
		if(l!=null){
			if(s_id.equals(l.getSessionId())){
				if(lockableEntityDAO.unlockEntity(e)){
					e.setSessionId(null);
					//ServerDAORegister.INSTANCE.getMessageDAO().addMessage(s_id,e,Message.Type.UPDATE);
					RegisterServerDAOService.INSTANCE.getMessageDAO().addUpdateMessage(e);
					return true;
				}
				else{
					return false;
				}
				
			}
			else return false;
		}
		return false;
		
		
		
		//return LockableEntityServerProvider.INSTANCE.unlockEntity(entityId,sessionId);
		
	}
	
	
	synchronized public HashMap<String, String> getAllLockedEntities(){
		LockableEntityDAOIF lockableEntityDAO=RegisterServerDAOService.INSTANCE.getLockableEntityDAO();
		
		Set<LockableEntityServerImpl> s=  lockableEntityDAO.getAllLockedEntities();
		HashMap<String, String> lockedEntitiesMap=new HashMap<String, String>();
		
		for (LockableEntityServerImpl e : s) {
			lockedEntitiesMap.put(e.getLockableId(), e.getSessionId());
		}
		
		
		return lockedEntitiesMap;
		
	}
	

}
