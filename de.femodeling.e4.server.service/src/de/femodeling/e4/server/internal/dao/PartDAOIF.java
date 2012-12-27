package de.femodeling.e4.server.internal.dao;

import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.server.internal.model.PartServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;

public interface PartDAOIF {
	
	/**
	 * 
	 * save a given part in a project
	 * 
	 * @param part
	 * @param proj
	 */
	public boolean savePart(PartServerImpl part, ProjectServerImpl proj);
	
	
	/**
	 * 
	 * save all the part from the given list in a project
	 * 
	 * @param part_l
	 * @param proj
	 * @return the list of parts that couldn't be saved
	 */
	public List<PartServerImpl> saveParts(List<PartServerImpl> part_l, ProjectServerImpl proj);
	
	
	/**
	 * return all parts from a given project that belong to a given type
	 * 
	 * @param proj
	 * @return
	 */
	public LinkedList<PartServerImpl> getPartsFromProject(ProjectServerImpl proj,Part.Type type);
	
	/**
	 * return the number of parts saved in a project
	 * 
	 * @param proj
	 * @return
	 */
	public int getNumberOfParts(ProjectServerImpl proj, Part.Type type);
	
	
	/**
	 * return all parts from a given project
	 * 
	 * @param proj
	 * @return
	 */
	public LinkedList<PartServerImpl> getPartsFromProject(ProjectServerImpl proj);
	
	
}
