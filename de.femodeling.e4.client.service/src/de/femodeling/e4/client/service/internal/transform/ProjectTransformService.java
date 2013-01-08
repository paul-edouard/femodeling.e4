package de.femodeling.e4.client.service.internal.transform;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.model.dto.ProjectDTOImpl;


public class ProjectTransformService {
	
	private static Logger logger = Logger.getLogger(ProjectTransformService.class);
	
	
	/**
	 * ProjectDTO to ProjectClientImpl
	 * 
	 * @param ent
	 * @param transformChilds
	 * @return
	 */
	public static ProjectClientImpl transformClient(ProjectDTO ent, boolean transformChilds){
		ProjectClientImpl p=new ProjectClientImpl();
		
		p.setGroup(ent.getGroup());
		p.setLockableId(ent.getLockableId());
		p.setPath(ent.getPath());
		p.setName(ent.getName(),true);
		p.setSessionId(ent.getSessionId());
		p.setState(ent.getState());
		p.setType(ent.getType());
		p.setCadPaths(ent.getCadPaths());
		
		p.setParameter(ent.getParameter());
		
		//logger.debug("Project DTO name: "+ent.getName());
		//logger.debug("Project Client name: "+p.getName());
		
		//System.out.println(p);
		
		if(transformChilds){
			List<ProjectClientImpl> childs_client=new LinkedList<ProjectClientImpl>();
			for (ProjectDTO child_dto : ent.getChilds()) {
				ProjectClientImpl child_client=transformClient(child_dto,transformChilds);
				child_client.setParent(p);
				childs_client.add(child_client);
			}
			p.setChilds(childs_client);
			
		}
		
		return p;
		
	}
	
	
	
	/**
	 * ProjectClientImpl to ProjectDTO
	 * 
	 * @param ent
	 * @return
	 */
	public static ProjectDTO transformClient(ProjectClientImpl ent){
		ProjectDTO p=new ProjectDTOImpl();
		
		p.setGroup(ent.getGroup());
		p.setLockableId(ent.getLockableId());
		p.setName(ent.getName());
		p.setPath(ent.getPath());
		p.setSessionId(ent.getSessionId());
		p.setState(ent.getState());
		p.setType(ent.getType());
		p.setCadPaths(ent.getCadPaths());
		p.setParameter(ent.getParameter());
		
		return p;
	}
	
	public static ProjectClientImpl createCopy(ProjectClientImpl ent){
		ProjectClientImpl p=new ProjectClientImpl();
		
		p.setGroup(ent.getGroup());
		p.setLockableId(ent.getLockableId());
		p.setPath(ent.getPath());
		p.setSessionId(ent.getSessionId());
		p.setState(ent.getState());
		p.setType(ent.getType());
		p.setCadPaths(ent.getCadPaths());
		p.setParent(ent.getParent());
		p.setChilds(ent.getChilds());
		p.setName(ent.getName(),false);
		p.setParameter(ent.getParameter());
		
		
		return p;
	}
	
	public static void copyData(ProjectClientImpl input, ProjectClientImpl target){
		
		target.setGroup(input.getGroup());
		target.setLockableId(input.getLockableId());
		target.setPath(input.getPath());
		target.setSessionId(input.getSessionId());
		target.setState(input.getState());
		target.setType(input.getType());
		target.setCadPaths(input.getCadPaths());
		//target.setParent(input.getParent());
		//target.setChilds(input.getChilds());
		
		//Copy the new name And update the path is the name has changed
		target.setName(input.getName(),true);
		target.setParameter(input.getParameter());
	}
	

}
