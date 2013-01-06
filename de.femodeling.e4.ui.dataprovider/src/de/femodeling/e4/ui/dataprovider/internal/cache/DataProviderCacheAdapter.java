/**
 * CacheDataProviderProxy.java created on 01.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package de.femodeling.e4.ui.dataprovider.internal.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.femodeling.e4.ui.dataprovider.IDataProvider;
import de.femodeling.e4.ui.dataprovider.IWritableDataProvider;
import de.femodeling.e4.ui.dataprovider.cache.ICachingStrategy;
import de.femodeling.e4.ui.dataprovider.key.ICollectionKey;
import de.femodeling.e4.ui.dataprovider.key.IKey;



/**
 * Adapter for an <code>IDataProvider</code> implementing caching.
 * 
 * @author Stefan Reichert
 */
public class DataProviderCacheAdapter implements IWritableDataProvider {

	/** This class' Logger instance. */
	private static Logger logger = Logger
			.getLogger(DataProviderCacheAdapter.class);

	/** The adapted <code>IDataProvider</code>. */
	private IDataProvider adaptedDataProvider;

	/** The <code>ICachingStrategy</code> for the <code>IDataProvider</code>. */
	private ICachingStrategy cachingStrategy;

	/** The <code>Map</code> of <code>ICacheEntryInformation</code>. */
	private Map<IKey, CacheEntryInformation> cacheInformationMap;

	/**
	 * Constructor for <class>DataProviderCacheAdapter</class>.
	 */
	public DataProviderCacheAdapter(IDataProvider adaptedDataProvider,
			ICachingStrategy cachingStrategy) {
		super();
		this.adaptedDataProvider = adaptedDataProvider;
		this.cachingStrategy = cachingStrategy;
		cacheInformationMap = new HashMap<IKey, CacheEntryInformation>();
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IDataProvider#getData(de.porsche.femodeling.ui.dataprovider.key.IKey)
	 */
	public Object getData(IKey key) {
		CacheEntryInformation cacheEntryInformation = cacheInformationMap
				.get(key);
		String cacheDetails = new StringBuilder("- type: ").append( //$NON-NLS-1$
				adaptedDataProvider.getType()).append(" - key: ").append(key) //$NON-NLS-1$
				.append(" - entry").toString(); //$NON-NLS-1$
		if (cacheEntryInformation != null
				&& !cachingStrategy.expired(key, cacheEntryInformation)) {
			//logger.debug("Cache Hit " + cacheDetails); //$NON-NLS-1$
			cacheEntryInformation.registerAccess();
			return (Object) cachingStrategy.getCachedData(key);
		}
		else {
			//logger.debug("Cache Miss " + cacheDetails); //$NON-NLS-1$
			Object data = adaptedDataProvider.getData(key);
			flushCacheForUpdate(key, data);
			return (Object) data;
		}
	}

	/**
	 * Proceeds a flush of the cache due to update of data. This flush for
	 * remove consist of three steps <li>Update the
	 * <code>ChacheInformation</code></li> <li>Evict the old data</li> <li>
	 * Register the new data</li>
	 * 
	 * @param key
	 *            The <code>IKey</code> of the data to update
	 * @param data
	 *            The data to update for the given <code>IKey</code>
	 */
	public void flushCacheForUpdate(IKey key, Object data) {
		StringBuilder cacheDetailsBuilder = new StringBuilder("- type: ") //$NON-NLS-1$
				.append(adaptedDataProvider.getType()).append(" - key: ") //$NON-NLS-1$
				.append(key);
		if (key instanceof ICollectionKey) {
			cacheDetailsBuilder.append(" - collection"); //$NON-NLS-1$
		}
		else {
			cacheDetailsBuilder.append(" - entry"); //$NON-NLS-1$

		}
		//logger.debug("Cache flush [update] " + cacheDetailsBuilder.toString()); //$NON-NLS-1$
		CacheEntryInformation cacheEntryInformation = new CacheEntryInformation();
		cacheInformationMap.put(key, cacheEntryInformation);
		cachingStrategy.evictCachedData(key);
		cachingStrategy.registerCachedData(key, data, cacheEntryInformation);
	}

	/**
	 * Proceeds a flush of the cache due to removal of data. This flush for
	 * remove consist of two steps <li>Update the <code>ChacheInformation</code>
	 * </li> <li>Evict the old data</li>
	 * 
	 * @param key
	 *            The <code>IKey</code> of the data to update
	 * @param data
	 *            The data to update for the given <code>IKey</code>
	 */
	private void flushCacheForRemove(IKey key) {
		StringBuilder cacheDetailsBuilder = new StringBuilder("- type: ") //$NON-NLS-1$
				.append(adaptedDataProvider.getType()).append(" - key: ") //$NON-NLS-1$
				.append(key);
		if (key instanceof ICollectionKey) {
			cacheDetailsBuilder.append(" - collection"); //$NON-NLS-1$
		}
		else {
			cacheDetailsBuilder.append(" - entry"); //$NON-NLS-1$

		}
		//logger.debug("Cache flush [delete] " + cacheDetailsBuilder.toString()); //$NON-NLS-1$
		CacheEntryInformation cacheEntryInformation = new CacheEntryInformation();
		cacheInformationMap.put(key, cacheEntryInformation);
		cachingStrategy.evictCachedData(key);
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IWritableDataProvider#putData(de.porsche.femodeling.ui.dataprovider.key.IKey,
	 *      java.lang.Object)
	 */
	public Object putData(IKey key, Object data) {
		if (adaptedDataProvider instanceof IWritableDataProvider) {
			
			if (key instanceof ICollectionKey) {
				throw new UnsupportedOperationException(
						"Updating data identified by an [ICollectionKey] is not supported in cache mode"); //$NON-NLS-1$
			}
			
			IWritableDataProvider writableDataProvider = (IWritableDataProvider) adaptedDataProvider;
			Object savedData = writableDataProvider.putData(key, data);
			if(savedData!=null)flushCacheForUpdate(key, savedData);
			return savedData;
		}
		else {
			throw new UnsupportedOperationException(
					"DataProvider does not support writable access"); //$NON-NLS-1$
		}
	}
	
	public Object updateData(IKey key, Object data){
		if (adaptedDataProvider instanceof IWritableDataProvider) {
			if (key instanceof ICollectionKey) {
				throw new UnsupportedOperationException(
						"Updating data identified by an [ICollectionKey] is not supported in cache mode"); //$NON-NLS-1$
			}
			IWritableDataProvider writableDataProvider = (IWritableDataProvider) adaptedDataProvider;
			
			//TODO Update the data from the cache
			//update the data in to the server layer
			Object updatedData = writableDataProvider.updateData(key, data);
			if(updatedData==null)return null;
			
			CacheEntryInformation cacheEntryInformation = cacheInformationMap
					.get(key);
			/*
			String cacheDetails = new StringBuilder("- type: ").append( //$NON-NLS-1$
					adaptedDataProvider.getType()).append(" - key: ").append(key) //$NON-NLS-1$
					.append(" - entry").toString(); //$NON-NLS-1$
			*/
			/*
			logger.debug("Try to update the cache data");
			if (cacheEntryInformation == null)
				logger.debug("No cach info!!");
			*/
			
			if (cacheEntryInformation != null
					&& !cachingStrategy.expired(key, cacheEntryInformation)) {
				
				//logger.debug("Strategy OK");
				
				cachingStrategy.updateCachedData(key, updatedData);
				cacheEntryInformation.registerUpdate();
			}
			
			//flushCacheForUpdate(key, updatedData);
			return updatedData;
		}
		else {
			throw new UnsupportedOperationException(
					"DataProvider does not support writable access"); //$NON-NLS-1$
		}
	}
	

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IWritableDataProvider#removeData(de.porsche.femodeling.ui.dataprovider.key.IKey)
	 */
	public boolean removeData(IKey key) {
		if (adaptedDataProvider instanceof IWritableDataProvider) {
			IWritableDataProvider writableDataProvider = (IWritableDataProvider) adaptedDataProvider;
			
			if(writableDataProvider.removeData(key)){
				flushCacheForRemove(key);return true;
			}
			
			return false;
		}
		else {
			throw new UnsupportedOperationException(
					"DataProvider does not support writable access"); //$NON-NLS-1$
			
		}
		
		
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IDataProvider#getProviderGroup()
	 */
	public String getProviderGroup() {
		return adaptedDataProvider.getProviderGroup();
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IDataProvider#getType()
	 */
	public String getType() {
		return adaptedDataProvider.getType();
	}

	/**
	 * @return the cachingStrategy
	 */
	public ICachingStrategy getCachingStrategy() {
		return cachingStrategy;
	}

	/**
	 * @return the <code>CacheEntryInformation</code> for the given
	 *         <code>IKey</code>
	 */
	public CacheEntryInformation getCacheInformation(IKey key) {
		if (!getCachingStrategy().exists(key)) {
			cacheInformationMap.remove(key);
		}
		return cacheInformationMap.get(key);
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IDataProvider#getDataCollection(de.porsche.femodeling.ui.dataprovider.key.IKey)
	 */
	@SuppressWarnings("unchecked")
	public Collection getDataCollection(ICollectionKey collectionKey) {
		CacheEntryInformation cacheEntryInformation = cacheInformationMap
				.get(collectionKey);
		String cacheDetails = new StringBuilder("- type: ").append( //$NON-NLS-1$
				adaptedDataProvider.getType()).append(" - key: ").append( //$NON-NLS-1$
				collectionKey).append(" - collection").toString(); //$NON-NLS-1$
		if (cacheEntryInformation != null
				&& !cachingStrategy.expired(collectionKey,
						cacheEntryInformation)) {
			//logger.debug("Cache Hit " + cacheDetails); //$NON-NLS-1$
			cacheEntryInformation.registerAccess();
			Collection<IKey> collectionKeys = (Collection<IKey>) cachingStrategy
					.getCachedData(collectionKey);
			Collection dataCollection = new ArrayList();
			//logger.debug("--- Reveal collection details from cache"); //$NON-NLS-1$
			for (IKey key : collectionKeys) {
				dataCollection.add(getData(key));
			}
			//logger.debug("--- Finished"); //$NON-NLS-1$
			return dataCollection;
		}
		else {
			//logger.debug("Cache Miss " + cacheDetails); //$NON-NLS-1$
			Collection dataCollection = adaptedDataProvider
					.getDataCollection(collectionKey);
			Collection<IKey> collectionKeys = new ArrayList<IKey>();
			//logger.debug("--- Provide collection details to cache"); //$NON-NLS-1$
			for (Object data : dataCollection) {
				IKey key = getKey(data);
				flushCacheForUpdate(key, data);
				collectionKeys.add(key);
			}
			//logger.debug("--- Finished"); //$NON-NLS-1$
			flushCacheForUpdate(collectionKey, collectionKeys);
			return dataCollection;
		}
	}

	/**
	 * @see de.porsche.femodeling.ui.dataprovider.IDataProvider#getKey(java.lang.Object)
	 */
	public IKey getKey(Object data) {
		return adaptedDataProvider.getKey(data);
	}

}
