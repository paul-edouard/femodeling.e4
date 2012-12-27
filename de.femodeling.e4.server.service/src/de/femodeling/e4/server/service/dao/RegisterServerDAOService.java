package de.femodeling.e4.server.service.dao;




public enum RegisterServerDAOService {
	
	INSTANCE;
	
	//LockableEntityDAO
	private LockableEntityDAOIF lockableEntityDAO;
	
	//UserDAO
	private UserDAOIF userDAO;
	
	//Message DAO
	private MessageDAOIF messageDAO;
	
	//Project DAO
	private ProjectDAOIF projectDAO;
	
	//Part DAO
	private PartDAOIF partDAO;
	
	//Assembly DAO
	private AssemblyDAOIF assemblyDAO;
	
	//Connection DAO
	private ConnectionElementDAOIF connectionDAO;
	

	public LockableEntityDAOIF getLockableEntityDAO() {
		return lockableEntityDAO;
	}

	public void setLockableEntityDAO(LockableEntityDAOIF lockableEntityDAO) {
		this.lockableEntityDAO = lockableEntityDAO;
	}

	public UserDAOIF getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAOIF userDAO) {
		this.userDAO = userDAO;
	}

	public MessageDAOIF getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAOIF messageDAO) {
		this.messageDAO = messageDAO;
	}

	public ProjectDAOIF getProjectDAO() {
		return projectDAO;
	}

	public void setProjectDAO(ProjectDAOIF projectDAO) {
		this.projectDAO = projectDAO;
	}

	public PartDAOIF getPartDAO() {
		return partDAO;
	}

	public void setPartDAO(PartDAOIF partDAO) {
		this.partDAO = partDAO;
	}

	public AssemblyDAOIF getAssemblyDAO() {
		return assemblyDAO;
	}

	public void setAssemblyDAO(AssemblyDAOIF assemblyDAO) {
		this.assemblyDAO = assemblyDAO;
	}

	public ConnectionElementDAOIF getConnectionDAO() {
		return connectionDAO;
	}

	public void setConnectionDAO(ConnectionElementDAOIF connectionDAO) {
		this.connectionDAO = connectionDAO;
	}
	
	
	
	
	
}
