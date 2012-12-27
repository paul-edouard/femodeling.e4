package de.femodeling.e4.server.service;

import de.femodeling.e4.server.service.remote.LockRemoteServiceIF;
import de.femodeling.e4.server.service.remote.MessageRemoteServiceIF;
import de.femodeling.e4.server.service.remote.ProjectRemoteServiceIF;
import de.femodeling.e4.server.service.remote.SessionServiceIF;
import de.femodeling.e4.server.service.remote.UserRemoteServiceIF;


public interface RemoteService {
	
	public void init(String applicationContextLocation);
	
	public ProjectRemoteServiceIF getProjectRemoteService();
	public UserRemoteServiceIF getUserService();
	public SessionServiceIF getSessionService();
	public LockRemoteServiceIF getLockService();
	public MessageRemoteServiceIF getMessageRemoteService();

}
