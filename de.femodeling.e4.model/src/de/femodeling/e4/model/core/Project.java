package de.femodeling.e4.model.core;

import java.io.File;
import java.util.LinkedList;

import de.femodeling.e4.model.core.lockable.LockableEntityProChanSupp;



public abstract class Project extends  LockableEntityProChanSupp{
	
	public enum State { STARTED , ACTIVATED, FINISHED, DELETED,NONE }
	public enum Type { ROOT, GROUP, CARLINE, DATA_LEVEL,NONE }
	
	public static final String[] STATE_LIST={"STARTED","ACTIVATED","FINISHED","DELETED","NONE"}; 
	public static final String[] TYPE_LIST={"ROOT","GROUP","CARLINE","DATA_LEVEL","NONE"};
	
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
		if(type==null)return  TYPE_LIST[4];
		
		switch(type){
			case ROOT:return TYPE_LIST[0];
			case GROUP:return TYPE_LIST[1];
			case CARLINE:return TYPE_LIST[2];
			case DATA_LEVEL:return TYPE_LIST[3];
			default: return TYPE_LIST[4];
		}
	}
	
	
	public void stringToType(String str){
		if(str==null || str.isEmpty())return;
		
		if(str.equals(TYPE_LIST[0]))
			type=Type.ROOT;
		else if(str.equals(TYPE_LIST[1]))
			type=Type.GROUP;
		else if(str.equals(TYPE_LIST[2]))
			type=Type.CARLINE;
		else if(str.equals(TYPE_LIST[3]))
			type=Type.DATA_LEVEL;
		else
			type=Type.NONE;
	}
	
	
    //public enum State { STARTED , ACTIVATED, FINISHED, DELETED }
    
	public String stateToString(){
		if(state==null)return STATE_LIST[4];
		
		switch(state){
			case STARTED:return STATE_LIST[0];
			case ACTIVATED:return STATE_LIST[1];
			case FINISHED:return STATE_LIST[2];
			case DELETED:return STATE_LIST[3];
			default: return STATE_LIST[4];
		}
	}
	
	
	public void stringToState(String str){
		if(str==null || str.isEmpty()){
			state=State.NONE;
			return;
		}
		
		if(str.equals(STATE_LIST[0]))
			state=State.STARTED;
		else if(str.equals(STATE_LIST[1]))
			state=State.ACTIVATED;
		else if(str.equals(STATE_LIST[2]))
			state=State.FINISHED;
		else if(str.equals(STATE_LIST[3]))
			state=State.DELETED;
		else if(str.equals(STATE_LIST[4])){
			state=State.NONE;
		}
	}

	@Override
	public String toString() {
		return "Project [state=" + state + ", type=" + type + ", name=" + name
				+ ", path=" + path + ", group=" + group + "]";
	}
	
	
	
}
