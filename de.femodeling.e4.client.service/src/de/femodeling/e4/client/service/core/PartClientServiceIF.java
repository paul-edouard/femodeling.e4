package de.femodeling.e4.client.service.core;

import java.util.List;

import de.femodeling.e4.client.model.PartClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.part.Part;



public interface PartClientServiceIF {
	
	
	//public void addPartsToProject(List<PartDTO> partList, ProjectClientImpl pro);
	
	public String getCADFileName(PartClientImpl part,ProjectClientImpl proj);
	
	public boolean addPart(PartClientImpl part,ProjectClientImpl pro);
	
	public boolean updatePart(PartClientImpl part,ProjectClientImpl pro);
	
	public List<PartClientImpl> getParts(ProjectClientImpl pro);
	
	public List<PartClientImpl> getParts(ProjectClientImpl pro,Part.Type type);
	
	


}
