package de.femodeling.e4.model.dto;

import java.util.List;

import de.femodeling.e4.model.core.Project;

public abstract class ProjectDTO extends Project {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    private List<ProjectDTO> childs;
    
    
    public List<ProjectDTO> getChilds() {
		return childs;
	}

	public void setChilds(List<ProjectDTO> childs) {
		this.childs = childs;
	}
    
    

	@Override
	public String toString() {
		return "ProjectDTO [getState()=" + getState() + ", getType()="
				+ getType() + ", getName()=" + getName() + ", getPath()="
				+ getPath() + ", getGroup()=" + getGroup() + ", getCadPaths()="
				+ getCadPaths() + ", typeToString()=" + typeToString()
				+ ", stateToString()=" + stateToString() + ", toString()="
				+ super.toString() + ", getLockableId()=" + getLockableId()
				+ ", islocked()=" + islocked() + ", getSessionId()="
				+ getSessionId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
    
  
    
    
}
