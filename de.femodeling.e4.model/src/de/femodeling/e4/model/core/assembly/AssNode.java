package de.femodeling.e4.model.core.assembly;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlElementIF;



public class AssNode implements XmlElementIF, Serializable {
	
	static final long serialVersionUID=1L;
	public static final String ROOT_ID="root";
	
	static final String IdStr="id";
	static final String ModuleIdStr="module_id";
	static final String UUIDStr="UUID";
	
	
	protected String uuid=UUID.randomUUID().toString();
	
	
	protected String id;
	protected LinkedList<AssEdge> edgeList;
	protected AssEdge parent;
	protected String moduleId="None";
	
	protected float calWeight=0;
	protected float weight=0;
	protected float deltaMass=0;
	protected float deltaMass2=0;
	protected float dynWeigth=0;
	
	
	protected boolean isFiltered=true;
	
	//private boolean isFeModule=false;
	
	protected static boolean exportFilteredOnly=false;
	
	protected static Logger logger = Logger.getLogger(AssNode.class);
	
	public String getTagName(){return "node";}
	
	public  boolean isFeModule(){
		return id.matches("(\\d{1}).(\\d{2}).(\\d{2})");
	}
	
	/*
	public void setFeModule(boolean isFeModule) {
		this.isFeModule = isFeModule;
		for(AssNode c:this.getChildNodes())
			c.setFeModule(isFeModule);
	}
	*/

	

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public void setCalWeight(float calWeight) {
		this.calWeight = calWeight;
	}



	public void setWeight(float weight) {
		this.weight = weight;
	}


	/*
	public boolean isFeModule() {
		return isFeModule;
	}
	*/


	public String getModuleId() {
		return moduleId;
	}


	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}




	public AssNode(){
		edgeList=new LinkedList<AssEdge>();
	}
	
	public AssNode(Element el){
		edgeList=new LinkedList<AssEdge>();
		init(el);
	}
	
	
	
	public AssEdge getParent() {
		return parent;
	}


	public void setParent(AssEdge parent) {
		this.parent = parent;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	
	public boolean isRoot(){
		return id.equals(ROOT_ID);
	}
	
	public LinkedList<AssEdge> getEdgeList() {
		return edgeList;
	}
	
	public boolean addEdge(AssEdge edge){
		if(edge==null)return false;
		edge.setParent(this);
		return edgeList.add(edge);
		
	}
	
	
	public float getCalWeight() {
		return calWeight;
	}
	
	
	

	public boolean isFiltered() {
		return isFiltered;
	}



	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}



	public float getDeltaMass() {
		return deltaMass;
	}
	
	
	
	public float getWeight() {
		return weight;
	}



	public float getDeltaMass2() {
		return deltaMass2;
	}
	
	

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			id=el.getAttribute(AssNode.IdStr);
			moduleId=el.getAttribute(AssNode.ModuleIdStr);
			uuid=el.getAttribute(AssNode.UUIDStr);
			
			//if(!moduleId.equals("None"))System.out.println(moduleId+" Node:"+this);
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals(new AssEdge().getTagName())){
						AssEdge e=new AssEdge(chilElement);
						addEdge(e);
					}
					
				}
			}
			
			
		}else{
			logger.warn("this is not a valid pmc node element");
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
		
		e.setAttribute(AssNode.IdStr, this.id);
		e.setAttribute(AssNode.ModuleIdStr, this.moduleId);
		e.setAttribute(AssNode.UUIDStr, this.uuid);
		//e.setAttribute("dyn_weigth", String.valueOf(dynWeigth));
		
		for(AssEdge edge:edgeList){
			if( exportFilteredOnly &&   !edge.getNode().isFiltered())continue;
			
			Element child=edge.toDomElement(Doc);
			e.appendChild(child);
			
		}

		return e;
	}



	@Override
	public String toString() {
		return "AssNode [id=" + id + ", edgeList=" + edgeList + ", parent="
				+ parent + ", moduleId=" + moduleId + ", calWeight="
				+ calWeight + ", deltaMass=" + deltaMass + "]";
	}
	
	
	

}
