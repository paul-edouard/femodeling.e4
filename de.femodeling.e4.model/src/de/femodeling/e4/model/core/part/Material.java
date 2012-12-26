package de.femodeling.e4.model.core.part;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.xml.XmlElementIF;

public class Material implements XmlElementIF,Serializable{
	
	private static Logger logger = Logger.getLogger(Material.class);
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
	
	private String anteil;
	private String normbezeichnung;
	private String text="";
	private String werkstoff;
	
	public String toCsv(){
		return werkstoff +";"+normbezeichnung+";"+anteil;
	}
	
	public String getAnteil() {
		return anteil;
	}

	public void setAnteil(String anteil) {
		this.anteil = anteil;
	}

	public String getNormbezeichnung() {
		return normbezeichnung;
	}

	public void setNormbezeichnung(String normbezeichnung) {
		this.normbezeichnung = normbezeichnung;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getWerkstoff() {
		return werkstoff;
	}

	public void setWerkstoff(String werkstoff) {
		this.werkstoff = werkstoff;
	}
	
	public Material createCopy(){
		Material copy=new Material();
		copy.anteil=this.anteil;
		copy.normbezeichnung=this.normbezeichnung;
		copy.text=this.text;
		copy.werkstoff=this.werkstoff;
		
		return copy;
	}
	
	
	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "Material";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			anteil=el.getAttribute("anteil");
			normbezeichnung=el.getAttribute("normbezeichnung");
			text=el.getAttribute("text");
			werkstoff=el.getAttribute("werkstoff");
			
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
		
		e.setAttribute("anteil", this.anteil);
		e.setAttribute("normbezeichnung", this.normbezeichnung);
		e.setAttribute("text", this.text);
		e.setAttribute("werkstoff", this.werkstoff);
		
		return e;
	}
	
}
