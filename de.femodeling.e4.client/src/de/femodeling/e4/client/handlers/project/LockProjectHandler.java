 
package de.femodeling.e4.client.handlers.project;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.parts.ProjectsPart;
import de.femodeling.e4.model.core.User;

public class LockProjectHandler {
	

	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	private MApplication app;
	
	@Inject
	private ClientSession session;
	
	@Inject
	private IUserProvider userProvider;
	
	
	/*
	@Inject
	@Named("de.femodeling.e4.client.handledtoolitem.project.lock")
	MHandledToolItem item;
	*/
	
	private static final String icon_projectlocked="platform:/plugin/de.femodeling.e4.client/icons/lock.png";
	private static final String icon_projectUnlocked="platform:/plugin/de.femodeling.e4.client/icons/folder_database.png";
	
	private static final String icon_locked="platform:/plugin/de.femodeling.e4.client/icons/lock.png";
	private static final String icon_Unlocked="platform:/plugin/de.femodeling.e4.client/icons/lock_open.png";
	
	
	@Execute
	public void execute(IEclipseContext parent,IClientService service,Shell shell) {
		
		ProjectClientImpl currentProject=parent.get(ProjectClientImpl.class);
		
		if(!currentProject.islocked()){
			service.getLockClientService().lockEntity(currentProject);
			if(currentProject.islocked()){
				eventBroker.post(IBrokerEvents.PROJECT_UPDATE,currentProject.getLockableId() );
				refreshEditor(currentProject,true,modelService,app);
			}
			else{
				MessageDialog.openError(shell, "Lock project error", "the project \""+currentProject.getName()+"\" cannot be locked");
				return;
			}
			
		}
		else{
			if(service.getLockClientService().unlockEntity(currentProject)){
				eventBroker.post(IBrokerEvents.PROJECT_UPDATE,currentProject.getLockableId() );
				refreshEditor(currentProject,true,modelService,app);
			}
			else{
				MessageDialog.openError(shell, "Unlock project error", "the project \""+currentProject.getName()+"\" cannot be unlocked");
				return;
			}
		}
		
		
		//System.out.println("Current Project: "+currentProject.getName());
		//System.out.println("Current ToolItem: "+toolItem.getElementId());
		
	}
	
	
	public static void refreshEditor(ProjectClientImpl currentProject, boolean islocked,EModelService modelService, MApplication app){
		
		
		List<String> tags=new LinkedList<String>();
		tags.add(currentProject.getLockableId());
		
		List<MPart> parts=modelService.findElements(app,ProjectsPart.PART_EDITOR_ID, MPart.class,tags );
		for(MPart part:parts){
			
			part.setLabel(currentProject.getName());
			
			MHandledToolItem item=findToolItem(part,"de.femodeling.e4.client.handledtoolitem.project.lock");
			
			if(islocked){
				part.setIconURI(icon_projectlocked);
				if(item!=null){
					item.setSelected(true);
					item.setIconURI(icon_Unlocked);
				}
				
			}
			else{
				part.setIconURI(icon_projectUnlocked);
				if(item!=null){
					item.setSelected(false);
					item.setIconURI(icon_locked);
				}
			}
			
			
			
			
		}
	}
	
	
	
	private static  MHandledToolItem findToolItem(MPart part,String elementId){
		List<MToolBarElement> elements=part.getToolbar().getChildren();
		for(MToolBarElement el:elements){
			if(el.getElementId().equals(elementId) && el instanceof MHandledToolItem){
				MHandledToolItem tooItem=(MHandledToolItem) el;
				return tooItem;
			}
			
		}
		
		return null;
	}
	
	
	@CanExecute
	public boolean canExecute(IEclipseContext parent) {
		
		ProjectClientImpl currentProject=parent.get(ProjectClientImpl.class);
		if(userProvider.getCurrentUser().getId().equals(currentProject.getCreationUser()))
			return true;
		
		return (userProvider.getCurrentUser().hasRole(User.ADMIN));
	}
	
	
	@Inject
	private void updatePro(@Optional  @UIEventTopic(IBrokerEvents.PROJECT_UPDATE)String lockableId,IEclipseContext parent){
		ProjectClientImpl currentProject=parent.get(ProjectClientImpl.class);
		
		if(currentProject!=null && lockableId!=null){
		
		if(!lockableId.equals(currentProject.getLockableId()))return;
		refreshEditor(currentProject,currentProject.islocked(),modelService,app);
		}
		
	
	}
		
}