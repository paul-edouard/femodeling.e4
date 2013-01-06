package de.femodeling.e4.client.service.core;

import java.util.HashMap;

import de.femodeling.e4.client.model.LockableEntityClientImpl;

public interface LockClientServiceIF {
	
	
	/**
	 * try to lock a entity 
	 * 
	 * @param entity
	 * @return true on success
	 */
	public String lockEntity(LockableEntityClientImpl entity);
	
	
	public boolean unlockEntity(LockableEntityClientImpl entity);
	
	
	/**
	 * return all locked entities from the server
	 * 
	 * HashMap<LockableId,SessionId>
	 * 
	 * @return
	 */
	public HashMap<String, String> getAllLockedEntities();
	

}
