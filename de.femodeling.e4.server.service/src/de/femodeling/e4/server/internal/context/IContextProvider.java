/**
 * IContextProvider.java
 *
 * Copyright (c) 2011-2012 Paul-Edouard Munch
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Paul-Edouard Munch. Use is subject to license terms.
 */
package de.femodeling.e4.server.internal.context;

/**
 * @author Paul-Edouard Munch
 */
public interface IContextProvider {

	final String USER_ID = "pmc.user.id";

	final String SESSION_ID = "pmc.session.id";

	final String VERSION = "pmc.version";

	String getContextUserId();

	String getContextSessionId();

	String getContextVersion();

}
