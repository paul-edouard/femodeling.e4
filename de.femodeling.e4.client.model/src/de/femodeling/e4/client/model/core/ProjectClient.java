package de.femodeling.e4.client.model.core;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.Project;

public abstract class ProjectClient extends Project {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    
    private ProjectClientImpl parent;
    
	private List<ProjectClientImpl> childs;
	

	public ProjectClientImpl getParent() {
		return parent;
	}

	public void setParent(ProjectClientImpl parent) {
		this.parent = parent;
	}

	public List<ProjectClientImpl> getChilds() {
		if(childs==null)childs=new LinkedList<ProjectClientImpl>(); 
		return childs;
	}

	public void setChilds(List<ProjectClientImpl> childs) {
		this.childs = childs;
	}
	
	public Project.Type getNextType(){
		if(this.getType()==Project.Type.GROUP){
			return Project.Type.CARLINE;
		}
		
		return Project.Type.DATA_LEVEL;
		
	}

	
	/**
	 * creation date
	 */
	private String creationDate=new Date().toString();
	public static final String CREATION_DATE="project.creation.date";


	public String getCreationDate() {
		String value=this.getStringParam(CREATION_DATE);
		if(!value.isEmpty())creationDate=value;
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		setParam(CREATION_DATE, creationDate);
		this.creationDate = creationDate;
	}
	
	/**
	 * creation user
	 */
	private String creationUser="No user";
	public static final String CREATION_USER="project.creation.user";


	public String getCreationUser() {
		String value=this.getStringParam(CREATION_USER);
		if(!value.isEmpty())creationUser=value;
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		setParam(CREATION_USER, creationUser);
		this.creationUser = creationUser;
	}
	
	
	
	
	
	
	
}
