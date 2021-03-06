package de.femodeling.e4.server.internal.dao;

import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.server.internal.model.AssemblyServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;



public interface AssemblyDAOIF {
	
	/**
	 * add a subassembly to a project
	 * 
	 * @param node
	 * @param proj
	 * @return
	 */
	public boolean addSubAssembly(AssemblyServerImpl ass,ProjectServerImpl proj, Assembly.Type type);
	
	
	/**
	 * return the assembly of the project
	 * 
	 * @param proj
	 * @return the current assembly
	 */
	public AssemblyServerImpl getAssembly(ProjectServerImpl proj,Assembly.Type type);
	
	public boolean hasAssembly(ProjectServerImpl proj,Assembly.Type type);
	
	/**
	 * save the assembly into the project
	 * 
	 * @param ass
	 * @param proj
	 * @return
	 */
	public boolean saveAssembly(AssemblyServerImpl ass,ProjectServerImpl proj);
	
}
