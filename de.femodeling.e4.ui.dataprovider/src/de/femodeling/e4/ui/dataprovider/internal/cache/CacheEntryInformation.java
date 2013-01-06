package de.femodeling.e4.ui.dataprovider.internal.cache;

import java.util.Date;

import de.femodeling.e4.ui.dataprovider.cache.ICacheEntryInformation;


/**
 * Internal implementation for <code>ICacheEntryInformation</code>.
 * 
 * @author Stefan Reichert
 */
public class CacheEntryInformation implements ICacheEntryInformation {

	/** The creation <code>Date</code>. */
	private final Date creation;

	/** The last access <code>Date</code>. */
	private Date lastAccess;
	
	/** The last update <code>Date</code>. */
	private Date lastUpdate;

	/**
	 * Constructor for <class>CacheEntryInformation</class>.
	 */
	public CacheEntryInformation() {
		super();
		creation = new Date();
		lastAccess = creation;
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.cache.ICacheEntryInformation#getCreation()
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.cache.ICacheEntryInformation#getLastAccess()
	 */
	public Date getLastAccess() {
		return lastAccess;
	}
	
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Proceeds an update on the last access <code>Date</code>.
	 */
	public void registerAccess() {
		lastAccess = new Date();
	}
	
	
	/**
	 * Proceeds an update on the last update <code>Date</code>.
	 */
	public void registerUpdate() {
		lastUpdate = new Date();
	}
	
	
}