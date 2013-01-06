package de.femodeling.e4.client.service.core;

import java.io.Serializable;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;


public interface ProjectClientServiceIF extends Serializable {
	
	public ProjectClientImpl addProject(ProjectClientImpl parent,ProjectClientImpl entry);
	
	public boolean removeProject(ProjectClientImpl pro);
	
	public boolean renameProject(ProjectClientImpl pro,String newName);
	
	public boolean updateProject(ProjectClientImpl pro);
	
	public int getNumberOfParts(ProjectClientImpl pro, Part.Type type);
	
	public boolean hasAssembly(ProjectClientImpl pro,Assembly.Type type);
	
	public boolean hasConnections(ProjectClientImpl pro);
	
}
