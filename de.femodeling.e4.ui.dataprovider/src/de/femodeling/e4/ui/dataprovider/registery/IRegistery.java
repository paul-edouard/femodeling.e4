package de.femodeling.e4.ui.dataprovider.registery;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.femodeling.e4.ui.dataprovider.IDataProvider;

public interface IRegistery {
	
	
	/**
	 * @param ignoreCacheDefinition
	 *            the ignoreCacheDefinition to set
	 */
	public void ignoreCacheDefinition();
	
	/**
	 * @return the ignoreCacheDefinition
	 */
	public boolean isCacheDefinitionIgnored();
	
	/**
	 * Initializes the <code>Regsitry</code> by loading the declared extensions.
	 * All <code>IDataProvider</code>s found are listed in the <code>Map</code>
	 * to allow a lookup later on.
	 */
	public void initialize(IEclipseContext parent); 
	
	
	/**
	 * @param providerType
	 *            The type of <code>IDataProvider</code> to look up
	 * @return The matching <code>IDataProvider</code>
	 * @throws UnknownDataProviderException
	 *             if the given <code>providerType</code> is not available.
	 *             Ckeck existence with {@link #isDataProviderAvailable(String)}
	 *             first.
	 */
	public IDataProvider lookupDataProvider(String providerType);
	
	/**
	 * @param providerType
	 *            The type of <code>IDataProvider</code> to look up
	 * @return Whether a matching <code>IDataProvider</code> exists
	 */
	public boolean isDataProviderAvailable(String providerType);

}
