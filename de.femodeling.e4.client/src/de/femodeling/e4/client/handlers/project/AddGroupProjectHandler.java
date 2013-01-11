 
package de.femodeling.e4.client.handlers.project;

import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.dialog.ProjectDialog;
import de.femodeling.e4.model.core.Project;
import de.femodeling.e4.model.core.User;

public class AddGroupProjectHandler {
	
	private ProjectClientImpl selectedProject;
	
	
	@Inject
	IUserProvider userProvider;
	
	
	@Execute
	public void execute(Shell shell,IProjectProvider projectProvider) {
		
		selectedProject=projectProvider.getRoot();
		
		//group=ProjectProvider.getRoot();
		
		ProjectDialog d = new ProjectDialog(shell, selectedProject,ProjectDialog.Type.CREATE);
		int code = d.open();
		
		
		if (code == Window.OK) {
			
				d.getPro().setParent(d.getParentPro());
				d.getPro().setCreationUser(userProvider.getCurrentUser().getId());
				d.getPro().setCreationDate(new Date().toString());
				d.getPro().setState(Project.State.STARTED);
				if(d.getParentPro().getGroup()!=null && !d.getParentPro().getGroup().isEmpty()){
					d.getPro().setGroup(d.getParentPro().getGroup());
				}
				else{
					d.getPro().setGroup(userProvider.getCurrentUser().getDefaultGroup());
				}
				d.getPro().setType(Project.Type.GROUP);
				projectProvider.putData(d.getPro());
				
		}
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return (userProvider.getCurrentUser().hasRole(User.ADMIN));
	}
	
	
	
		
}