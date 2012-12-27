package de.femodeling.e4.server.service.dao;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlFile;
import de.femodeling.e4.server.internal.model.ConnectionElementServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;


public class ConnectionElementDAOImpl extends XmlFile implements ConnectionElementDAOIF {
	
	private ConnectionElementServerImpl vtaElement;
	
	//private static Logger logger = Logger.getLogger(VtaElementDAOImpl.class);
	
	

	@Override
	public boolean saveVtaElement(ConnectionElementServerImpl e, ProjectServerImpl proj) {
		this.vtaElement=e;
		
		
		String path_name=proj.getConnectionsDir();
		File path=new File(path_name);
		if(!path.exists())path.mkdir();
		
		return saveAsXml(path_name+File.separator+vtaElement.getXmlFileName());
	}

	@Override
	public List<ConnectionElementServerImpl> saveVtaElements(List<ConnectionElementServerImpl> e_l, ProjectServerImpl proj) {
		
		List<ConnectionElementServerImpl> e_error=new LinkedList<ConnectionElementServerImpl>();
		
		for(ConnectionElementServerImpl e:e_l){
			if(!saveVtaElement(e,proj))e_error.add(e);
		}
		
		return e_error;
	}

	@Override
	public LinkedList<ConnectionElementServerImpl> getConnectionElements(ProjectServerImpl proj) {
		
		LinkedList<ConnectionElementServerImpl> e_l=new LinkedList<ConnectionElementServerImpl>();
		
		String vta_path=proj.getConnectionsDir();
		File path=new File(vta_path);

		if(path.exists() && path.isDirectory()){
			
			//Set the file filter
			String[] fileList=path.list(new FilenameFilter() {				
				@Override
				public boolean accept(File dir, String name) {
					
					return name.endsWith(".xml");
				}
			});
			
			//read the xml file
			for(int i=0;i<fileList.length;i++){
				vtaElement=new ConnectionElementServerImpl();
				if(readFromXml(vta_path+File.separator+fileList[i])){
					e_l.add(vtaElement);
				}
			}
			
			
		}
		
		return e_l;
	}
	
	
	/***********************************
	 *                                 *
	 *		       XML                 *
	 *                                 *
	 ***********************************/
	@Override
	public Element toDomElement(Document doc) {
		Element e=doc.createElement(this.getTagName());
		
		Element c=vtaElement.toDomElement(doc);
		e.appendChild(c);
		
		e.setAttribute("id", vtaElement.getLockableId());
		
		return e;
	}

	@Override
	public void init(Element Root) {
		if(Root.getTagName().equals(this.getTagName())){
			
			vtaElement.setLockableId(Root.getAttribute("id"));
			
			NodeList Children=Root.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					
					if(childElement.getTagName().equals(vtaElement.getTagName())){
						vtaElement.init(childElement);
					}
				}
			}	
			
		}

	}

	@Override
	public String getTagName() {
		return "VtaElement";
	}
}
