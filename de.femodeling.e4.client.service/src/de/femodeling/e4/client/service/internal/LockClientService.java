package de.femodeling.e4.client.service.internal;

import java.util.HashMap;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.service.core.LockClientServiceIF;
import de.femodeling.e4.client.service.internal.transform.LockTransformService;
import de.femodeling.e4.model.core.lockable.LockableEntity;
import de.femodeling.e4.server.service.RemoteService;


public class LockClientService implements LockClientServiceIF {
	
	
	private static Logger logger = Logger.getLogger(LockClientService.class);
	
	RemoteService remoteService;
	
	ClientSession session;
	
	public LockClientService(RemoteService remoteService, ClientSession session) {
		super();
		this.remoteService = remoteService;
		this.session = session;
	}


	@Override
	public String lockEntity(LockableEntity entity) {
		
		//logger.info("Trying to lock entity:"+entity.getLockableId());
		
		LockableEntity e=LockTransformService.transform(entity);
		
		
		LockableEntity r=remoteService.
				getLockService().lockEntity(e);
		
		if(r!=null){
			return r.getSessionId();
		}
		
		entity.setSessionId(session.getSessionId());
		
		return "";
	}

	
	@Override
	public boolean unlockEntity(LockableEntity entity) {
		//logger.info("Trying to unlock entity:"+entity.getLockableId());
		
		LockableEntity e=LockTransformService.transform(entity);
		
		if(remoteService.
				getLockService().unlockEntity(e)){
			entity.setSessionId("");
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public HashMap<String, String> getAllLockedEntities(){
		logger.info("Get all locked entities:");
		
		return remoteService.
				getLockService().getAllLockedEntities();
	}

}
