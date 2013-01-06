package de.femodeling.e4.client.service.internal;

import de.femodeling.e4.client.model.AssemblyClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.core.AssemblyClientServiceIF;
import de.femodeling.e4.client.service.internal.transform.AssemblyTransformationService;
import de.femodeling.e4.client.service.internal.transform.ProjectTransformService;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.dto.AssemblyDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.service.RemoteService;


public class AssemblyClientService implements AssemblyClientServiceIF {
	
	
	
	RemoteService remoteService;
	
	public AssemblyClientService(RemoteService remoteService) {
		super();
		this.remoteService = remoteService;
	}

	@Override
	public void addSubAssembly(AssemblyClientImpl ass, ProjectClientImpl pro, Assembly.Type type) {
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		AssemblyDTO a_dto=AssemblyTransformationService.transformClient(ass);
				
		remoteService.
		getProjectService().addSubAssembly(a_dto, p_dto, type);

	}

	@Override
	public boolean saveAssemby(AssemblyClientImpl ass, ProjectClientImpl pro) {
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		AssemblyDTO a_dto=AssemblyTransformationService.transformClient(ass);
				
		return remoteService.
						getProjectService().saveAssembly(a_dto, p_dto);
	}

	@Override
	public AssemblyClientImpl getAssembly(ProjectClientImpl pro, Assembly.Type type) {
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		
		AssemblyDTO ass=remoteService.
				getProjectService().getAssemblyFromProject(p_dto,type);
		ass.setType(type);
		
		return AssemblyTransformationService.transformClient(ass);
	}

}
