package de.femodeling.e4.model.core.part;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.xml.XmlElementIF;

public class Property implements XmlElementIF,Serializable{
	
private static Logger logger = Logger.getLogger(Property.class);
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int id;
    private String caeMaterial;
    private String cadMaterial;
    private String originMaterial;
    private String body;
    
    private String partId;
    
    
    
    public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaeMaterial() {
		return caeMaterial;
	}

	public void setCaeMaterial(String caeMaterial) {
		this.caeMaterial = caeMaterial;
	}

	public String getCadMaterial() {
		return cadMaterial;
	}

	public void setCadMaterial(String cadMaterial) {
		this.cadMaterial = cadMaterial;
	}

	public String getOriginMaterial() {
		return originMaterial;
	}

	public void setOriginMaterial(String origineMaterial) {
		this.originMaterial = origineMaterial;
	}
	
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", id=" + id + ", caeMaterial="
				+ caeMaterial + ", cadMaterial=" + cadMaterial
				+ ", originMaterial=" + originMaterial + "]";
	}
	
	public Property createCopy(){
		
		Property copy=new Property();
		copy.cadMaterial=this.cadMaterial;
		copy.caeMaterial=this.caeMaterial;
		copy.id=this.id;
		copy.name=this.name;
		copy.originMaterial=this.originMaterial;
		copy.partId=this.partId;
		
		return copy;
	}

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "Property";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			name=el.getAttribute("name");
			id=Integer.parseInt(el.getAttribute("id"));
			caeMaterial=el.getAttribute("caeMaterial");
			cadMaterial=el.getAttribute("cadMaterial");
			originMaterial=el.getAttribute("originMaterial");
			body=el.getAttribute("body");
			
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
		e.setAttribute("id", String.valueOf(this.id));
		e.setAttribute("caeMaterial", this.caeMaterial);
		e.setAttribute("cadMaterial", this.cadMaterial);
		e.setAttribute("originMaterial", this.originMaterial);
		e.setAttribute("body", this.body);
		
		return e;
	}
	
}
