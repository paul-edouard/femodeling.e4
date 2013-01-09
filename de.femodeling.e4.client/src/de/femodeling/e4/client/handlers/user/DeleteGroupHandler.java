package de.femodeling.e4.client.handlers.user;
 

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.parts.UsersPart;
import de.femodeling.e4.model.core.User;

public class DeleteGroupHandler {
	
	private boolean canExcecute=false;
	private UserClientGroup selectedGroup;
	
	@Inject
	IUserProvider provider;
	
	@Inject
	private IEventBroker eventBroker;
	
	
	
	@Execute
	public void execute(Shell shell) {
		if(selectedGroup==null){
			MessageDialog.openError(shell, "Selection error", "No group selected");
			return;
		}
		
		boolean res = MessageDialog.openConfirm(shell, "Delete User?",
				"Do you really want to delete the project: \""+selectedGroup.getName()+"\"?");
		
		if(!res)return;
		
	
		
		if (provider.removeData(
				selectedGroup.getGroup())) {
			
			eventBroker.post(IBrokerEvents.USER_REMOVE, selectedGroup);
			
		} else {
			MessageDialog.openWarning(shell,
					"Delete group Error",
					"Cannot delete the group"+selectedGroup.getName());
		}
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return (provider.getCurrentUser().hasRole(User.ADMIN)) && canExcecute;
	}
	
	
	@Inject
	public void analyseSelection(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) 
	ISelection selection){
	
		if(selection instanceof IStructuredSelection){
			IStructuredSelection sel=(IStructuredSelection) selection;
			if(sel.size()==1 && sel.getFirstElement() instanceof UserClientGroup){
				selectedGroup=(UserClientGroup)sel.getFirstElement();
				if(!selectedGroup.getName().equals(UsersPart.ALL_USERS_GROUP) 
						&& !selectedGroup.getName().equals(UsersPart.ROOT_GROUP)){
					canExcecute=true;
					return;
				}
				
			}
		}
		
		selectedGroup=null;
		canExcecute=false;
	}
		
}