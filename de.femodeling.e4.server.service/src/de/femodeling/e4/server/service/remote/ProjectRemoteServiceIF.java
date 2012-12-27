package de.femodeling.e4.server.service.remote;

import java.io.Serializable;
import java.util.List;

import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.dto.AssemblyDTO;
import de.femodeling.e4.model.dto.ConnectionElementDTO;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.ProjectDTO;

/*
import java.util.HashMap;
import java.util.LinkedList;

import de.porsche.femodeling.core.assembly.part.PmcPart;
import de.porsche.femodeling.core.assembly.structure.PmcAssembly;
import de.porsche.femodeling.core.assembly.structure.PmcNode;
*/



public interface ProjectRemoteServiceIF extends Serializable
{
	
	 /** 
	    * Provide capital of state whose name is provided. 
	    *  
	    * @param stateName Name of state whose capital is desired. 
	    * @return Capital of the specified state; null if not found. 
	    */ 
	/*
	   public HashMap<Long, ProjectsEntry> getAllProjects();
	  */ 
	   /**
	    * 
	    * tries to add a new project to the current project list
	    * 
	    * @param pro
	    * @param dir
	    * @return
	    */
	   public ProjectDTO addProject(ProjectDTO parent,ProjectDTO new_project);
	   
	   
	   /**
	    * 
	    * tries to delete a project
	    * 
	    * @param projectId
	    * @return
	    */
	   public boolean deleteProject(ProjectDTO p);
	   
	   
	   /**
	    * 
	    * tries to rename a project
	    * 
	    * @param projectId
	    * @param newProName
	    * @return
	    */
	   public boolean renameProject(ProjectDTO p,String newProName);
	   
	   
	   public boolean updateProject(ProjectDTO p);
	   
	   
	   /**
	    * return the root project
	    */
	   public ProjectDTO getRootProject();
	   
	   
	   
	   /***********************************
		 *                                 *
		 *		      PARTS                *
		 *                                 *
		 ***********************************/
	   
	   
	  /**
	   * add part to a project
	   * 
	   * @param p_l
	   * @param proj
	   * @return the list of the parts that couldn't be saved
	   */
	//   public List<PartDTO> addParts(List<PartDTO> p_l,ProjectDTO proj);
	   
	   public String getCADFileName(PartDTO part,ProjectDTO proj);
	   
	   public boolean addPart(PartDTO part,ProjectDTO proj);
	   
	   public boolean updatePart(PartDTO part,ProjectDTO proj);
	   
	   /**
	    * return all the parts from a project
	    * 
	    * @param proj
	    * @return
	    */
	   public List<PartDTO> getPartsFromProject(ProjectDTO proj);
	   
	   
	   /**
	    * return the parts from a project of a given type
	    * 
	    * @param proj
	    * @return
	    */
	   public List<PartDTO> getPartsFromProject(ProjectDTO proj, Part.Type type);
	   
	   public int getNumberOfParts(ProjectDTO proj, Part.Type type);
	   
	   /***********************************
		 *                                 *
		 *		   ASSEMBLIES              *
		 *                                 *
		 ***********************************/	   
	   
	   
	   
	   /**
	    * add a sub assembly to a project
	    * @param node
	    * @param proj
	    * @return
	    */
	   public boolean addSubAssembly(AssemblyDTO ass, ProjectDTO proj, Assembly.Type type);
	   
	   
	   /**
	    * return the assembly from a project
	    * 
	    * @param proj
	    * @return
	    */
	   public AssemblyDTO getAssemblyFromProject(ProjectDTO proj,Assembly.Type type);
	   
	   /**
	    * try to save the assembly into the project
	    * 
	    * @param ass
	    * @param proj
	    */
	   public boolean saveAssembly(AssemblyDTO ass,ProjectDTO proj);
	   
	   
	   /**
	    * test if the project contains a module structure
	    * 
	    * @param proj
	    * @return
	    */
	   public boolean hasAssembly(ProjectDTO proj,Assembly.Type type);
	   
	   /***********************************
		 *                                 *
		 *		   CONNECTIONS             *
		 *                                 *
		 ***********************************/
	   
	   /**
	    * 
	    * @param e
	    * @param proj
	    * @return
	    */
	   public boolean saveVtaElement(ConnectionElementDTO e,ProjectDTO proj);
	   
	   /**
	    * 
	    * @param e_l
	    * @param proj
	    * @return
	    */
	   public List<ConnectionElementDTO> addVtaElements(List<ConnectionElementDTO> e_l,ProjectDTO proj);
	   
	   
	   /**
	    * 
	    * @param proj
	    * @return
	    */
	   public List<ConnectionElementDTO> getVtaElements(ProjectDTO proj);
	   
}
