package de.femodeling.e4.server.service.remote;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.dto.AssemblyDTO;
import de.femodeling.e4.model.dto.ConnectionElementDTO;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.internal.model.AssemblyServerImpl;
import de.femodeling.e4.server.internal.model.ConnectionElementServerImpl;
import de.femodeling.e4.server.internal.model.PartServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;
import de.femodeling.e4.server.service.dao.AssemblyDAOImpl;
import de.femodeling.e4.server.service.dao.ConnectionElementDAOImpl;
import de.femodeling.e4.server.service.dao.PartDAOImpl;
import de.femodeling.e4.server.service.dao.ProjectDAOImpl;
import de.femodeling.e4.server.service.dao.RegisterServerDAOService;
import de.femodeling.e4.server.service.transform.AssemblyTransformationService;
import de.femodeling.e4.server.service.transform.ConnectionElementTransformService;
import de.femodeling.e4.server.service.transform.PartTransformService;
import de.femodeling.e4.server.service.transform.ProjectTransformService;



public class ProjectRemoteService implements ProjectRemoteServiceIF{
	
	
	static final long serialVersionUID=1L;
	
	public static final String PROJECT_DIR="ProjectDir";
	
	private static Logger logger = Logger.getLogger(ProjectRemoteService.class);

	
	public ProjectRemoteService(){
		//BasicConfigurator.configure();
		logger.info("------->Project Service started");
	}

	public void setConfigFiles(final Map<String, String> configFiles) {
		
		//initialize the Project DAO
		if(RegisterServerDAOService.INSTANCE.getProjectDAO()==null){
			RegisterServerDAOService.INSTANCE.setProjectDAO(new ProjectDAOImpl());
		}
		
		//initialize the part DAO
		if(RegisterServerDAOService.INSTANCE.getPartDAO()==null){
			RegisterServerDAOService.INSTANCE.setPartDAO(new PartDAOImpl());
		}
		
		//initialize the assembly DAO
		if(RegisterServerDAOService.INSTANCE.getAssemblyDAO()==null){
			RegisterServerDAOService.INSTANCE.setAssemblyDAO(new AssemblyDAOImpl());
		}
		
		//initialize the connection DAO
		if(RegisterServerDAOService.INSTANCE.getConnectionDAO()==null){
			RegisterServerDAOService.INSTANCE.setConnectionDAO(new ConnectionElementDAOImpl());
		}
		
		
		//logger.info("Start to read the config files");
		
		for(String key:configFiles.keySet()){
			
			//read the project file list
			if(key.equals(PROJECT_DIR)){
				
				if(configFiles.get(key)!=null && new File(configFiles.get(key)).exists()){
					//Add the Root dir	
					RegisterServerDAOService.INSTANCE.getProjectDAO().addRootDir(configFiles.get(key));
					
				}
				else{
					logger.warn("Cannot resolve of the Project File List location");
				}
			}
			
			
			
		}
		
		
	}
	
	
	  
	  public ProjectDTO getRootProject(){
		  ProjectServerImpl p=RegisterServerDAOService.INSTANCE.getProjectDAO().createRoot();
		  
		  return ProjectTransformService.transform(p, true);
	  }
	  
	  
	  /**
	   * call the project provider to add a new project
	   */
	  synchronized public ProjectDTO addProject(ProjectDTO parent,ProjectDTO new_project){
		  
		  logger.info("Try to add a new project");
		  logger.info("Parent: "+parent.toString());
		  logger.info("Child: "+new_project.toString());
		  //Transformation!!
		  ProjectServerImpl s_parent=ProjectTransformService.transform(parent);
		  ProjectServerImpl s_new_project=ProjectTransformService.transform(new_project);
		  
		  logger.info("Transfo Done");
		 
		  
		  //set the child path
		  s_new_project.setPath(s_parent.getEntriesDir()+File.separator+new_project.getName());
		  
		  //save the new project
		  if(RegisterServerDAOService.INSTANCE.getProjectDAO().save(s_new_project)){
			  //send a message: a new project is created!
			  RegisterServerDAOService.INSTANCE.getMessageDAO().addAddMessage(s_new_project,parent.getLockableId());
			  return ProjectTransformService.transform(s_new_project, false);
		  }
		  else return null;
		  
		  
	  }
	  
	  /**
	   * 
	   */
	  synchronized public boolean deleteProject(ProjectDTO p){
		  logger.info("Try to remove the project with id: "+p.getLockableId());
		  
		  //Transformation!!
		  ProjectServerImpl s_p=ProjectTransformService.transform(p);
		  
		  if(RegisterServerDAOService.INSTANCE.getProjectDAO().delete(s_p)){
			  //send a message: the project is deleted!
			  RegisterServerDAOService.INSTANCE.getMessageDAO().addRemoveMessage(s_p,"");
			  return true;
		  }
		  else return false;
		  
		  /*
		  ProjectsEntry pro=getProject(projectId);
		  if(pro==null)return false;
		  
		  if(pro.getEntries().length!=0)return false;
		  
		  return ProjectProvider.INSTANCE.removeProject(pro);
		  */
		  
	  }
	  
	  
	  
	  /**
	   * 
	   */
	  synchronized public boolean renameProject(ProjectDTO p,String newProName){
		  
		  logger.debug("Try to rename the project with id: "+p.getLockableId());
		  
		  //Transformation!!
		  ProjectServerImpl s_p=ProjectTransformService.transform(p);
		 
		  if(RegisterServerDAOService.INSTANCE.getProjectDAO().rename(s_p, newProName)){
			  //send a message: the project get a new name
			  RegisterServerDAOService.INSTANCE.getMessageDAO().addUpdateMessage(s_p,"");
			  return true;
		  }
		  else return false;
		  
	  }
	  
	  
	  
	  
	   @Override
	public boolean updateProject(ProjectDTO p) {
		logger.debug("Try to update the project with id: "+p.getLockableId());
		   
		//Transformation!!
		ProjectServerImpl s_p=ProjectTransformService.transform(p);
			  if(RegisterServerDAOService.INSTANCE.getProjectDAO().rename(s_p, s_p.getName()) && 
					  RegisterServerDAOService.INSTANCE.getProjectDAO().save(s_p)){
				  //send a message: a new project is created!
				  RegisterServerDAOService.INSTANCE.getMessageDAO().addUpdateMessage(s_p);
				  return true;
			  }
			  else return false;  
		   
		   
		
	}

	/***********************************
		 *                                 *
		 *		      PARTS                *
		 *                                 *
		 ***********************************/
	  
	  /*
	   public List<PartDTO> addParts(List<PartDTO> p_l,ProjectDTO proj){
		   
		   //Transformation!!
		   List<PartServerImpl> s_p_l=PartTransformService.transformDTOList(p_l);
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   
		   List<PartServerImpl> s_r_l=RegisterServerDAOService.INSTANCE.getPartDAO().saveParts(s_p_l, s_proj);
		   
		   
		   
		   //TODO send a message the number of part is changed
		   
		   return PartTransformService.transformServerList(s_r_l);
	   }
	   */
	   
	   public String getCADFileName(PartDTO part,ProjectDTO proj){
		   String cad_file=part.getCadFileName();
		 // logger.info("CAD File Name: "+cad_file);
		   for(String cad_path:proj.getCadPaths()){
			   String completeCadFileName=cad_path+"/"+cad_file;
		//	   logger.info("CAD PATH: "+completeCadFileName);
			   if(new File(completeCadFileName).isFile()){
				   return completeCadFileName;
			   }   
		   }
		   
		   return null;
	   }
	   
	   
	   public boolean addPart(PartDTO part,ProjectDTO proj){
		 //Transformation!!
		   PartServerImpl s_p_l=PartTransformService.transform(part);
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   
		   if(RegisterServerDAOService.INSTANCE.getPartDAO().savePart(s_p_l, s_proj)){
			   //send a message: a new part is added
				  RegisterServerDAOService.INSTANCE.getMessageDAO().addAddMessage(s_p_l, s_proj.getLockableId());
			   return true;
		   }
		   else return false;
		   
	   }
	   
	   public boolean updatePart(PartDTO part,ProjectDTO proj){
		 //Transformation!!
		   PartServerImpl s_p_l=PartTransformService.transform(part);
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   
		   if(RegisterServerDAOService.INSTANCE.getPartDAO().savePart(s_p_l, s_proj)){
			   //send a message: the part is updated!
				  RegisterServerDAOService.INSTANCE.getMessageDAO().addUpdateMessage(s_p_l, s_proj.getLockableId());
			   return true;
		   }
		   else return false;
	   }
	  
	   
	   
	   public List<PartDTO> getPartsFromProject(ProjectDTO proj){
		   //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   
		   List<PartServerImpl> p_l=RegisterServerDAOService.INSTANCE.getPartDAO().getPartsFromProject(s_proj);
		   
		 
		   //Set the isCadPartFound
		   List<PartDTO> p_dto_l=PartTransformService.transformServerList(p_l);
		   setCadPartFound(p_dto_l,proj);
		   
		   return p_dto_l;
		   
	   }
	   
	   private void setCadPartFound( List<PartDTO> p_dto_l,ProjectDTO proj){
		   for(PartDTO p:p_dto_l){
			   String cad_file=p.getCadFileName();
			   p.setCadFileFound(false);
			   for(String cad_path:proj.getCadPaths()){
				   String completeCadFileName=cad_path+"/"+cad_file;
				   if(new File(completeCadFileName).isFile()){
					   p.setCadFileName(completeCadFileName);
					   p.setCadFileFound(true);
					   break;  
				   }   
			   }
		   }
	   }
	   
	   
	   public List<PartDTO> getPartsFromProject(ProjectDTO proj, Part.Type type){
		   //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   
		   List<PartServerImpl> p_l=RegisterServerDAOService.INSTANCE.getPartDAO().getPartsFromProject(s_proj,type);
		   
		   //logger.info("Number of Server Parts: "+p_l.size());
		   
		   //Set the isCadPartFound
		   List<PartDTO> p_dto_l=PartTransformService.transformServerList(p_l);
		   setCadPartFound(p_dto_l,proj);
		   
		   return p_dto_l;
	   }
	   
	   public int getNumberOfParts(ProjectDTO proj, Part.Type type){
		   //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   return RegisterServerDAOService.INSTANCE.getPartDAO().getNumberOfParts(s_proj, type);
	   }
	   
	   
	   
	   /***********************************
		 *                                 *
		 *		    ASSEMBLY               *
		 *                                 *
		 ***********************************/
	   
	   
	   /**
	    * add a sub assembly to a project
	    * @param node
	    * @param proj
	    * @return
	    */
	   
	   public boolean addSubAssembly(AssemblyDTO ass, ProjectDTO proj, Assembly.Type type){
		   
		   //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   AssemblyServerImpl s_ass=AssemblyTransformationService.transform(ass);
		   
		   return RegisterServerDAOService.INSTANCE.getAssemblyDAO().addSubAssembly(s_ass, s_proj, type);
		   
	   }
	   
	   
	   /**
	    * return the assembly from a project
	    * 
	    * @param proj
	    * @return
	    */
	   public AssemblyDTO getAssemblyFromProject(ProjectDTO proj,Assembly.Type type){
		   //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   AssemblyServerImpl ass=RegisterServerDAOService.INSTANCE.getAssemblyDAO().getAssembly(s_proj,type);
		   
		   return AssemblyTransformationService.transform(ass);
		   
	   }
	   
	   public boolean saveAssembly(AssemblyDTO ass,ProjectDTO proj){
		   
		 //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   AssemblyServerImpl s_ass=AssemblyTransformationService.transform(ass);
		   
		   return RegisterServerDAOService.INSTANCE.getAssemblyDAO().saveAssembly(s_ass, s_proj);
		   
	   }
	   
	   public boolean hasAssembly(ProjectDTO proj,Assembly.Type type){
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   return RegisterServerDAOService.INSTANCE.getAssemblyDAO().hasAssembly(s_proj, type);
		   
	   }
	   
	   
	  /*
	  
	   public boolean addPartToProject(long projectId,PmcPart part){
		   
		   ProjectsEntry pro=getProject(projectId);
		   if(pro==null)return false;
			  
		   return PmcPartProvider.INSTANCE.registerPartToProject(part, pro);
		   
	   }
	   
	   
	   public LinkedList<PmcPart> getAllPartsFromProject(long projectId){
		  
		   ProjectsEntry pro=getProject(projectId);
			  if(pro==null)return null;
			  
			  if(!pro.hasParts())return null;
			  
			  return pro.getParts();
		   
	   }
	   
	   
	   public boolean addSubAssemblyToProject(long projectId,PmcNode node){
		   
		   ProjectsEntry pro=getProject(projectId);
		   if(pro==null)return false;
		   
		   PmcAssembly ass=PmcAssemblyProvider.INSTANCE.getAssemblyFromProject(pro);
		   if(ass==null){
			   ass=new PmcAssembly();
			   PmcAssemblyProvider.INSTANCE.registerAssemblyToProject(ass, pro);
		   }
		   
		   ass.addNode(node);
		   return ass.save();
		   
		   
	   }
	   
	   public PmcAssembly getAssemblyFromProject(long projectId){
		   
			  ProjectsEntry pro=getProject(projectId);
			  if(pro==null)return null;
			  
			  return PmcAssemblyProvider.INSTANCE.getAssemblyFromProject(pro);

		   
	   }
	   
	   private ProjectsEntry getProject(long projectId){
		   //Test if the project is the root
			  if(projectId==ProjectsEntry.ROOT_ID)return null;
			  
			  //Test if the project is valid
			  if(!ProjectProvider.INSTANCE.getAllProjects().containsKey(projectId))return null;
			  
			  return ProjectProvider.INSTANCE.getAllProjects().get(projectId);
	   }
	  
	  */
	   
	   
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
	   public boolean saveVtaElement(ConnectionElementDTO e,ProjectDTO proj){
		 //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   ConnectionElementServerImpl e_s=ConnectionElementTransformService.transform(e);
		   
		   return RegisterServerDAOService.INSTANCE.getConnectionDAO().saveVtaElement(e_s, s_proj);
		   
	   }
	   
	   /**
	    * 
	    * @param e_l
	    * @param proj
	    * @return
	    */
	   public List<ConnectionElementDTO> addVtaElements(List<ConnectionElementDTO> e_l,ProjectDTO proj){
		   List<ConnectionElementDTO> e_dto_l=new LinkedList<ConnectionElementDTO>();
		   
		   logger.info("Add connections To Project: "+proj);
		   
		   for(ConnectionElementDTO e:e_l){
			   if(!saveVtaElement(e,proj))e_dto_l.add(e);
		   }
		   
		   return e_dto_l;
	   }
	   
	   
	   /**
	    * 
	    * @param proj
	    * @return
	    */
	   public List<ConnectionElementDTO> getVtaElements(ProjectDTO proj){
		   //Transformation!!
		   ProjectServerImpl s_proj=ProjectTransformService.transform(proj);
		   LinkedList<ConnectionElementServerImpl> e_l=RegisterServerDAOService.INSTANCE.getConnectionDAO().getConnectionElements(s_proj);
		   
		   return ConnectionElementTransformService.transformServerList(e_l);
		   
	   }
	   
	  

}