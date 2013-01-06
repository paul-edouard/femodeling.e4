package de.femodeling.e4.client.service.core;

import de.femodeling.e4.client.model.AssemblyClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.assembly.Assembly;



public interface AssemblyClientServiceIF {
	

	
	
	public void addSubAssembly(AssemblyClientImpl ass, ProjectClientImpl pro, Assembly.Type type);
	
	
	public boolean saveAssemby(AssemblyClientImpl ass,ProjectClientImpl pro);
	
	public AssemblyClientImpl getAssembly(ProjectClientImpl pro,Assembly.Type type);
	

}
