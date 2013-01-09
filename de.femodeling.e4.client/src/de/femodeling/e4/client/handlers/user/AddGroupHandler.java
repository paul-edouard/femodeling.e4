package de.femodeling.e4.client.handlers.user;
 

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.model.core.UserClient;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.dialog.UserGroupDialog;
import de.femodeling.e4.model.core.User;

public class AddGroupHandler {
	
	
	@Inject
	IUserProvider provider;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Execute
	public void execute(Shell shell) {
		UserClientImpl u = new UserClientImpl();
		UserGroupDialog d = new UserGroupDialog(shell, u);
		//d.setMessage("Create new group", IMessageProvider.INFORMATION);
		
		int code = d.open();

		if (code == Window.OK) {
			u.setType(UserClient.TYPE_GROUP);
			u.setId(u.getLockableId());
			System.out.println(u);
			
			if (provider.putData(u) != null) {
				//System.out.println("New User Created: "+u);
				eventBroker.post(IBrokerEvents.USER_GROUP_ADD, u);
				
				
			} else {
				MessageDialog.openWarning(shell, "Add user Error",
						"Cannot save the user");
			}
			
		}
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return (provider.getCurrentUser().hasRole(User.ADMIN));
	}
		
}