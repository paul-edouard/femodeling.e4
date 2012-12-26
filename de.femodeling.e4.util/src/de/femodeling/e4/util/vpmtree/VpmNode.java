package de.femodeling.e4.util.vpmtree;

import java.util.HashMap;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.assembly.AssEdge;
import de.femodeling.e4.model.core.assembly.AssNode;



public class VpmNode extends VpmXmlElement{
	
	private String id;
	private LinkedList<VpmEdge> edgeList;
	
	
	public static String getTagName(){return "node";}
	
	public VpmNode(Element el){
		edgeList=new LinkedList<VpmEdge>();
		
		init(el);
	}
	
	
	
	
	public LinkedList<VpmEdge> getEdgeList() {
		return edgeList;
	}

	public LinkedList<VpmNode> getChilds(){
		LinkedList<VpmNode> c_l=new LinkedList<VpmNode>();
		
		for(VpmEdge e:edgeList){
			if(e.getNode()!=null)
				c_l.add(e.getNode());
		}
		return c_l;
	}
	
	public String getId() {
		return id;
	}

	public VpmNode(String id){
		edgeList=new LinkedList<VpmEdge>();
		this.id=id;
	}
	
	
	public void addEdge(VpmEdge e){
		edgeList.add(e);
	}
	
	public AssNode toAssNode(){
		AssNode n=new AssNode();
		
		n.setId(id);
		for(VpmEdge edge: edgeList)
			n.addEdge(edge.toAssEdge());
		
		return n;
	}
	
	public AssNode toFeModuleNode(HashMap<String, VpmPart> vpmPartMap){
		
		VpmPart vpart=vpmPartMap.get(id);
		if(vpart==null)return null;
		
		if(vpart.isFeModule()){
			AssNode n=new AssNode();
			n.setId(vpart.getModuleId());
			n.setModuleId(vpart.getModuleId());
			for(VpmEdge edge: edgeList){
				VpmNode n_c=edge.getNode();
				VpmPart vpart_c=vpmPartMap.get(n_c.getId());
				if(vpart_c.isFeModule()){
					AssEdge e_c=edge.toFeModuleEdge(vpmPartMap);
					e_c.setId(vpart_c.getModuleId());
					n.addEdge(e_c);
				}
			}
			
			return n;
		}
			
		return null;	
		
	}
	
	
	public void init(Element el){
		
		if(el.getTagName().equals(VpmNode.getTagName())){
			
			id=el.getAttribute("id");
			//System.out.println(this);
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals(VpmEdge.getTagName())){
						VpmEdge e=new VpmEdge(chilElement);
						edgeList.add(e);
					}
					
				}
			}
			
			
		}else{
			System.out.println("this is not a valid Vpm node element");
		}
	}
	
	
	public void parseStructure(HashMap<String, VpmPart> vpmPartMap,int level){
		VpmPart p=vpmPartMap.get(this.id);
		
		if(p!=null){
			//if(p.getName().startsWith("918.000408_"))
			//System.out.println("Level: "+level+" "+p.getName()+", "+p.getDescription()+", "+p.getType());
			level++;
			for(VpmEdge e:this.edgeList){
				e.getNode().parseStructure(vpmPartMap, level);
			}
			
		}
		
	}
	
	/**
	 * this function parse the structure to transmit the materials from parent to child
	 */
	public void transmitMaterialsToChild(HashMap<String, VpmPart> vpmPartMap,LinkedList<VpmMaterial>  parentMat){
		VpmPart p=vpmPartMap.get(this.id);
		
		if(p!=null){
			
			if(parentMat!=null && p.getMaterialList().isEmpty()  /* && p.getMaterialWithTheMajorPercentage()==null*/){
				for(VpmMaterial mat:parentMat)
					p.addMaterial(mat);
			}
			
			
			for(VpmEdge e:this.edgeList){
				e.getNode().transmitMaterialsToChild(vpmPartMap, p.getMaterialList());
			}
			
		}
		
	}
	
	

	@Override
	public String toString() {
		return "VpmNode [id=" + id + "]";
	}
	
	
	
}
