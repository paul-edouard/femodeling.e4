/**
 * ServiceException.java created on 27.02.2009
 * 
 * Copyright (c) 2011-2012 Paul-Edouard Munch
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Paul-Edouard Munch. Use is subject to license terms.
 */
package de.femodeling.e4.internal.exception;

/**
 * A {@link RuntimeException} for service call errors.
 * 
 * @author Paul-Edouard Munch
 */
public class ServiceException extends RuntimeException {

	/** The serial version UID. */
	private static final long serialVersionUID = -8742903852410465059L;

	/** The name of the cause {@link Throwable} if available. */
	private String causeName;

	/**
	 * Constructor for <class>ServiceException</class>.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Constructor for <class>ServiceException</class>.
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor for <class>ServiceException</class>.
	 */
	public ServiceException(String message, Throwable cause) {
		super(message);
		causeName = cause.getClass().getName();
	}

	/**
	 * Constructor for <class>ServiceException</class>.
	 */
	public ServiceException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	/**
	 * @return the name of the cause {@link Throwable}
	 */
	public String getCauseName() {
		return causeName;
	}

}
