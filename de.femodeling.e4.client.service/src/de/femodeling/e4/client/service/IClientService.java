package de.femodeling.e4.client.service;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.service.core.AssemblyClientServiceIF;
import de.femodeling.e4.client.service.core.ConnectionClientServiceIF;
import de.femodeling.e4.client.service.core.LockClientServiceIF;
import de.femodeling.e4.client.service.core.PartClientServiceIF;
import de.femodeling.e4.client.service.core.ProjectClientServiceIF;
import de.femodeling.e4.client.service.core.UserClientServiceIF;
import de.femodeling.e4.server.service.RemoteService;


public interface IClientService {

	public abstract ProjectClientServiceIF getProjectClientService();

	public abstract PartClientServiceIF getPartClientService();

	public abstract AssemblyClientServiceIF getAssemblyClientService();

	public abstract ConnectionClientServiceIF getConnectionClientService();

	public abstract LockClientServiceIF getLockClientService();

	public abstract UserClientServiceIF getUserClientService();
	
	public abstract void init(RemoteService remoteService, ClientSession session);

}