package de.femodeling.e4.server.internal;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import de.femodeling.e4.server.service.RemoteService;
import de.femodeling.e4.server.service.remote.LockRemoteServiceIF;
import de.femodeling.e4.server.service.remote.MessageRemoteServiceIF;
import de.femodeling.e4.server.service.remote.ProjectRemoteServiceIF;
import de.femodeling.e4.server.service.remote.SessionServiceIF;
import de.femodeling.e4.server.service.remote.UserRemoteServiceIF;

public class RemoteServiceImpl implements RemoteService {
	
	
	//private final String DEFAULT_CONTEXT_LOCATION="classpath:spring-http-client-config.xml";
	private final String DEFAULT_URL_SERVICE_LOCATION="http://localhost:8081/de.femodeling.e4.server";
	//private ClassPathXmlApplicationContext context=null;
	//private String contextLocation;
	private String serviceLocation;
		
	private GenericApplicationContext context=null;
	
	private ProjectRemoteServiceIF projectRemoteService=null;
	private UserRemoteServiceIF userService=null;
	private SessionServiceIF sessionService=null;
	private LockRemoteServiceIF lockService=null;
	private MessageRemoteServiceIF messageService=null;
	
	
	
	public RemoteServiceImpl(){
		System.out.println("Remote Service impl started");
	}
	
	
	
	public void init(String applicationContextLocation){
		serviceLocation=applicationContextLocation;
		System.out.println("Set Context locations");
	}
	
	@Override
	public ProjectRemoteServiceIF getProjectRemoteService() {
		if(projectRemoteService==null){
			projectRemoteService=(ProjectRemoteServiceIF) getRemoteService("projectProxyService");
		}
		return projectRemoteService;
	}

	@Override
	public UserRemoteServiceIF getUserService() {
		if(userService==null){
			userService=(UserRemoteServiceIF) getRemoteService("userProxyService");
		}
		return userService;
	}

	@Override
	public SessionServiceIF getSessionService() {
		if(sessionService==null){
			sessionService=(SessionServiceIF) getRemoteService("sessionProxyService");
		}
		return sessionService;
	}

	@Override
	public LockRemoteServiceIF getLockService() {
		if(lockService==null){
			lockService=(LockRemoteServiceIF) getRemoteService("lockProxyService");
		}
		return lockService;
	}

	@Override
	public MessageRemoteServiceIF getMessageRemoteService() {
		if(messageService==null){
			messageService=(MessageRemoteServiceIF) getRemoteService("messageProxyService");
		}
		return messageService;
	}
	
	
	private Object getRemoteService(String serviceName){
		Thread currentThread = Thread.currentThread();
		ClassLoader originalClassloader=currentThread.getContextClassLoader();
		try{
		
			currentThread.setContextClassLoader(this.getClass().getClassLoader());
			return  getContext().getBean(serviceName);
		
		}finally{
			currentThread.setContextClassLoader(originalClassloader);
		}
	}
	
	private BeanDefinitionBuilder createBeanDefinition(String serviceUrl, String serviceInterface){
		
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean.class)
				.addPropertyValue("serviceUrl", serviceUrl)
				.addPropertyValue("serviceInterface", serviceInterface);
		
		return builder;
		
	}
	
	private synchronized ApplicationContext getContext(){
		if(context==null){
			
			
			
			if(serviceLocation==null){
				serviceLocation=DEFAULT_URL_SERVICE_LOCATION;
			}
			
			Thread currentThread = Thread.currentThread();
			ClassLoader originalClassloader=currentThread.getContextClassLoader();
			try{
				
				currentThread.setContextClassLoader(this.getClass().getClassLoader());
				
				
				context= new GenericApplicationContext();
				
				
				BeanDefinitionBuilder builderRequestExecutor = BeanDefinitionBuilder.rootBeanDefinition(de.femodeling.e4.server.internal.context.HttpInvokerRequestExecutor.class);
				
				//BeanDefinitionBuilder builderRequestExecutor = BeanDefinitionBuilder.rootBeanDefinition(org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor.class);
				
				
				//org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor
				
				//org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor
				BeanDefinitionBuilder builderA=createBeanDefinition(
						serviceLocation+"/projects", 
						"de.femodeling.e4.server.service.remote.ProjectRemoteServiceIF");
				builderA.addPropertyReference("httpInvokerRequestExecutor","requestExecutorProject");
				
				
				BeanDefinitionBuilder builderB=createBeanDefinition(
						serviceLocation+"/users", 
						"de.femodeling.e4.server.service.remote.UserRemoteServiceIF");
				builderB.addPropertyReference("httpInvokerRequestExecutor","requestExecutorUser");
				//builderB.
				
				
				BeanDefinitionBuilder builderC=createBeanDefinition(
						serviceLocation+"/sessions", 
						"de.femodeling.e4.server.service.remote.SessionServiceIF");
				builderC.addPropertyReference("httpInvokerRequestExecutor","requestExecutorSession");
				
				
				BeanDefinitionBuilder builderD=createBeanDefinition(
						serviceLocation+"/lock", 
						"de.femodeling.e4.server.service.remote.LockRemoteServiceIF");
				builderD.addPropertyReference("httpInvokerRequestExecutor","requestExecutorLock");
				
				
				BeanDefinitionBuilder builderE=createBeanDefinition(
						serviceLocation+"/messages", 
						"de.femodeling.e4.server.service.remote.MessageRemoteServiceIF");
				builderE.addPropertyReference("httpInvokerRequestExecutor","requestExecutorMessage");
				
				
				context.registerBeanDefinition("requestExecutorProject", builderRequestExecutor.getBeanDefinition());
				context.registerBeanDefinition("projectProxyService", builderA.getBeanDefinition());
				
				context.registerBeanDefinition("requestExecutorUser", builderRequestExecutor.getBeanDefinition());
				context.registerBeanDefinition("userProxyService", builderB.getBeanDefinition());
				
				context.registerBeanDefinition("requestExecutorSession", builderRequestExecutor.getBeanDefinition());
				context.registerBeanDefinition("sessionProxyService", builderC.getBeanDefinition());
				
				context.registerBeanDefinition("requestExecutorLock", builderRequestExecutor.getBeanDefinition());
				context.registerBeanDefinition("lockProxyService", builderD.getBeanDefinition());
				
				context.registerBeanDefinition("requestExecutorMessage", builderRequestExecutor.getBeanDefinition());
				context.registerBeanDefinition("messageProxyService", builderE.getBeanDefinition());
				

				//context=new ClassPathXmlApplicationContext(contextLocation);
				
			}finally{
				currentThread.setContextClassLoader(originalClassloader);
			}
			
		}
		return context;
	}

}
