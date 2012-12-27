package de.femodeling.e4.server.internal.dao;

import java.io.File;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.assembly.AssNode;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.xml.XmlFile;
import de.femodeling.e4.server.internal.model.AssemblyServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;



public class AssemblyDAOImpl extends XmlFile implements AssemblyDAOIF {

	
	private AssemblyServerImpl ass;
	
	private static Logger logger = Logger.getLogger(AssemblyDAOImpl.class);
	
	
	
	//@Override
	public boolean addSubAssembly(AssemblyServerImpl ass, ProjectServerImpl proj, Assembly.Type type) {
		this.ass=getAssembly(proj,type);
		if(this.ass==null)return false;
		
		//add the subassembly
		this.ass.addSubAssembly(ass.getRoot());
		
		String ass_path=proj.getAssembliesDir();
		File path=new File(ass_path);
		if(!path.exists())path.mkdir();
		
		if(this.ass.getXmlFileName().isEmpty())return false;
		
		return saveAsXml(proj.getAssembliesDir()+File.separator+this.ass.getXmlFileName());
	}
	

	@Override
	public AssemblyServerImpl getAssembly(ProjectServerImpl proj, Assembly.Type type) {
		
		String ass_path=proj.getAssembliesDir();
		
		ass=new AssemblyServerImpl(type);
		//ass.setType(type);
		readFromXml(ass_path+File.separator+ass.getXmlFileName());
		
		return ass;
		
		
	
	}
	
	@Override
	public boolean saveAssembly(AssemblyServerImpl ass,ProjectServerImpl proj){
		this.ass=ass;
		if(ass==null)return false;
		
		String ass_path=proj.getAssembliesDir();
		
		File path=new File(ass_path);
		if(!path.exists())path.mkdir();
		
		logger.info("Saving Assembly:"+proj.getAssembliesDir()+File.separator+ass.getXmlFileName());
		
		return saveAsXml(proj.getAssembliesDir()+File.separator+ass.getXmlFileName());
		
	}
	
	
	public boolean hasAssembly(ProjectServerImpl proj,Assembly.Type type){
		ass=new AssemblyServerImpl(type);
		//ass.setType(type);
		
		String ass_file_name=proj.getAssembliesDir()+File.separator+ass.getXmlFileName();
		return new File(ass_file_name).exists();
	}
	
	/***********************************
	 *                                 *
	 *		       XML                 *
	 *                                 *
	 ***********************************/
	
	@Override
	public String getTagName() {
		return "assembly";
	}
	
	@Override
	public Element toDomElement(Document doc) {
	
		Element e=doc.createElement(this.getTagName());
		
		e.setAttribute("id", ass.getLockableId());
		e.setAttribute("type", Assembly.typeToString(ass.getType()));
		
		Element child=ass.getRoot().toDomElement(doc);
		e.appendChild(child);
		
		return e;
	}
	
	@Override
	public void init(Element el) {
		if(el.getTagName().equals(this.getTagName())){
			
			ass.setLockableId(el.getAttribute("id"));
			ass.setType(Assembly.stringToType(el.getAttribute("type")));
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals(new AssNode().getTagName())){
						AssNode e=new AssNode(chilElement);
						ass.setRoot(e);
					}
					
				}
			}
			
		}
		else{
			logger.warn("this is not a valid " + this.getTagName()+" element");
		
		}

	}
	
	

}
