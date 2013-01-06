package de.femodeling.e4.client.service.internal;

import java.util.List;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.PartClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.core.PartClientServiceIF;
import de.femodeling.e4.client.service.internal.transform.PartTransformService;
import de.femodeling.e4.client.service.internal.transform.ProjectTransformService;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.service.RemoteService;



public class PartClientService implements PartClientServiceIF {
	
	private static Logger logger = Logger.getLogger(PartClientService.class);
	
	RemoteService remoteService;
	
	
	public PartClientService(RemoteService remoteService) {
		super();
		this.remoteService = remoteService;
	}



	public String getCADFileName(PartClientImpl part,ProjectClientImpl pro){
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		PartDTO part_dto=PartTransformService.transformClient(part);
		
		return remoteService.getProjectService().getCADFileName(part_dto, p_dto);
		
	}
	
	
	
	public boolean addPart(PartClientImpl part,ProjectClientImpl pro){
		
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		PartDTO part_dto=PartTransformService.transformClient(part);
		
		return remoteService.getProjectService().addPart(part_dto, p_dto);
		
	}
	
	public boolean updatePart(PartClientImpl part,ProjectClientImpl pro){
		
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		PartDTO part_dto=PartTransformService.transformClient(part);
		
		return remoteService.getProjectService().updatePart(part_dto, p_dto);
	}
	
	
	public List<PartClientImpl> getParts(ProjectClientImpl pro){
			//Transformation!!
			ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
			
			List<PartDTO> part_dto_l=remoteService.
					getProjectService().getPartsFromProject(p_dto);
			logger.info("Number of parts:"+part_dto_l.size());
			
			return PartTransformService.transformClientDTOList(part_dto_l);
		
	}
	
	public List<PartClientImpl> getParts(ProjectClientImpl pro,Part.Type type){
		//Transformation!!
		ProjectDTO p_dto=ProjectTransformService.transformClient(pro);
		
		List<PartDTO> part_dto_l=remoteService.
				getProjectService().getPartsFromProject(p_dto, type);
		//for(PartDTO p:part_dto_l)
		//	logger.info("Number of weight:"+p.getWeightList().size());
		
		logger.info("Number of parts:"+part_dto_l.size());
		
		return PartTransformService.transformClientDTOList(part_dto_l);
	
	}

}
