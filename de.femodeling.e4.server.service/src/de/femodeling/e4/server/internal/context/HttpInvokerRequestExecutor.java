/**
 * HttpInvokerRequestExecutor.java created on 27.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package de.femodeling.e4.server.internal.context;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;



/**
 * A custom invoker executor class to allow piggy packing.
 * 
 * @author Stefan Reichert
 */
public class HttpInvokerRequestExecutor extends
SimpleHttpInvokerRequestExecutor {

	/** The session id */
	private static String sessionId="";

	/** The user id */
	private static String userId="";

	/** {@inheritDoc} */
	@Override
	protected void prepareConnection(HttpURLConnection connection,
			int contentLength) throws IOException {
		
		
		super.prepareConnection(connection, contentLength);
		connection.setRequestProperty(IContextProvider.USER_ID, userId);
		connection.setRequestProperty(IContextProvider.SESSION_ID, sessionId);
		
		/*connection.setRequestProperty(IContextProvider.VERSION, Activator
				.getDefault().getBundle().getHeaders().get("Bundle-Version")
				.toString());*/
		
	
	connection.setRequestProperty(IContextProvider.VERSION, "001");
	
	//Connection Options
	/*
	 -Dorg.eclipse.ecf.provider.filetransfer.excludeContributors=org.eclipse.ecf.provider.filetransfer.httpclient -Dhttp.proxySet=true -Dhttp.proxyPort=3140 -Dhttp.proxyHost=23.49.10.12 -Dhttp.proxyUser=porscheLocalUserDatabase\p323518 -Dhttp.proxyPassword=BZQEA2ZJ -Dhttp.nonProxyHosts="localhost|127.0.0.1" -Dosgi.requiredJavaVersion=1.5 -XX:MaxPermSize=256m -Xms40m -Xmx512m
	 * 
	 */
	
	}
	
	/**
	 * @return the sessionId
	 */
	
	public static String getSessionId() {
		return sessionId;
	}

	/**
	 * @param seesionId
	 *            the sessionId to set
	 */
	
	public static void setSessionId(String sessionId) {
		HttpInvokerRequestExecutor.sessionId = sessionId;
	}
	

	/**
	 * @return the userId
	 */
	
	public static String getUserId() {
		return userId;
	}
	

	/**
	 * @param userId
	 *            the userId to set
	 */
	
	public static void setUserId(String userId) {
		HttpInvokerRequestExecutor.userId = userId;
	}
	
}
