package de.femodeling.e4.model.core.assembly;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlElementIF;

public class AssEdge implements XmlElementIF, Serializable{
	
	static final long serialVersionUID=1L;
	
	public static final String ROOT_ID="root";
	
	static final String IdStr="id";
	static final String DescriptionStr="description";
	
	private String id;
	private String description;
	private int instanceId=1;
	
	private AssNode node;
	private AssNode parent;
	private AssTmx absTmx;
	private AssTmx relTmx;
	
	
	private static Logger logger = Logger.getLogger(AssEdge.class);
	
	public String getTagName(){return "edge";}
	
	public AssEdge(Element el){
		absTmx=new AssTmx(AssTmx.Type.ABS);
		relTmx=new AssTmx(AssTmx.Type.REL);
		init(el);
	}
	
	public AssEdge(){
		absTmx=new AssTmx(AssTmx.Type.ABS);
		relTmx=new AssTmx(AssTmx.Type.REL);
	}
	
	

	public AssNode getParent() {
		return parent;
	}

	public void setParent(AssNode parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AssNode getNode() {
		return node;
	}

	public void setNode(AssNode node) {
		this.node = node;
		this.node.setParent(this);
	}

	public AssTmx getAbsTmx() {
		return absTmx;
	}

	public void setAbsTmx(AssTmx absTmx) {
		this.absTmx = absTmx;
	}

	public AssTmx getRelTmx() {
		return relTmx;
	}

	public void setRelTmx(AssTmx relTmx) {
		this.relTmx = relTmx;
	}


	public int getInstanceId() {
		return instanceId;
	}
	
	
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
	

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			id=el.getAttribute(AssEdge.IdStr);
			description=el.getAttribute(AssEdge.DescriptionStr);
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals(new AssNode().getTagName())){
						AssNode n=new AssNode(chilElement);
						setNode(n);
					}
					else if(chilElement.getTagName().equals(absTmx.getTagName())){
						AssTmx t=new AssTmx(chilElement,AssTmx.Type.ABS);
						absTmx=t;
						//System.out.println(absTmx);
					}
					else if(chilElement.getTagName().equals(relTmx.getTagName())){
						AssTmx t=new AssTmx(chilElement,AssTmx.Type.REL);
						relTmx=t;
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
		
		e.setAttribute(AssEdge.IdStr, this.id);
		e.setAttribute(AssEdge.DescriptionStr, this.description);
		
		if(absTmx!=null){
			Element child=absTmx.toDomElement(Doc);
			e.appendChild(child);
		}
		
		if(relTmx!=null){
			Element child=relTmx.toDomElement(Doc);
			e.appendChild(child);
		}
		
		if(node!=null){
			Element child=node.toDomElement(Doc);
			e.appendChild(child);
		}
		
		

		return e;
	}
	
	
	
	
	

}
