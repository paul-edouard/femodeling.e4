package de.femodeling.e4.server.service.remote;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.core.Session;
import de.femodeling.e4.model.dto.UserDTO;
import de.femodeling.e4.server.internal.context.ServerContextProvider;
import de.femodeling.e4.server.internal.model.UserServerImpl;
import de.femodeling.e4.server.service.dao.RegisterServerDAOService;
import de.femodeling.e4.server.service.dao.UserDAOIF;
import de.femodeling.e4.server.service.dao.UserDAOImpl;
import de.femodeling.e4.server.service.transform.UserTransformService;



public class UserRemoteService implements UserRemoteServiceIF {

	private static Logger logger = Logger.getLogger(UserRemoteService.class);
	static final long serialVersionUID = 1L;
	public static final String USER_LIST_KEY = "UserFileList";

	// private UserDAOImpl userDAO;

	public UserRemoteService() {
		// BasicConfigurator.configure();
		logger.info("------->User Service started");
	}

	@Override
	public Set<UserDTO> getAllUsers() {
		Set<UserDTO> uDTOSet = new HashSet<UserDTO>();
		UserDAOIF userDAO = RegisterServerDAOService.INSTANCE.getUserDAO();
		for (UserServerImpl user : userDAO.loadAll()) {
			uDTOSet.add(UserTransformService.transform(user));
		}

		return uDTOSet;
	}

	@Override
	public Set<String> getOnlineUserIds() {
		Set<String> userIds = new HashSet<String>();

		for (Session s : SessionProvider.INSTANCE.getAllSessions().values()) {
			userIds.add(s.getUserId());
		}
		return userIds;
	}

	/**
	 * return a session Id in case of a success login
	 */
	@Override
	public String internalLogin(String userId, String password) {

		UserDAOIF userDAO = RegisterServerDAOService.INSTANCE.getUserDAO();
		String sessionId = null;

		UserServerImpl u = userDAO.loadUser(userId);
		if (u != null) {
			sessionId = UUID.randomUUID().toString();
			Session s = new Session();
			s.setUserId(u.getId());
			s.setSessionId(sessionId);
			s.setLastAccess(new Date());

			SessionProvider.INSTANCE.addSession(s);
		}

		return sessionId;
	}

	public UserDTO getCurrentUser() {

		String userId = ServerContextProvider.getServerContext().getUserId();
		UserDAOIF userDAO = RegisterServerDAOService.INSTANCE.getUserDAO();
		UserServerImpl u = userDAO.loadUser(userId);

		return UserTransformService.transform(u);
	}

	@Override
	public UserDTO saveUser(UserDTO u) {
		UserDAOIF userDAO = RegisterServerDAOService.INSTANCE.getUserDAO();
		return UserTransformService.transform(userDAO
				.saveUser(UserTransformService.transform(u)));
	}

	public boolean deleteUser(UserDTO u) {
		return RegisterServerDAOService.INSTANCE.getUserDAO().deleteUser(
				UserTransformService.transform(u));
	}

	public void setConfigFiles(final Map<String, String> configFiles) {

		// logger.info("Start to read the config files for the user Service!!!!!!!!!!!!!!!!!!!");
		for (String key : configFiles.keySet()) {
			// System.out.println(configFiles.get(key));

			if (key.equals(USER_LIST_KEY)) {
				if (configFiles.get(key) != null
						&& new File(configFiles.get(key)).exists()) {
					// read the project files
					UserDAOIF userDAO = new UserDAOImpl(configFiles.get(key));
					RegisterServerDAOService.INSTANCE.setUserDAO(userDAO);
				} else {
					logger.warn("Cannot resolve of the User File List location");
				}

			}
		}

	}

}
