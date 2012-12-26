package de.femodeling.e4.client.model;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.core.ConnectionDetails;




public enum ClientSession {

	INSTANCE;

	private static Logger logger = Logger.getLogger(ClientSession.class);

	private String name;
	private String server;
	private String sessionId;
	private UserClientImpl user;
	private ConnectionDetails connectionDetails;

	private ClientSession() {

	}

	public ConnectionDetails getConnectionDetails() {
		return connectionDetails;
	}

	public void setConnectionDetails(ConnectionDetails details) {
		this.connectionDetails = details;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void keepAlive() {
		logger.info("Session:" + sessionId);
		
		//TODO inject the session service
		/*
		RegisterClientRemoteService.INSTANCE.getSessionService().keepAlive(
				sessionId);
				*/
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
