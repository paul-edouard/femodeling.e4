package de.femodeling.e4.server.service;

import de.femodeling.e4.server.service.remote.LockRemoteServiceIF;
import de.femodeling.e4.server.service.remote.MessageRemoteServiceIF;
import de.femodeling.e4.server.service.remote.ProjectRemoteServiceIF;
import de.femodeling.e4.server.service.remote.SessionServiceIF;
import de.femodeling.e4.server.service.remote.UserRemoteServiceIF;


public interface RemoteService {
	
	public void init(String applicationContextLocation);
	public void setSessionId(String sessionid);
	public void setUserId(String userid);
	
	
	public ProjectRemoteServiceIF getProjectService();
	public UserRemoteServiceIF getUserService();
	public SessionServiceIF getSessionService();
	public LockRemoteServiceIF getLockService();
	public MessageRemoteServiceIF getMessageService();
	
	public void startMessageService();
	public void stopMessageService();
	

}
