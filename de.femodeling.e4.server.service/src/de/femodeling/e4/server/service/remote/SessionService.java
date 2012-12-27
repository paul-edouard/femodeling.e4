package de.femodeling.e4.server.service.remote;

import java.util.Calendar;

import org.apache.log4j.Logger;

import de.femodeling.e4.internal.exception.SessionExpiredException;
import de.femodeling.e4.model.core.Session;

public class SessionService implements SessionServiceIF {
	
	static final long serialVersionUID=1L;
	
	public static final int SESSION_EXPIRATION = 120;

	/** The logger instance */
	private static final Logger logger = Logger.getLogger(SessionService.class);
	
	
	public SessionService(){
		//BasicConfigurator.configure();
		logger.info("------->Session Service started");
	}
	

	@Override
	public void keepAlive(String sessionId)  {
		//logger.info("processing keep alive for session <" + sessionId + ">");
		Session session = SessionProvider.INSTANCE.getSessionFromId(sessionId);
		if (session != null) {
			Calendar now = Calendar.getInstance();
			Calendar expireDate = Calendar.getInstance();
			expireDate.setTime(session.getLastAccess());
			expireDate.add(Calendar.SECOND, SESSION_EXPIRATION);
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
				//logger.info(" * expiration updated");
				session.setLastAccess(now.getTime());
				
				/*try{Thread.sleep(4000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}*/
				//getSessionDAO().saveSession(sessionImpl);
				return;
			}
		}
		throw new SessionExpiredException("Session <" + sessionId
				+ "> expired.");

	}

}
