package de.femodeling.e4.client.service.internal.job;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;

import de.femodeling.e4.client.service.IMessageKey;
import de.femodeling.e4.model.dto.MessageDTO;
import de.femodeling.e4.server.service.RemoteService;




public class MessagesJob extends Job {

	private static Logger logger = Logger.getLogger(MessagesJob.class);
	
	private Date lastMessageCalled;
	
	@Inject
	private RemoteService remoteService;
	
	@Inject
	IEventBroker broker;
	
	
	public MessagesJob(){
		super("Message Job");
		setPriority(SHORT);
		setSystem(true);
		
	}
	
	

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Getting new messages", -1);
		
		Calendar now = Calendar.getInstance();
			
		if(lastMessageCalled==null){
			//Calendar now=Calendar.getInstance();
			lastMessageCalled=now.getTime();
		}
		
		LinkedList<MessageDTO> mesList=remoteService.getMessageService().getLastMesssages( lastMessageCalled);
		
		
		for(MessageDTO ent:mesList){
			lastMessageCalled=ent.getCreatingTime();
			switch (ent.getSendingType()) {
			case ADD:
				broker.post(IMessageKey.ADD_DATA, ent);
				break;
			case UPDATE:
				broker.post(IMessageKey.UPDATE_DATA, ent);
				break;	
			case REMOVE:
				broker.post(IMessageKey.REMOVE_DATA, ent);
				break;	
			}
		}
		
		
		/*
		logger.info("Getting new Messages: " + DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.MEDIUM).format(now.getTime())+" Number of Messages: "+mesList.size());
		*/
		
		schedule(de.femodeling.e4.model.core.Message.MESSAGE_EXPIRATION*25);
		return Status.OK_STATUS;
	}
	
	
	@Inject
	public void topics(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) IEclipseContext context){

		if(this.getState()!=RUNNING){
			logger.info("Starting the message job: ");
			schedule();
		}
		
	}
	
	

}
