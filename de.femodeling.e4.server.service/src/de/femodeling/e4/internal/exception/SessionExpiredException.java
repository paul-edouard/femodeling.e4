/**
 * SessionExpiredException.java
 *
 * Copyright (c) 2011-2012 Paul-Edouard Munch
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Paul-Edouard Munch. Use is subject to license terms.
 */
package de.femodeling.e4.internal.exception;

/**
 * @author Paul-Edouard Munch
 *
 */
public class SessionExpiredException extends RuntimeException {

	/** The serial version uid. */
	private static final long serialVersionUID = 5621642522087540445L;

	/**
	 * Constructor for <class>SessionExpiredException</class>.
	 */
	public SessionExpiredException() {
		super();
	}

	/**
	 * Constructor for <class>SessionExpiredException</class>.
	 */
	public SessionExpiredException(String message) {
		super(message);
	}
	
	

}
