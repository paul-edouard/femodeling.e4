package de.femodeling.e4.model.core.part;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlElementIF;
import de.femodeling.e4.model.xml.XmlFile;

public class RepFile extends XmlFile implements XmlElementIF,Serializable{
	
	//public static String TRANSLATION="orignal.ansa";
	
	private static Logger logger = Logger.getLogger(RepFile.class);
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String type;
    private String size;
    private String numberOfFaces;
    private HashMap<Integer, Property> propertyMap;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public HashMap<Integer, Property> getPropertyMap() {
		if(propertyMap==null)propertyMap=new HashMap<Integer, Property>();
		return propertyMap;
	}

	public void setPropertyMap(HashMap<Integer, Property> propertyMap) {
		this.propertyMap = propertyMap;
	}
	
	public void addProperty(Property p){
		this.getPropertyMap().put(p.getId(), p);
	}
	
	public RepFile createCopy(){
		RepFile copy=new RepFile();
		copy.name=this.name;
		copy.size=this.size;
		copy.type=this.type;
		
		for(Integer key:this.propertyMap.keySet()){
			copy.getPropertyMap().put(key, 
					this.propertyMap.get(key).createCopy());
			
		}
		
		return copy;
	}
	
	
	
	@Override
	public String toString() {
		return "RepFile [name=" + name + ", type=" + type + ", size=" + size
				+ ", numberOfFaces=" + numberOfFaces + ", propertyMap="
				+ propertyMap + "]";
	}

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "RepFile";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			name=el.getAttribute("name");
			type=el.getAttribute("type");
			size=el.getAttribute("size");
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					Property p=new Property();
					if(childElement.getTagName().equals(p.getTagName())){

						p.init(childElement);
						this.addProperty(p);
						
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
		
		e.setAttribute("name", this.name);
		e.setAttribute("type", this.type);
		e.setAttribute("size", this.size);
		
		for(int key:this.getPropertyMap().keySet()){
			
			Property p=this.getPropertyMap().get(key);
			e.appendChild(p.toDomElement(doc));
			
		}
		
		return e;
	}

}
