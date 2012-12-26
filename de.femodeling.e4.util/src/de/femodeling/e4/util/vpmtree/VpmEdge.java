package de.femodeling.e4.util.vpmtree;


import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.assembly.AssEdge;



public class VpmEdge extends VpmXmlElement{
	
	private String id;
	private String description;
	
	private VpmNode node;
	private VpmTmx absTmx;
	private VpmTmx relTmx;
	
	
	
	public String getId() {
		return id;
	}

	public VpmNode getNode() {
		return node;
	}

	public static String getTagName(){return "edge";}
	
	public VpmEdge(String id,VpmTmx absTmx,VpmTmx relTmx){
		this.id=id;
		this.absTmx=absTmx;
		this.relTmx=relTmx;
	}
	
	
	public void setNode(VpmNode node) {
		this.node = node;
	}

	public VpmEdge(Element el){
		
		init(el);
	}
	
	
	public AssEdge toAssEdge(){
		AssEdge edge=new AssEdge();
		
		edge.setNode(node.toAssNode());
		edge.setId(id);
		edge.setDescription(description);
		edge.setAbsTmx(absTmx.toAssTmx());
		edge.setRelTmx(relTmx.toAssTmx());
		
		return edge;
	}
	
	public AssEdge toFeModuleEdge(HashMap<String, VpmPart> vpmPartMap){
		AssEdge edge=new AssEdge();
		
		edge.setNode(node.toFeModuleNode(vpmPartMap));
		edge.setId(id);
		edge.setDescription(description);
		edge.setAbsTmx(absTmx.toAssTmx());
		edge.setRelTmx(relTmx.toAssTmx());
		
		return edge;
	}
	
	public void init(Element el){
		
		if(el.getTagName().equals(VpmEdge.getTagName())){
			
			id=el.getAttribute("id");
			description=el.getAttribute("description");
			
			//System.out.println(this);
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals(VpmNode.getTagName())){
						VpmNode n=new VpmNode(chilElement);
						node=n;
					}
					else if(chilElement.getTagName().equals("absTmx")){
						VpmTmx t=new VpmTmx(chilElement);
						absTmx=t;
					}
					else if(chilElement.getTagName().equals("relTmx")){
						VpmTmx t=new VpmTmx(chilElement);
						relTmx=t;
					}
					
					
				}
			}
			
		}else{
			System.out.println("this is not a valid Vpm edge element");
		}
	}

	@Override
	public String toString() {
		return "VpmEdge [id=" + id + ", description=" + description + ", node="
				+ node + ", absTmx=" + absTmx + ", relTmx=" + relTmx + "]";
	}

	
	
	

}
