/**
 * IDataProviderAware.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package de.femodeling.e4.ui.dataprovider.cache;

import de.femodeling.e4.ui.dataprovider.IDataProvider;



/**
 * An interface that indicates an {@link ICachingStrategy} to be aware of it's
 * using {@link IDataProvider}.
 * 
 * @author Stefan Reichert
 */
public interface IDataProviderAware {

	/**
	 * @param dataProvider The using {@link IDataProvider}
	 */
	void setDataProvider(IDataProvider dataProvider);
}
