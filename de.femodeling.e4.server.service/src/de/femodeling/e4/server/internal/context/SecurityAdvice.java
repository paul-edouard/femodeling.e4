/**
 * SecurityAdvice.java
 *
* Copyright (c) 2011-2012 Paul-Edouard Munch
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Paul-Edouard Munch. Use is subject to license terms.
 */
package de.femodeling.e4.server.internal.context;



import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

import de.femodeling.e4.server.internal.SessionProvider;
import de.femodeling.e4.server.internal.exception.SessionExpiredException;


/**
 * @author Paul-Edouard Munch
 */
public class SecurityAdvice implements MethodBeforeAdvice/*,
		ApplicationContextAware*/ {

	private static Logger logger = Logger.getLogger(SecurityAdvice.class);

	//private ApplicationContext applicationContext;

	private IContextProvider contextProvider;

	public SecurityAdvice() {
		logger.info("security advice created.");
	}

	/** {@inheritDoc} */
	public void before(Method method, Object[] arguments, Object returnType)
			throws Throwable {
		/*ISessionService sessionService = (ISessionService) applicationContext
				.getBean("sessionService");*/
		/*
		logger.info("session verification for user <"
				+ contextProvider.getContextUserId() + "> with session <"
				+ contextProvider.getContextSessionId() + ">");
		*/
		
		if (!SessionProvider.INSTANCE.isSessionAlive(contextProvider.getContextSessionId())) {
			logger.info("- failed");
			throw new SessionExpiredException("Session <"
					+ contextProvider.getContextSessionId() + "> expired.");
		}
		
		
		//logger.info("- succeeded");
		
	}

	/** {@inheritDoc} */
	/*
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	*/

	public IContextProvider getContextProvider() {
		return contextProvider;
	}

	public void setContextProvider(IContextProvider contextProvider) {
		this.contextProvider = contextProvider;
	}

}
