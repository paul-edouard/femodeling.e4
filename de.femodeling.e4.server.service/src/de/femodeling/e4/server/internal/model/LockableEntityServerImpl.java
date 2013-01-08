package de.femodeling.e4.server.internal.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.core.lockable.LockableEntity;
import de.femodeling.e4.model.xml.XmlElementIF;


public class LockableEntityServerImpl extends LockableEntity implements XmlElementIF {
	
	static final long serialVersionUID=10123;
	
	public String getTagName(){return "lockableEntity";}

	public Element toDomElement(Document doc){
		Document Doc=doc;
		Element ele=Doc.createElement(this.getTagName());
		
		ele.setAttribute("lockableId", this.getLockableId());
		ele.setAttribute("sessionId", this.getSessionId());		
		
		return ele;
	}
	
	
	public void init(Element el){
		
		if(!el.getTagName().equals(this.getTagName()))return;
			
		this.setLockableId(el.getAttribute("lockableId"));
		this.setSessionId(el.getAttribute("sessionId"));
		
		
	}

}
