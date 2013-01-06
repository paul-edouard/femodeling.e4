package de.femodeling.e4.client.model.core;

import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.LockableEntityClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.Project.State;
import de.femodeling.e4.model.core.Project.Type;

public abstract class ProjectClient extends LockableEntityClientImpl {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    private State state;
    
    private Type type;
    
    protected String name;
    
    private String path;
    
    private String group;
    
    private ProjectClientImpl parent;
    
	private List<ProjectClientImpl> childs;
	
	private LinkedList<String> cadPaths=new LinkedList<String>();
	

	
	public LinkedList<String> getCadPaths() {
		return cadPaths;
	}
	
	public void addCadPath(String path){
		cadPaths.add(path);
	}
	
	public void setCadPaths(LinkedList<String> cadPaths) {
		this.cadPaths = cadPaths;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	/*
	public void setName(String name) {
		this.name = name;
	}
	*/

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

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

	@Override
	public String toString() {
		return "ProjectClient [state=" + state + ", type=" + type + ", name="
				+ name + ", path=" + path + ", group=" + group /*+ ", parent="
				+ parent.toString() + ", childs=" + childs.size() */+ ", cadPaths=" + cadPaths
				+ "]";
	}

	

	

	
	
	
}
