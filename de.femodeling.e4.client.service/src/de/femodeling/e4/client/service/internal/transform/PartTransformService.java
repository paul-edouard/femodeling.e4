package de.femodeling.e4.client.service.internal.transform;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.PartClientImpl;
import de.femodeling.e4.model.core.part.Material;
import de.femodeling.e4.model.core.part.Representation;
import de.femodeling.e4.model.core.part.Weight;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.PartDTOImpl;

public class PartTransformService {
	
	
	
	public static PartClientImpl transformClient(PartDTO ent){
		PartClientImpl p=new PartClientImpl();
		
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
		p.setParameter(ent.getParameter());
		
		return p;
		
	}
	
	public static List<PartClientImpl> transformClientDTOList(List<PartDTO> ent_l){
		List<PartClientImpl> p_l=new LinkedList<PartClientImpl>();
		
		for(PartDTO ent:ent_l){
			p_l.add(transformClient(ent));
		}
		
		return p_l;
	}
	
	
	
	public static PartDTO transformClient(PartClientImpl ent){
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
		p.setParameter(ent.getParameter());
		
		return p;
		
	}
	
	public static List<PartDTO> transformClientList(List<PartClientImpl> ent_l){
		List<PartDTO> p_l=new LinkedList<PartDTO>();
		
		for(PartClientImpl ent:ent_l){
			p_l.add(transformClient(ent));
		}
		
		return p_l;
	}
	
	
	public static PartClientImpl createCopy(PartClientImpl ent){
		PartClientImpl p=new PartClientImpl();
		
		p.setDescription(ent.getDescription());
		p.setName(ent.getName());
		p.setOwner(ent.getOwner());
		p.setPartId(ent.getPartId());
		p.setProjectId(ent.getProjectId());
		p.setRevision(ent.getRevision());
		p.setStatus(ent.getStatus());
		p.setStatusText(ent.getStatusText());
		p.setType(ent.getType());
		p.setTypeText(ent.getTypeText());
		p.setAnsaModuleId(ent.getAnsaModuleId());
		p.setCadFileName(new File(ent.getCadFileName()).getName());
		p.setCadFileFound(ent.isCadFileFound());
		
		//File
		
		//Lock entity
		p.setLockableId(ent.getLockableId());
		p.setSessionId(ent.getSessionId());
		p.setParameter(ent.getParameter());
		
		//Weight list
		for(Weight w:ent.getWeightList())
			p.getWeightList().add(w.createCopy());
		
		//Material List
		for(Material m:ent.getMaterialList())		
			p.getMaterialList().add(m.createCopy());
		
		//Representation List
		for(Representation rep:ent.getRepresentationList())
		p.getRepresentationList().add(rep.createCopy());
		
		//Translation
		p.setTranslation(ent.getTranslation().createCopy());
		
		
		return p;
	}
	
	public static void copyData(PartClientImpl input, PartClientImpl target){
		
		target.setDescription(input.getDescription());
		target.setName(input.getName());
		target.setOwner(input.getOwner());
		target.setPartId(input.getPartId());
		target.setProjectId(input.getProjectId());
		target.setRevision(input.getRevision());
		target.setStatus(input.getStatus());
		target.setStatusText(input.getStatusText());
		target.setType(input.getType());
		target.setTypeText(input.getTypeText());
		target.setAnsaModuleId(input.getAnsaModuleId());
		//input.setCadFileName(target.getCadFileName());
		target.setCadFileFound(input.isCadFileFound());
		//Lock entity
		target.setLockableId(input.getLockableId());
		target.setSessionId(input.getSessionId());
		target.setParameter(input.getParameter());
		
		//Weight list
		target.setWeightList(input.getWeightList());
		
		//Material List
		target.setMaterialList(input.getMaterialList());
		
		//Representation List
		target.setRepresentationList(input.getRepresentationList());
		
		//Translation
		target.setTranslation(input.getTranslation());
		
	}
	

}
