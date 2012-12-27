package de.femodeling.e4.server.internal.model;

import java.io.File;

import de.femodeling.e4.model.core.Project;

public class ProjectServerImpl extends Project{
	
	static final long serialVersionUID=010101L;
	
	
	//private static Logger logger = Logger.getLogger(ProjectServerImpl.class);
	
	/*
	public ProjectServerImpl(String path){
		this.setPath(path);
	}
	*/
	
	
	public String getEntriesDir(){
		if(this.getType()!=Type.ROOT)return getPath()+ File.separator + entriesDirName;
		else return this.getPath();
		}
	public String getPartsDir(){	return getPath()+ File.separator + partsDirName;}
	public String getAssembliesDir(){	return getPath()+ File.separator + assembliesDirName;}
	public String getConnectionsDir(){	return getPath()+ File.separator + connectionsDirName;}
	public String getFileName(){return getPath()+ File.separator +projectFile;}
	
	public boolean renameProject(String newName){
		File oldDir=new File( getPath());
		File newDir=new File(oldDir.getParent()+File.separator+newName);
		if(oldDir.renameTo(newDir)){
			this.setPath(newDir.getAbsolutePath());
			return true;
		}
		else return false;
	}
	
	/**
	 * create the directory structure
	 * 
	 * @return
	 */
	public boolean createProjectStructure(){
		
		//create the project directory
		if(!createStructureDirectory(getPath()))return false;
		
		//create the entries directory
		if(!createStructureDirectory(getEntriesDir()))return false;
		
		//create the parts directory
		if(!createStructureDirectory(getPartsDir()))return false;
		
		//create the assemblies directory
		if(!createStructureDirectory(getAssembliesDir()))return false;
		
		//create the connections directory
		if(!createStructureDirectory(getConnectionsDir()))return false;
		
		return true;
	}
	
	
	private static  boolean createStructureDirectory(String dirName){
		File dir=new File(dirName);
		if(!dir.exists()){
			if(!dir.mkdir())return false;
		}
		
		return true;
	}
	

}
