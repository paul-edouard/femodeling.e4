package de.femodeling.e4.client.service.internal.processor;
 

import org.apache.log4j.Logger;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.service.internal.job.MessagesJob;
import de.femodeling.e4.server.service.RemoteService;
import de.femodeling.e4.ui.dataprovider.registery.IRegistery;

public class ClientServiceProcessor {
	
	private static Logger logger = Logger.getLogger(ClientServiceProcessor.class);
	
	private MessagesJob messagejob;
	
	@Execute
	public void execute(RemoteService remoteService, ClientSession session,
			IClientService service,IEclipseContext context,
			IRegistery registery,IProjectProvider projectProvider){
		//Client service initialization
		logger.info("Client service initialization");
		service.init(remoteService, session);
		
		//Project Provider service initialization
		logger.info("----->Project Provider service initialization");
		projectProvider.init(registery, service, remoteService);
		
		//Start the message service
		logger.info("Start of message service");
		messagejob=ContextInjectionFactory.make(MessagesJob.class,context);
		
	}
	
	
	
	
}