package de.femodeling.e4.server.internal.dao;


import java.util.List;

import de.femodeling.e4.server.internal.model.ProjectServerImpl;


public interface ProjectDAOIF {
	
	/**
	 * add a path to the root dirs
	 * 
	 * @param path
	 */
	public void addRootDir(String path);
	
	
	/**
	 * save the given project
	 * 
	 * @param p
	 * @return
	 */
	public boolean save(ProjectServerImpl p);
	
	
	/**
	 * tries to delete a project
	 * 
	 * @param p
	 * @return
	 */
	public boolean delete(ProjectServerImpl p);
	
	
	/**
	 * rename a project
	 * 
	 * @param p
	 * @param name
	 * @return
	 */
	public boolean rename(ProjectServerImpl p, String name);
	
	
	/**
	 * return all the sub projects
	 * 
	 * @param p
	 * @return
	 */
	public List<ProjectServerImpl> getAllSubProjects(ProjectServerImpl p);
	
	
	/**
	 * create the dummy project root
	 * 
	 * @return
	 */
	public ProjectServerImpl createRoot();

}
