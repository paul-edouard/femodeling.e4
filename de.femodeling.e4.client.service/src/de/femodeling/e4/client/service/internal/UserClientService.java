package de.femodeling.e4.client.service.internal;

import java.util.Set;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.service.core.UserClientServiceIF;
import de.femodeling.e4.client.service.internal.transform.UserTransformService;
import de.femodeling.e4.model.dto.UserDTO;
import de.femodeling.e4.server.service.RemoteService;



public class UserClientService implements UserClientServiceIF {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;
	

	RemoteService remoteService;

	public UserClientService(RemoteService remoteService) {
		super();
		this.remoteService = remoteService;
	}

	@Override
	public Set<UserClientImpl> getAllUsers() {
		Set<UserDTO> uDTOSet = remoteService
				.getUserService().getAllUsers();
		return UserTransformService.transformClient(uDTOSet);
	}

	@Override
	public Set<String> getOnlineUserIds() {
		return remoteService.getUserService()
				.getOnlineUserIds();
	}

	@Override
	public UserClientImpl saveUser(UserClientImpl u) {
		// Transformation!!

		UserDTO u_dto = UserTransformService.transformClient(u);

		UserDTO r_u_dto = remoteService.getUserService()
				.saveUser(u_dto);

		return UserTransformService.transformClient(r_u_dto);
	}

	public boolean deleteUser(UserClientImpl u) {
		// Transformation!!

		UserDTO u_dto = UserTransformService.transformClient(u);
		return remoteService.getUserService()
				.deleteUser(u_dto);
	}

	public UserClientImpl getCurrentUser() {

		UserDTO u_dto = remoteService.getUserService()
				.getCurrentUser();
		return UserTransformService.transformClient(u_dto);

	}

}
