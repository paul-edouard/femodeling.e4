package de.femodeling.e4.client.model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.core.ProjectClient;
import de.femodeling.e4.client.model.listener.ProjectClientListenerIF;
import de.femodeling.e4.model.core.Project;
import de.femodeling.e4.util.Utils;
//import org.apache.log4j.Logger;



public class ProjectClientImpl extends ProjectClient {

	 /** The serial version UID. */
	static final long serialVersionUID=1L;
	
	//private static Logger logger = Logger.getLogger(ProjectClientImpl.class);
	
	
	public void setState(State state) {
		propertyChangeSupport.firePropertyChange("state", this.getState(),state);
		fireProjectsChanged(null);
		super.setState(state);
	}
	
	public void setType(Type type) {
		propertyChangeSupport.firePropertyChange("type", this.getType(),type);
		fireProjectsChanged(null);
		super.setType(type);
	}
	
	
	public void setName(String name,boolean updatePath) {
		propertyChangeSupport.firePropertyChange("name", this.getName(),name);
		fireProjectsChanged(null);
		
		if(updatePath && !name.equals(this.getName())){
			//update path and path from childs
			File p=new File(this.getPath());
			String new_path=p.getParent()+File.separator+name;
			this.setPath(new_path);
			updateChildsPath();
		}
		
		this.setName(name);
		
		//super.setName(name);
	}
	
	
	private void updateChildsPath(){
		
		for(ProjectClientImpl c:this.getChilds()){
			c.setPath(this.getPath()+File.separator+
					Project.entriesDirName+File.separator+c.getName());
			c.updateChildsPath();
		}
		
	}
	
	public void setPath(String path) {
		propertyChangeSupport.firePropertyChange("path", this.getPath(),path);
		fireProjectsChanged(null);
		
		/*
		File f=new File(path);
		//if(f.exists()){
			propertyChangeSupport.firePropertyChange("name", this.getPath(),path);
			fireProjectsChanged(null);
			super.setName(f.getName());
			
		//}
		*/
		super.setPath(path);
	}
	
	public void setGroup(String group) {
		propertyChangeSupport.firePropertyChange("group", this.getGroup(),group);
		fireProjectsChanged(null);
		super.setGroup(group);
	}
	
	public void setParent(ProjectClientImpl parent) {
		propertyChangeSupport.firePropertyChange("parent", this.getParent(),parent);
		fireProjectsChanged(null);
		super.setParent(parent);
	}
	
	public void setChilds(List<ProjectClientImpl> childs) {
		propertyChangeSupport.firePropertyChange("childs", this.getChilds(),childs);
		fireProjectsChanged(null);
		super.setChilds(childs);
	}
	
	public void addChild(ProjectClientImpl child){
		propertyChangeSupport.firePropertyChange("childs",this.getChilds().add(child),false);
		child.setParent(this);
		fireProjectsChanged(null);
	}
	
	
	@Override
	public void addCadPath(String path) {
		propertyChangeSupport.firePropertyChange("cadPaths",this.getCadPaths().add(path),false);
		fireProjectsChanged(null);
	}
	
	
	@Override
	public void setCadPaths(LinkedList<String> cadPaths) {
		
		propertyChangeSupport.firePropertyChange("cadPaths",this.getCadPaths(),cadPaths);
		fireProjectsChanged(null);
		super.setCadPaths(cadPaths);
	}

	public void removeChild(ProjectClientImpl child){
		String searchId=child.getLockableId();
		ProjectClientImpl childToDelete=null;
		
		for (ProjectClientImpl c : this.getChilds()) {
			if(c.getLockableId().equals(searchId)){
				childToDelete=c;
				break;
			}
			
		}
		
		if(childToDelete==null)return;
		
		propertyChangeSupport.firePropertyChange("childs",this.getChilds().remove(childToDelete),false);
		fireProjectsChanged(null);
		
	}
	
	
	public ProjectClientImpl createCopy(){
		ProjectClientImpl p=new ProjectClientImpl();
		
		p.setGroup(this.getGroup());
		p.setLockableId(this.getLockableId());
		p.setPath(this.getPath());
		p.setSessionId(this.getSessionId());
		p.setState(this.getState());
		p.setType(this.getType());
		p.setCadPaths(Utils.copyCollection(this.getCadPaths()));
		p.setParent(this.getParent());
		p.setChilds(this.getChilds());
		p.setName(this.getName(),false);
		p.setParameter(this.getParameter().createCopy());
		
		return p;
	}
	
	/***********************************
	 *                                 *
	 *		      LISTENER             *
	 *                                 *
	 ***********************************/	
	private List<ProjectClientListenerIF> listeners;
	
	@Override
	protected void fireLockableEntityChanged() {
		fireProjectsChanged(null);
	}
	
	public void addProjectsListener(ProjectClientListenerIF listener) {
		if (getParent() != null)
			getParent().addProjectsListener(listener);
		else {
			if (listeners == null)
				listeners = new LinkedList<ProjectClientListenerIF>();
			listeners.add(listener);
		}
	}

	public void removeProjectsListener(ProjectClientListenerIF listener) {
		if (getParent() != null)
			getParent().removeProjectsListener(listener);
		else {
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty())
					listeners = null;
			}
		}
	}

	protected void fireProjectsChanged(ProjectClientImpl entry) {
		if (getParent() != null){
			//logger.warn("Parent Found For: "+this.getName());
			getParent().fireProjectsChanged(entry);
		}
		else {
			//logger.warn("No Parent Found: "+this.getName());
			if (listeners == null)
				return;
			Object[] rls = listeners.toArray();
			for (int i = 0; i < rls.length; i++) {
				ProjectClientListenerIF listener = (ProjectClientListenerIF) rls[i];
				listener.projectsChanged(this, entry);
			}
		}
	}
	
	

}
