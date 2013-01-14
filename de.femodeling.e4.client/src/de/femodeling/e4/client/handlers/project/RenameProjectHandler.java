 
package de.femodeling.e4.client.handlers.project;

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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.dialog.ProjectDialog;

public class RenameProjectHandler {
	
	private boolean canExcecute=false;
	private ProjectClientImpl selectedProject;
	
	
	@Inject
	IUserProvider userProvider;
	
	@Inject
	private IEventBroker eventBroker;
	
	
	@Execute
	public void execute(Shell shell,IProjectProvider projectProvider,IClientService service) {
		if(selectedProject==null){
			MessageDialog.openError(shell, "Selection error", "No project selected");
			return;
		}
			//group=ProjectProvider.getRoot();
		ProjectClientImpl copyPro=selectedProject.createCopy();
		
		
		//TODO Lock and unlock the project
		service.getLockClientService().lockEntity(selectedProject);
		if(!selectedProject.islocked()){
			MessageDialog.openError(shell, "Lock project error", "cannot lock the project");
			return;
		}
		
		ProjectDialog d = new ProjectDialog(shell, copyPro,ProjectDialog.Type.RENAME);
		int code = d.open();
		
		
		if (code == Window.OK) {
			
			//System.out.println("New Name:"+copyPro.getName());
			//System.out.println("Old Name:"+selectedProject.getName());
		
			if(!copyPro.getName().equals(selectedProject.getName())){
				if(!projectProvider.rename(selectedProject,copyPro.getName() )){
					MessageDialog.openError(shell, "Save project Error", "Cannot rename the project");
				}
				else{
					selectedProject.setName(copyPro.getName(),true);
					eventBroker.post(IBrokerEvents.PROJECT_UPDATE,selectedProject.getLockableId() );
				}
				
			}
			
				
		}
		
		//Unlock the entity
		service.getLockClientService().unlockEntity(selectedProject);
	}
	
	
	
	@CanExecute
	public boolean canExecute() {
		return canExcecute && !selectedProject.islocked();
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