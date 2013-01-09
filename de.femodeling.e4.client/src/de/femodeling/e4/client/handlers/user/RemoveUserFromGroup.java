 
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

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.model.core.UserClient;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.parts.UsersPart;
import de.femodeling.e4.model.core.User;

public class RemoveUserFromGroup {
	
	private boolean canExcecute=false;
	private UserClientImpl selectedUser;
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
		
		if(selectedUser==null){
			MessageDialog.openError(shell, "Selection error", "No user selected");
			return;
		}
		
		boolean res = MessageDialog.openConfirm(shell, "Remove User?",
				"Do you really want to remove the user: \""+selectedUser.getId()+"\" from the group: \""+selectedGroup.getName()+"\"?");
		
		if(!res)return;
		
		selectedGroup.removeUser(selectedUser);
		
		
		if (provider.updateData(
				selectedGroup.getGroup())!=null) {
			
			eventBroker.post(IBrokerEvents.USER_GROUP_UPDATE, selectedGroup);
			
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
			
			if(sel.size()==1 && sel.getFirstElement() instanceof UserClientImpl){
				selectedUser=(UserClientImpl)sel.getFirstElement();
				if(selectedUser.getType().equals(UserClient.TYPE_USER)){
					
					
					
					if(selectedUser.getParent()!=null && selectedUser.getParent() instanceof UserClientGroup){
						UserClientGroup group=(UserClientGroup) selectedUser.getParent();
						
						//System.out.println(selectedUser.getParent());
						
						if(!group.getName().equals(UsersPart.ALL_USERS_GROUP) 
								&& !group.getName().equals(UsersPart.ROOT_GROUP)){
							
							System.out.println(group);
							
							selectedGroup=group;
							canExcecute=true;
							return;
						}
						
						
					}
					
					
					
					
				}
			}
		}
		
		selectedGroup=null;
		selectedUser=null;
		canExcecute=false;
	}
	
		
}