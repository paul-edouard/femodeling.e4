package de.femodeling.e4.client.model;

import org.apache.log4j.Logger;
import org.eclipse.e4.core.di.annotations.Creatable;

import de.femodeling.e4.model.core.ConnectionDetails;



@Creatable
public class  ClientSession {

	
	private static Logger logger = Logger.getLogger(ClientSession.class);

	private String name;
	private String server;
	private String sessionId;
	private UserClientImpl user;
	private ConnectionDetails connectionDetails;

	
	public ClientSession() {
		//logger.info("Client Session created!!!");
	}

	public ConnectionDetails getConnectionDetails() {
		return connectionDetails;
	}

	public void setConnectionDetails(ConnectionDetails details) {
		this.connectionDetails = details;
		this.server=details.getServer();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	

	public void setSessionDescription(String name, String server) {
		this.name = name;
		this.server = server;
	}

	public String getName() {
		return name;
	}

	public String getServer() {
		return server;
	}

	public UserClientImpl getUser() {
		return user;
	}

	public void setUser(UserClientImpl user) {
		this.user = user;
	}

	/***********************************
	 * * PROJECT * *
	 ***********************************/

	/*
	 * public ProjectClientImpl getRoot() {
	 * 
	 * if(rootProject==null){
	 * 
	 * DataProviderCacheAdapter dataProvider = (DataProviderCacheAdapter)
	 * Registry.getRegistry()
	 * .lookupDataProvider(ProjectProvider.DATA_PROVIDER_TYPE);
	 * 
	 * ProjectClientImpl p=(ProjectClientImpl) dataProvider.getData(new
	 * UUIDKey(ProjectProvider.DATA_ROOT)); rootProject=p;
	 * fillProjectCache(dataProvider,p);
	 * 
	 * }
	 * 
	 * return rootProject; }
	 */
	/*
	 * private void fillProjectCache(DataProviderCacheAdapter
	 * dataProvider,ProjectClientImpl p){
	 * 
	 * for(ProjectClientImpl c:p.getChilds()){ UUIDKey key=new
	 * UUIDKey(c.getLockableId()); dataProvider.flushCacheForUpdate(key, c);
	 * fillProjectCache(dataProvider,c);
	 * 
	 * }
	 * 
	 * }
	 */

}
