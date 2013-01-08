package de.femodeling.e4.server.internal.dao;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.Project.State;
import de.femodeling.e4.model.core.Project.Type;
import de.femodeling.e4.model.core.parameter.Parameter;
import de.femodeling.e4.model.xml.XmlFile;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;
import de.femodeling.e4.util.file.FileService;


public class ProjectDAOImpl extends XmlFile implements ProjectDAOIF {
	
	
	private List<String> rootDirList=new LinkedList<String>();
	
	private ProjectServerImpl project;
	
	//private static Logger logger = Logger.getLogger(ProjectDAOImpl.class);
	
	public ProjectDAOImpl(){
		project=new ProjectServerImpl();
	}
	
	/**
	 * save the project
	 */
	public boolean save(ProjectServerImpl p){
		project=p;
		if(project.createProjectStructure()){
			return saveAsXml(project.getFileName());
		}
		return false;
		
		
		//return 
	}
	
	/**
	 * delete a project
	 */
	public boolean delete(ProjectServerImpl p){
		if(getAllSubProjects(p).size()>0)return false;
		
		File dir=new File(p.getPath());
		
		return FileService.deleteDirRecu(dir);
	}
	
	/*
	private boolean deleteDirRecu(File dir){
		
		File[] fileList=dir.listFiles();
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].isFile())fileList[i].delete();
			else if(fileList[i].isDirectory())
					deleteDirRecu(fileList[i]);
		}
		
		return dir.delete();
		
		
	}
	*/
	
	/**
	 * rename a project
	 */
	public boolean rename(ProjectServerImpl p, String name){
		return p.renameProject(name);
	}
	
	
	
	/**
	 * search all sub projects
	 * 
	 */
	public List<ProjectServerImpl> getAllSubProjects(ProjectServerImpl p){
		List<ProjectServerImpl> p_list=new LinkedList<ProjectServerImpl>();
		
		if(p.getType()==Type.ROOT){
			for (String path : rootDirList) {
				List<ProjectServerImpl> l=extractAllProjectsFromDir(path);
				p_list.addAll(l);
			}
		}
		else{
			p_list=extractAllProjectsFromDir(p.getEntriesDir());
		}
		
		return p_list;
	}
	
	private List<ProjectServerImpl> extractAllProjectsFromDir(String dirName){
		List<ProjectServerImpl> p_list=new LinkedList<ProjectServerImpl>();
		
		File dir=new File(dirName);
		
		if(dir.exists() && dir.canRead()){
			File[] fileListe=dir.listFiles();
			
			for(int i=0;i<fileListe.length;i++){
				
				if(!fileListe[i].isDirectory())continue;
				
				File entryFile=new File(fileListe[i].getAbsolutePath()+File.separator+ProjectServerImpl.projectFile);
				if(!entryFile.exists() || !entryFile.canRead())continue;
				
				project=new ProjectServerImpl();
				//System.out.println(project.toString());
				if(readFromXml(entryFile.getAbsolutePath())){
					project.setPath(fileListe[i].getAbsolutePath());
					//System.out.println("coucou"+project.toString());
					p_list.add(project);
				}
				
				
				
			}
			
		}
		
		return p_list;
	}
	
	
	/**
	 * return a dummy root project
	 */
	public ProjectServerImpl createRoot(){
		
		ProjectServerImpl root=new ProjectServerImpl();
		
		root.setPath("root");
		root.setType(Type.ROOT);
		root.setState(State.ACTIVATED);
		root.setSessionId("");
		
		return root;
	}
	
	
	
	public void addRootDir(String path){
		rootDirList.add(path);
	}
	

	
	
	/***********************************
	 *                                 *
	 *		       XML                 *
	 *                                 *
	 ***********************************/	
	
	
	/**
	 * return the TAG Name used in the xml file
	 */
	public String getTagName(){return "project";}
	
	
	/**
	 * initializes the users map from a xml element
	 */
	public void init(Element Root){
		
		if(Root.getTagName().equals(this.getTagName())){
			
			project.setGroup(Root.getAttribute("group"));
			project.setLockableId(Root.getAttribute("id"));
			//project.set
			
			project.stringToState(Root.getAttribute("state"));
			project.stringToType(Root.getAttribute("type"));
			
			NodeList Children=Root.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					
					if(childElement.getTagName().equals("CadPath")){
						
						project.addCadPath(childElement.getTextContent());
						
					}
					else if(childElement.getTagName().equals(new Parameter().getTagName())){
						project.setParameter(new Parameter(childElement));
					}
					
				}
				
			}	
			
			
		}
	}
	
	
	/**
	 * export the user map in a xml element
	 */
	public Element toDomElement(Document doc){
		Element e=doc.createElement(this.getTagName());
			
		e.setAttribute("group", project.getGroup());
		e.setAttribute("id", project.getLockableId());
		e.setAttribute("state", project.stateToString());
		e.setAttribute("type",project.typeToString());
		
		for(String cadPath:project.getCadPaths()){
			Element cad_path=doc.createElement("CadPath");
			cad_path.setTextContent(cadPath);
			e.appendChild(cad_path);
		}
		
		e.appendChild(project.getParameter().toDomElement(doc));
		
		return e;
	  }
	
	
}
