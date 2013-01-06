 
package de.femodeling.e4.client.handlers.project;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.IProjectProvider;

public class DeleteProjectHandler {
	
	
	private boolean canExcecute=false;
	private ProjectClientImpl selectedProject;
	
	
	@Execute
	public void execute(Shell shell,IProjectProvider projectProvider) {
		if(selectedProject==null){
			MessageDialog.openError(shell, "Selection error", "No project selected");
			return;
		}
		
		boolean res = MessageDialog.openConfirm(shell, "Delete Project?",
				"Do you really want to delete the project: \""+selectedProject.getName()+"\"?");
		
		if(!res)return;
		
		if(selectedProject!=null){
			
			if(!projectProvider.removeData(selectedProject)){
				MessageDialog.openWarning(shell, "Delete Project Error", "Cannot delete the project: "+selectedProject.getName());
			}
			else{
				selectedProject.getParent().removeChild(selectedProject);
				selectedProject=null;
			}
			
		}
		
	}
	
 
	
	@CanExecute
	public boolean canExecute(ESelectionService service) {
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