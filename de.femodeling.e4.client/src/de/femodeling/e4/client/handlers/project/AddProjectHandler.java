 
package de.femodeling.e4.client.handlers.project;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.dialog.ProjectDialog;
import de.femodeling.e4.model.core.Project;

public class AddProjectHandler {
	
	private boolean canExcecute=false;
	private ProjectClientImpl selectedProject;
	
	
	@Inject
	IUserProvider userProvider;
	
	@Execute
	public void execute(Shell shell,IProjectProvider projectProvider) {
		if(selectedProject==null){
			MessageDialog.openError(shell, "Selection error", "No project selected");
			return;
		}
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
				d.getPro().setType(d.getParentPro().getNextType());
				projectProvider.putData(d.getPro());
				
		}
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return canExcecute;
	}
	
	
	
	@Inject
	public void analyseSelection(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) 
	ISelection selection){
		if(selection instanceof IStructuredSelection){
			IStructuredSelection sel=(IStructuredSelection) selection;
			if(sel.size()==1 && sel.getFirstElement() instanceof ProjectClientImpl){
				selectedProject=(ProjectClientImpl)sel.getFirstElement();
				canExcecute=true;
				return;
			}
		}
		
		selectedProject=null;
		canExcecute=false;
	}
	
	
		
}