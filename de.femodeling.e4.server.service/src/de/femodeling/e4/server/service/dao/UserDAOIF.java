package de.femodeling.e4.server.service.dao;

import java.util.Set;

import de.femodeling.e4.internal.model.UserServerImpl;

public interface UserDAOIF {

	/**
	 * Loads all existing {@link de.femodeling.e4.internal.model.porsche.femodeling.core.user.UserServerImpl}
	 * entities from the database.
	 */
	public Set<UserServerImpl> loadAll();

	/**
	 * Loads the {@link de.femodeling.e4.internal.model.porsche.femodeling.core.user.UserServerImpl} with the
	 * given key from the database.
	 */
	public UserServerImpl loadUser(String id);

	/**
	 * Creates or updates the given
	 * {@link de.femodeling.e4.internal.model.porsche.femodeling.core.user.UserServerImpl}.
	 */
	public UserServerImpl saveUser(UserServerImpl user);

	/**
	 * Deletes the given {@link de.femodeling.e4.internal.model.porsche.femodeling.core.user.UserServerImpl}.
	 */
	public boolean deleteUser(UserServerImpl user);

}
