package de.femodeling.e4.server.service.dao;

import java.util.Set;

import de.femodeling.e4.internal.model.LockableEntityServerImpl;


public interface LockableEntityDAOIF {
	
	/**
	 * locks the given entity and returns the session id on success
	 * 
	 * @param ent
	 * @return
	 */
	public boolean lockEntity(LockableEntityServerImpl ent);
	
	
	/**
	 * unlocks the given entity
	 * 
	 * @param ent
	 * @return true on success false otherwise
	 */
	public  boolean unlockEntity(LockableEntityServerImpl ent);

	
	/**
	 * check if the entity is locked
	 * 
	 * @param ent
	 * @return the locked entity or null otherwise
	 */
	public LockableEntityServerImpl isEntityLocked(LockableEntityServerImpl ent);
	
	/**
	 * return all the current locked entities
	 * 
	 * @return
	 */
	public Set<LockableEntityServerImpl> getAllLockedEntities();
	
	/**
	 * unlock all the entities referenced from a session id
	 * 
	 * @param sessionId
	 */
	public void unlockAllEntitiesFromSession(String sessionId);
	
	
}
