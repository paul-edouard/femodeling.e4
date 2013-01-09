package de.femodeling.e4.client.service;

import java.util.Collection;
import java.util.Set;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.ui.dataprovider.registery.IRegistery;

public interface IUserProvider {

	public abstract void init(IRegistery registery, IClientService service,ClientSession session);

	public abstract UserClientImpl putData(UserClientImpl p);

	public abstract UserClientImpl updateData(UserClientImpl p);

	public abstract boolean removeData(UserClientImpl p);

	public abstract UserClientImpl getData(String userId);
	
	public abstract Collection<UserClientImpl> getAllUsers();
	
	public abstract Collection<UserClientImpl> getAllGroups();

	public abstract UserClientImpl getCurrentUser();

	public abstract Set<String> getOnlineUserIds();

}