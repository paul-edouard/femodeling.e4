package de.femodeling.e4.server.service.remote;

import java.io.Serializable;
import java.util.HashMap;

import de.femodeling.e4.model.dto.LockableEntityDTO;


public interface LockRemoteServiceIF extends Serializable {
	
	/**
	 * locks the given entity
	 * 
	 * @param ent
	 * @return null on success or return the already locked entity on error
	 */
	public LockableEntityDTO lockEntity( LockableEntityDTO ent);
	
	
	/**
	 * unlocks the given entity
	 * 
	 * @param ent
	 * @return true on success or false on error
	 */
	public  boolean unlockEntity(final LockableEntityDTO ent);
	
	
	/**
	 * return all the current locked entities
	 * 
	 * @return
	 */
	public HashMap<String, String> getAllLockedEntities();

}
