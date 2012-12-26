package de.femodeling.e4.model.core.part;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlElementIF;

public class Representation implements XmlElementIF,Serializable{
	
	private static Logger logger = Logger.getLogger(Representation.class);
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    private int id;
    private HashMap<String, RepFile> fileMap;
    private String partId;
    
    public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, RepFile> getFileMap() {
		if(fileMap==null)fileMap=new HashMap<String, RepFile>();
		return fileMap;
	}

	public void setFileMap(HashMap<String, RepFile> fileMap) {
		this.fileMap = fileMap;
	}
	
	public void addFile(RepFile file){
		
		for(int key:file.getPropertyMap().keySet()){
			file.getPropertyMap().get(key).setPartId(partId);
		}
		
		this.getFileMap().put(file.getName(), file);
	}
	
	public Representation createCopy(){
		Representation copy=new Representation();
		copy.id=this.id;
		
		for(String key:this.fileMap.keySet()){
			copy.getFileMap().put(key, 
					this.fileMap.get(key).createCopy());	
		}
		
		return copy;
	}

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "Representation";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			id=Integer.parseInt(el.getAttribute("id"));
			
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					RepFile r=new RepFile();
					if(childElement.getTagName().equals(r.getTagName())){

						r.init(childElement);
						this.addFile(r);
						
					}
				}
			}
			
		}else{
			logger.warn("this is not a valid "+this.getTagName()+ "element");
		}
	}
	
	/***********************************
	 *                                 *
	 *	    RETURN AS ELEMENT          *
	 *                                 *
	 ***********************************/	
	
	public Element toDomElement(Document doc){
		Document Doc=doc;
		Element e=Doc.createElement(this.getTagName());
		
		e.setAttribute("id", String.valueOf(this.id));
		
		
		for(String key:this.getFileMap().keySet()){
			
			RepFile f=this.getFileMap().get(key);
			e.appendChild(f.toDomElement(doc));
			
		}
		
		return e;
	}

}
