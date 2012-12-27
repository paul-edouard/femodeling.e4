package de.femodeling.e4.server.internal.transform;

import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.PartDTOImpl;
import de.femodeling.e4.server.internal.model.PartServerImpl;


public class PartTransformService {
	
	
	/**
	 * PartServerImpl to PartDTO
	 * 
	 * @param ent
	 * @return
	 */
	
	public static PartDTO transform(PartServerImpl ent){
		
		PartDTO p=new PartDTOImpl();
		
		p.setDescription(ent.getDescription());
		p.setName(ent.getName());
		p.setOwner(ent.getOwner());
		p.setPartId(ent.getPartId());
		p.setRevision(ent.getRevision());
		p.setStatus(ent.getStatus());
		p.setStatusText(ent.getStatusText());
		p.setType(ent.getType());
		p.setTypeText(ent.getTypeText());
		p.setAnsaModuleId(ent.getAnsaModuleId());
		//Weight list
		p.setWeightList(ent.getWeightList());
		p.setMaterialList(ent.getMaterialList());
		p.setRepresentationList(ent.getRepresentationList());
		p.setTranslation(ent.getTranslation());
		p.setCadFileName(ent.getCadFileName());
		p.setCadFileFound(ent.isCadFileFound());
		
		//Lock entity
		p.setLockableId(ent.getLockableId());
		p.setSessionId(ent.getSessionId());
		
		return p;
	}
	
	
	public static List<PartDTO> transformServerList(List<PartServerImpl> ent_l){
		List<PartDTO> p_l=new LinkedList<PartDTO>();
		
		for(PartServerImpl ent:ent_l){
			p_l.add(transform(ent));
		}
		
		return p_l;
	}
	
	
	/**
	 * PartDTO to PartServerImpl
	 * 
	 * @param ent
	 * @return
	 */
	public static PartServerImpl transform(PartDTO ent){
		
		PartServerImpl p=new PartServerImpl();
		
		p.setDescription(ent.getDescription());
		p.setName(ent.getName());
		p.setOwner(ent.getOwner());
		p.setPartId(ent.getPartId());
		p.setRevision(ent.getRevision());
		p.setStatus(ent.getStatus());
		p.setStatusText(ent.getStatusText());
		p.setType(ent.getType());
		p.setTypeText(ent.getTypeText());
		p.setAnsaModuleId(ent.getAnsaModuleId());
		//Weight list
		p.setWeightList(ent.getWeightList());
		p.setMaterialList(ent.getMaterialList());
		p.setRepresentationList(ent.getRepresentationList());
		p.setTranslation(ent.getTranslation());
		p.setCadFileName(ent.getCadFileName());
		p.setCadFileFound(ent.isCadFileFound());
		
		//Lock entity
		p.setLockableId(ent.getLockableId());
		p.setSessionId(ent.getSessionId());
		
		return p;
		
	}
	
	public static List<PartServerImpl> transformDTOList(List<PartDTO> ent_l){
		List<PartServerImpl> p_l=new LinkedList<PartServerImpl>();
		
		for(PartDTO ent:ent_l){
			p_l.add(transform(ent));
		}
		
		return p_l;
	}
	
	
	

}
