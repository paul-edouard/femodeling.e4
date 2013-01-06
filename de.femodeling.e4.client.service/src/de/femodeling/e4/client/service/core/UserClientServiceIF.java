package de.femodeling.e4.client.service.core;

import java.io.Serializable;
import java.util.Set;

import de.femodeling.e4.client.model.UserClientImpl;


public interface UserClientServiceIF extends Serializable {

	public Set<UserClientImpl> getAllUsers();

	public UserClientImpl getCurrentUser();

	public Set<String> getOnlineUserIds();

	public UserClientImpl saveUser(UserClientImpl u);

	public boolean deleteUser(UserClientImpl u);

	// public String internalLogin(String userId, String password);

}
