package de.femodeling.e4.client.model.core;

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

	
	
	
}
