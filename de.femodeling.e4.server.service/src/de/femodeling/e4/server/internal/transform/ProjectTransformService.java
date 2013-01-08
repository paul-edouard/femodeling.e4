package de.femodeling.e4.server.internal.transform;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.model.dto.ProjectDTOImpl;
import de.femodeling.e4.server.internal.dao.ProjectDAOIF;
import de.femodeling.e4.server.internal.dao.RegisterServerDAOService;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;


public class ProjectTransformService {
	
	private static Logger logger = Logger.getLogger(ProjectTransformService.class);
	
	
	/**
	 * ProjectServerImpl to ProjectDTO
	 * 
	 * @param ent
	 * @param transformChilds
	 * @return
	 */
	
	public static ProjectDTO transform(ProjectServerImpl ent, boolean transformChilds){
		ProjectDTO p=new ProjectDTOImpl();
		
		p.setGroup(ent.getGroup());
		p.setLockableId(ent.getLockableId());
		p.setPath(ent.getPath());
		logger.debug("Project Server name: "+ent.getName());
		
		p.setName(ent.getName());
		p.setSessionId(ent.getSessionId());
		p.setState(ent.getState());
		p.setType(ent.getType());
		p.setCadPaths(ent.getCadPaths());
		
		ProjectDAOIF pDAO=RegisterServerDAOService.INSTANCE.getProjectDAO();
		
		if(transformChilds && pDAO!=null){
			List<ProjectServerImpl> p_list= pDAO.getAllSubProjects(ent);
			List<ProjectDTO> p_dto_list=new LinkedList<ProjectDTO>();
			for (ProjectServerImpl projectServerImpl : p_list) {
				p_dto_list.add(transform(projectServerImpl,transformChilds));
			}
			
			p.setChilds(p_dto_list);
		}
		
		p.setParameter(ent.getParameter());
		
		return p;
	}
	
	
	/**
	 * 
	 * ProjectDTO to ProjectServerImpl
	 * 
	 * @param ent
	 * @return
	 */
	public static ProjectServerImpl transform(ProjectDTO ent){
		ProjectServerImpl p=new ProjectServerImpl();
		
		//System.out.println("Group: "+ent.getGroup());
		p.setGroup(ent.getGroup());
		
		//System.out.println("LockableId: "+ent.getLockableId());
		p.setLockableId(ent.getLockableId());
		
		//System.out.println("Path: "+ent.getPath());
		p.setPath(ent.getPath());
		
		//System.out.println("SessionId: "+ent.getSessionId());
		p.setSessionId(ent.getSessionId());
		
		//System.out.println("State: "+ent.getState());
		p.setState(ent.getState());
		
		//System.out.println("Type: "+ent.getType());
		p.setType(ent.getType());
		
		p.setCadPaths(ent.getCadPaths());
		
		p.setName(ent.getName());
		
		p.setParameter(ent.getParameter());
		
		return p;
		
	}
	
	
	

}
