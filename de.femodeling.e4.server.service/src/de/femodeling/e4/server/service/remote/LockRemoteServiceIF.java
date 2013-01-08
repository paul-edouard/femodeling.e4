package de.femodeling.e4.server.service.remote;

import java.io.Serializable;
import java.util.HashMap;

import de.femodeling.e4.model.core.lockable.LockableEntity;


public interface LockRemoteServiceIF extends Serializable {
	
	/**
	 * locks the given entity
	 * 
	 * @param ent
	 * @return null on success or return the already locked entity on error
	 */
	public LockableEntity lockEntity( LockableEntity ent);
	
	
	/**
	 * unlocks the given entity
	 * 
	 * @param ent
	 * @return true on success or false on error
	 */
	public  boolean unlockEntity(final LockableEntity ent);
	
	
	/**
	 * return all the current locked entities
	 * 
	 * @return
	 */
	public HashMap<String, String> getAllLockedEntities();

}
