package de.femodeling.e4.client.service.internal;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.core.AssemblyClientServiceIF;
import de.femodeling.e4.client.service.core.ConnectionClientServiceIF;
import de.femodeling.e4.client.service.core.LockClientServiceIF;
import de.femodeling.e4.client.service.core.PartClientServiceIF;
import de.femodeling.e4.client.service.core.ProjectClientServiceIF;
import de.femodeling.e4.client.service.core.UserClientServiceIF;
import de.femodeling.e4.server.service.RemoteService;


public class ClientService implements IClientService {
	
	private static Logger logger = Logger.getLogger(ClientService.class);
	
	
    private RemoteService remoteService;
	
    private ClientSession session;
	
	
	private ProjectClientServiceIF projectClientService;
	private PartClientServiceIF partClientService;
	private AssemblyClientServiceIF assemblyClientService;
	private ConnectionClientServiceIF connectionClientService;
	private LockClientServiceIF lockClientService;
	private UserClientServiceIF userClientService;
	
	
	
	public ClientService() {
		super();
		logger.info("Client Service Started!");
		
	}

	public void init(RemoteService remoteService, ClientSession session){
		this.remoteService = remoteService;
		this.session = session;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.IClientService#getProjectClientService()
	 */
	@Override
	public ProjectClientServiceIF getProjectClientService() {
		if(projectClientService==null)
			projectClientService=new ProjectClientService(remoteService);
		return projectClientService;
	}

	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.IClientService#getPartClientService()
	 */
	@Override
	public PartClientServiceIF getPartClientService() {
		if(partClientService==null)
			partClientService=new PartClientService(remoteService);
		return partClientService;
	}

	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.IClientService#getAssemblyClientService()
	 */
	@Override
	public AssemblyClientServiceIF getAssemblyClientService() {
		if(assemblyClientService==null)
			assemblyClientService=new AssemblyClientService(remoteService);
		return assemblyClientService;
	}

	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.IClientService#getConnectionClientService()
	 */
	@Override
	public ConnectionClientServiceIF getConnectionClientService() {
		if(connectionClientService==null)
			connectionClientService=new ConnectionClientService(remoteService);
		return connectionClientService;
	}

	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.IClientService#getLockClientService()
	 */
	@Override
	public LockClientServiceIF getLockClientService() {
		if(lockClientService==null)
			lockClientService=new LockClientService(remoteService,session);
		return lockClientService;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.IClientService#getUserClientService()
	 */
	@Override
	public UserClientServiceIF getUserClientService(){
		if(userClientService==null)
			userClientService=new UserClientService(remoteService);
		return userClientService;
	}
	

}
