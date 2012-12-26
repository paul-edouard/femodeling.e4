package de.femodeling.e4.model.core.connection;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlElementIF;

public class ConnectionZSB implements XmlElementIF, Serializable{
	
	static final long serialVersionUID=1L;
	
	private static Logger logger = Logger.getLogger(ConnectionZSB.class);
	
	private String idx;
	private String name;
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VtaZSB [idx=" + idx + ", name=" + name + "]";
	}
	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "ZSB";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals("ZSBParameter")){
						String attr=chilElement.getAttribute("Name");
						if(attr.equals("Idx."))this.idx=chilElement.getTextContent();
						else if(attr.equals("ZSB Name"))this.name=chilElement.getTextContent();
						
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
		
		Element e_idx=Doc.createElement("ZSBParameter");
		e_idx.setAttribute("Name", "Idx.");
		e_idx.setTextContent(this.idx);
		e.appendChild(e_idx);
		
		Element e_name=Doc.createElement("ZSBParameter");
		e_name.setAttribute("Name", "ZSB Name");
		e_name.setTextContent(this.name);
		e.appendChild(e_name);
		
		
		
		return e;
	}
	
}
