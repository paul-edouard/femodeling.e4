package de.femodeling.e4.server.internal;

import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.core.Session;
import de.femodeling.e4.server.internal.dao.RegisterServerDAOService;
public enum SessionProvider {
	
	INSTANCE;
	
	private HashMap<String,Session> sessions=null;
	
	private static Logger logger = Logger.getLogger(SessionProvider.class);
	
	
	//private static final int SESSION_EXPIRATION = 240;
	private ExpiredSessionRemover messageRemover;
	
	private SessionProvider(){
		sessions=new HashMap<String,Session>();
		messageRemover=new ExpiredSessionRemover();
		messageRemover.start();
	}
	
	public HashMap<String,Session> getAllSessions(){
		return sessions;
	}

	public Session getSessionFromId(String key){
		return sessions.get(key);
	}
	
	public synchronized void addSession(Session s){
		
		removeExpiredSession();
		
		//logger.info("Session added: "+s.getSessionId());
		sessions.put(s.getSessionId(), s);
		
	}
	
	private synchronized void removeExpiredSession(){
		
		//remove the old sessions
		//logger.info("Removing expired session!");
		
		Object[] keyArray=sessions.keySet().toArray();
				
		for(int i=0;i<keyArray.length;i++){
			if(!isSessionAlive((String)keyArray[i])){
				
				//logger.info("Removing session old Session: "+(String)keyArray[i]);
				//unlock all the entities lock with the expired session
				RegisterServerDAOService.INSTANCE.getLockableEntityDAO().unlockAllEntitiesFromSession((String)keyArray[i]);
				
				//LockableEntityServerProvider.INSTANCE.unlockAllEntitiesFromSessionId((String)keyArray[i]);
				sessions.remove((String)keyArray[i]);
				
			}
			else{
				//logger.info("Session is still alive: "+(String)keyArray[i]);
			}
		}
		
	}
	
	public Boolean isSessionAlive(String sessionId) {
		//logger.info("processing is alive for session <" + sessionId + ">");
		Session session = sessions.get(sessionId);
		if (session != null) {
			Calendar now = Calendar.getInstance();
			Calendar expireDate = Calendar.getInstance();
			expireDate.setTime(session.getLastAccess());
			expireDate.add(Calendar.SECOND, SessionService.SESSION_EXPIRATION);
			/*
			logger.info(" * date is <"
					+ DateFormat.getDateTimeInstance(DateFormat.LONG,
							DateFormat.MEDIUM).format(now.getTime()) + ">");
			logger.info(" * expire date is <"
					+ DateFormat.getDateTimeInstance(DateFormat.LONG,
							DateFormat.MEDIUM).format(expireDate.getTime())
					+ ">");
			*/
			if (expireDate.after(now)) {
				//logger.info(" * valid");
				return true;
			}
			else {
				//logger.info(" * expired");
				//unlock all the entities lock with the expired session
				RegisterServerDAOService.INSTANCE.getLockableEntityDAO().unlockAllEntitiesFromSession(sessionId);
				//LockableEntityServerProvider.INSTANCE.unlockAllEntitiesFromSessionId(sessionId);
				return false;
			}
		}
		//logger.info(" * unknown session id");
		return Boolean.FALSE;
	}
	
	
	
	/**
	 * Delete the old Messages of the list
	 * 
	 * @author Paul
	 *
	 */
	class ExpiredSessionRemover extends Thread
	{
	   
		 
		 
		 
		 
	   public void run()
	   {
	       
		   boolean deleteSessions=true;
		   
		   logger.info("ExpiredSessionRemover Started");
		   
		   while(deleteSessions){
	    	   
			   try{
				  // this.wait((int)MESSAGE_EXPIRATION/2);
				   sleep((int)SessionService.SESSION_EXPIRATION*1000);
			   }
			   catch(InterruptedException e){
				   //logger.info("InterruptedException: "+e.getMessage());
				   deleteSessions=false;
				   continue;
			   }
			   
	    	   
			   removeExpiredSession();
	    	  
	    	   
	       }
		   
		   
	   }
	}
	
	
}
