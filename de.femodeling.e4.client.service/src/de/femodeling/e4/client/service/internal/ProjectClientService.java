package de.femodeling.e4.client.service.internal;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.core.ProjectClientServiceIF;
import de.femodeling.e4.client.service.internal.transform.ProjectTransformService;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.service.RemoteService;


public class ProjectClientService implements ProjectClientServiceIF {
	
	
	static final long serialVersionUID=1L;
	

	RemoteService remoteService;
	

	public ProjectClientService(RemoteService remoteService) {
		super();
		this.remoteService = remoteService;
	}


	@Override
	public ProjectClientImpl addProject(ProjectClientImpl parent, ProjectClientImpl entry) {
		 //Transformation!!
		ProjectDTO parent_dto=ProjectTransformService.transformClient(parent);
		ProjectDTO entry_dto=ProjectTransformService.transformClient(entry);
		
		ProjectDTO newPro=remoteService.
				getProjectService().addProject(parent_dto, entry_dto);
		
		if(newPro!=null){
			//ProjectsEntry.setProjectsEntryRegister(projectRemoteService.getAllProjects());
			parent.addChild(ProjectTransformService.transformClient(newPro, false));
				
			return ProjectTransformService.transformClient(newPro, false);
		}
		return null;
	}

	
	
	@Override
	public boolean removeProject(ProjectClientImpl pro) {
		if(pro==null)return false;
		
		ProjectDTO entry_dto=ProjectTransformService.transformClient(pro);
		
		return remoteService.
				getProjectService().deleteProject(entry_dto);
	}

	
	
	@Override
	public boolean renameProject(ProjectClientImpl pro, String newName) {
		ProjectDTO entry_dto=ProjectTransformService.transformClient(pro);
		
		return remoteService.
				getProjectService().renameProject(entry_dto,  newName);
	}



	@Override
	public boolean updateProject(ProjectClientImpl pro) {
		
		ProjectDTO entry_dto=ProjectTransformService.transformClient(pro);
		
		return remoteService.
				getProjectService().updateProject(entry_dto);
	}
	
	public int getNumberOfParts(ProjectClientImpl pro, Part.Type type){
		ProjectDTO entry_dto=ProjectTransformService.transformClient(pro);
		
		return remoteService.
				getProjectService().getNumberOfParts(entry_dto, type);
				
	}
	
	
	public boolean hasAssembly(ProjectClientImpl pro,Assembly.Type type){
		ProjectDTO entry_dto=ProjectTransformService.transformClient(pro);
		
		return remoteService.
				getProjectService().hasAssembly(entry_dto, type);
	}
	
	public boolean hasConnections(ProjectClientImpl pro){
		ProjectDTO entry_dto=ProjectTransformService.transformClient(pro);
		return !remoteService.
				getProjectService().getVtaElements(entry_dto).isEmpty();
	}
	
	

}
