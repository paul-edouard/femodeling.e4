package de.femodeling.e4.client.jobs;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.services.events.IEventBroker;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.server.service.RemoteService;

public class KeepAliveJob extends Job {

	private static Logger logger = Logger.getLogger(KeepAliveJob.class);
	
	
	public static final int RESTART_TIMEOUT=30000;
	
	public enum State{ONLINE,OFFLINE};
	
	@Inject
	ClientSession session;
	
	@Inject
	RemoteService remoteService;
	
	@Inject
	private IEventBroker eventBroker;
	
	/**
	 * Constructor for <class>KeepAliveJob</class>.
	 */
	@Inject
	public KeepAliveJob(ClientSession session) {
		super("Keep Alive"); //$NON-NLS-1$
		setSystem(true);
		setPriority(SHORT);
		schedule(RESTART_TIMEOUT);
		
	}

	/** {@inheritDoc} */
	@Override
	public IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Sending keep alive signal", -1); //$NON-NLS-1$
		
		/*
		Calendar now = Calendar.getInstance();
		logger.info("Sending keep alive signal: " + DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.MEDIUM).format(now.getTime()));
		*/		
		try{
			
			remoteService.getSessionService().keepAlive(session.getSessionId());
			eventBroker.post(IBrokerEvents.ONLINE_STATE, State.ONLINE);
				
		}
		catch(Exception e){
			e.printStackTrace();
			
			eventBroker.post(IBrokerEvents.ONLINE_STATE, State.OFFLINE);
			
			return Status.CANCEL_STATUS;
			
			
		}
		
		
		schedule(RESTART_TIMEOUT);
		return Status.OK_STATUS;
	}

}
