package de.femodeling.e4.server.service.remote;

import java.io.Serializable;
import java.util.Set;

import de.femodeling.e4.model.dto.UserDTO;


public interface UserRemoteServiceIF extends Serializable {

	public Set<UserDTO> getAllUsers();

	public Set<String> getOnlineUserIds();

	public UserDTO getCurrentUser();

	public String internalLogin(String userId, String password);

	public UserDTO saveUser(UserDTO u);

	public boolean deleteUser(UserDTO u);

}
