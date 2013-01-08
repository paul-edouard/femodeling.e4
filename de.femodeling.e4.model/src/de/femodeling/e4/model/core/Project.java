package de.femodeling.e4.model.core;

import java.io.File;
import java.util.LinkedList;

import de.femodeling.e4.model.core.lockable.LockableEntityProChanSupp;



public abstract class Project extends  LockableEntityProChanSupp{
	
	public enum State { STARTED , ACTIVATED, FINISHED, DELETED }
	public enum Type { ROOT, GROUP, CARLINE, DATA_LEVEL,NONE }
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    public static final String entriesDirName		=	"01_Projects";
    public static final String partsDirName		=	"02_Parts";
    public static final String assembliesDirName	=	"03_Assemblies";
    public static final String connectionsDirName	=	"04_Connections";
    public static final String projectFile			=	"proj.xml";
    
    private State state;
    
    private Type type;
    
    private String name;
    
    private String path;
    
    private String group;    
    
    private LinkedList<String> cadPaths=new LinkedList<String>();
    
    
    
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


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		if(path!=null){
			File f=new File(path);
			if(f!=null)name=f.getName();
			this.path = path;
		}
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	
	
	
	//public enum Type { ROOT, GROUP, CARLINE, DATA_LEVEL }
	
	public LinkedList<String> getCadPaths() {
		return cadPaths;
	}
	
	public void addCadPath(String path){
		cadPaths.add(path);
	}
	
	
	public void setCadPaths(LinkedList<String> cadPaths) {
		this.cadPaths = cadPaths;
	}
	
	

	public void setName(String name) {
		this.name = name;
	}

	public String typeToString(){
		if(type==null)return  "unknow state";
		
		switch(type){
			case ROOT:return "root";
			case GROUP:return "group";
			case CARLINE:return "carline";
			case DATA_LEVEL:return "data level";
			default: return "unknow type";
		}
	}
	
	
	public void stringToType(String str){
		if(str==null || str.isEmpty())return;
		
		if(str.equals("root"))
			type=Type.ROOT;
		else if(str.equals("group"))
			type=Type.GROUP;
		else if(str.equals("carline"))
			type=Type.CARLINE;
		else if(str.equals("data level"))
			type=Type.DATA_LEVEL;
		else
			type=Type.NONE;
	}
	
	
    //public enum State { STARTED , ACTIVATED, FINISHED, DELETED }
    
	public String stateToString(){
		if(state==null)return  "unknow state";
		
		switch(state){
			case STARTED:return "started";
			case ACTIVATED:return "activated";
			case FINISHED:return "finished";
			case DELETED:return "deleted";
			default: return "unknow state";
		}
	}
	
	
	public void stringToState(String str){
		if(str==null || str.isEmpty())return;
		
		if(str.equals("started"))
			state=State.STARTED;
		else if(str.equals("activated"))
			state=State.ACTIVATED;
		else if(str.equals("finished"))
			state=State.FINISHED;
		else if(str.equals("deleted"))
			state=State.DELETED;
	}

	@Override
	public String toString() {
		return "Project [state=" + state + ", type=" + type + ", name=" + name
				+ ", path=" + path + ", group=" + group + "]";
	}
	
	
	
}
