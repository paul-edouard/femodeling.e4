package de.femodeling.e4.client.service.internal;

import java.util.List;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.ConnectionElementClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.core.ConnectionClientServiceIF;
import de.femodeling.e4.client.service.internal.transform.ProjectTransformService;
import de.femodeling.e4.client.service.internal.transform.VtaElementTransformService;
import de.femodeling.e4.model.dto.ConnectionElementDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.service.RemoteService;


public class ConnectionClientService implements ConnectionClientServiceIF {
	
	
	private static Logger logger = Logger.getLogger(ConnectionClientService.class);
	
	RemoteService remoteService;
	
	public ConnectionClientService(RemoteService remoteService) {
		super();
		this.remoteService = remoteService;
	}


	public List<ConnectionElementClientImpl> addConnections(List<ConnectionElementClientImpl> e_l,ProjectClientImpl pro){
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		List<ConnectionElementDTO> e_l_dto=VtaElementTransformService.transformClientList(e_l);
		
		List<ConnectionElementDTO> e_l_dto_error=remoteService.
				getProjectService().addVtaElements(e_l_dto, p_dto);
		
		return VtaElementTransformService.transformClientDTOList(e_l_dto_error);
		
	}
	
	
	public List<ConnectionElementClientImpl> getConnections(ProjectClientImpl pro){
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		List<ConnectionElementDTO> e_l_dto=remoteService.
				getProjectService().getVtaElements(p_dto);
		logger.info("Number of vta elements:" + e_l_dto.size());
		
		return VtaElementTransformService.transformClientDTOList(e_l_dto);
		
	}

}
