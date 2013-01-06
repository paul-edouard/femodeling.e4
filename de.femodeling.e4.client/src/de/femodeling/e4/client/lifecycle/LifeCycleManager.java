package de.femodeling.e4.client.lifecycle;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.osgi.service.event.Event;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.ui.dialog.SecureLoginDialog;
import de.femodeling.e4.client.ui.preferences.GeneralPreferences;
import de.femodeling.e4.model.core.ConnectionDetails;
import de.femodeling.e4.server.service.RemoteService;
import de.femodeling.e4.ui.dataprovider.registery.IRegistery;

public class LifeCycleManager {
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.AUTO_LOGIN)
	Boolean auto_login;
	
	@Inject
	private ClientSession session;
	
	private RemoteService remoteService;
	
	@Inject
	private IRegistery registery;
	
	private static final long WaitingTime = 10;
	
	/*
	 * save a close workbench request
	 */
	boolean closeCalled=false;
	
	@PostContextCreate
	void postContextCreate(RemoteService remoteService, IEclipseContext parent){
		//BasicConfigurator.configure();
		this.remoteService=remoteService;
		//create and start the login interface
		closeCalled=login(parent);
		
		//save the session in the eclipse context
		parent.set(ClientSession.class,session);
		
		//save the Remote Service in the eclipse context
		//parent.set(RemoteService.class,this.remoteService);
		
	}
	
	
	@Inject
	@Optional
	public void partActivated(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event,
			IWorkbench workbench){
		//System.out.println(event.toString());
		if(workbench!=null && !closeCalled){
				//System.out.println("close ready!!");
				workbench.close();closeCalled=true;
		}
		
		//registery.initialize();
		
		
	}
	
	
	
	private boolean login(IEclipseContext parent) {
		boolean firstTry = true;
		//create the loginDialog within the current context
		SecureLoginDialog loginDialog = ContextInjectionFactory.make(
				SecureLoginDialog.class, parent);
		loginDialog.setShowError(!firstTry);

		while (session.getSessionId() == null) {
			
			ConnectionDetails details = loginDialog.getConnectionDetails();
			if (!auto_login || !firstTry ) {
				loginDialog.setShowError(!firstTry);
				if (loginDialog.open() != Window.OK)
					return false;
				details = loginDialog.getConnectionDetails();
			}
			firstTry = false;
			session.setConnectionDetails(details);
			connectWithProgress();
		}
		return true;
	}

	private void connectWithProgress() {
		ProgressMonitorDialog progress = new ProgressMonitorDialog(null);
		progress.setCancelable(true);
		try {
			progress.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException {
					// connect and login
					connectAndLogin(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Establishes the connection to the server and logs in. The connection
	 * details must have already been set.
	 */
	public void connectAndLogin(final IProgressMonitor monitor) {

		try {
			// monitor.beginTask("Connecting...", IProgressMonitor.UNKNOWN);
			monitor.beginTask("Connecting...", 7);

			monitor.worked(1);

			ConnectionDetails connectionDetails = session
					.getConnectionDetails();
			
			
			monitor.subTask("Contacting " + connectionDetails.getServer()
					+ "...");
			remoteService.init(connectionDetails.getServer());

			Thread.sleep(WaitingTime);
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			// try to call the user service
			monitor.worked(1);
			monitor.subTask("Calling user service" + "...");
			session.setSessionId(remoteService.getUserService().internalLogin(
									connectionDetails.getUserId(),
									connectionDetails.getPassword()));

			// logger.info("Session Id: "+ClientSession.INSTANCE.getSessionId());

			// check if the login was successful
			if (session.getSessionId() != null) {
				// login succeeded
				// set the context information
				remoteService.setSessionId(session.getSessionId());
				remoteService.setUserId(connectionDetails.getUserId());

			}

			Thread.sleep(WaitingTime);
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			// try to call the session service
			monitor.worked(1);
			monitor.subTask("Calling session service" + "...");
			remoteService.getSessionService();

			Thread.sleep(WaitingTime);
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			// try to call the project service
			monitor.worked(1);
			monitor.subTask("Calling project service" + "...");
			remoteService.getProjectService();

			Thread.sleep(WaitingTime);
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			// try to call the project service
			monitor.worked(1);
			monitor.subTask("Calling lock service" + "...");
			remoteService.getLockService();

			Thread.sleep(WaitingTime);
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			// try to call the project service
			monitor.worked(1);
			monitor.subTask("Calling message service" + "...");
			remoteService.getMessageService();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// if (connection != null)
			// connection.removePacketWriterListener(progressPacketListener);
			monitor.done();
		}
	}

}
