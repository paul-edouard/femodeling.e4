package de.femodeling.e4.client.model.core;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.AssemblyClientImpl;
import de.femodeling.e4.client.model.PartClientImpl;
import de.femodeling.e4.model.core.assembly.AssEdge;
import de.femodeling.e4.model.core.assembly.AssNode;


public class AssNodeClient extends AssNode{
	
	
	static final long serialVersionUID=1L;
	
	public boolean areAllChildFiltered(){
		/*
		if(this.getChildNodes().size()==1){
			for(AssNode n_c:this.getChildNodes()){
				return n_c.areAllChildFiltered();
			}
		}
		*/
		for(AssNodeClient n_c:this.getChildNodes()){
			if(!n_c.isFiltered)return false;
			
			if(!n_c.areAllChildFiltered())return false;
		}
		
		if(this.getChildNodes().size()==0)return this.isFiltered;
		
		return true;
	}

	public List<AssNodeClient> getChildNodes(){
		List<AssNodeClient> l_n=new LinkedList<AssNodeClient>();
		
		if(edgeList==null)return l_n;
			
		for(AssEdge e:edgeList){
			if(e.getNode()==null)continue;
			l_n.add( (AssNodeClient) e.getNode());
		}
		
		return l_n;
	}
	
	public String toModuleCsv(HashMap<String, PartClientImpl> parts){
		String content_csv="";
		if(!this.getId().endsWith(ROOT_ID))
		content_csv=this.getId()+";"+parts.get(this.getId()).getDescription()+";"+this.getWeight()+"\n";
		
		for(AssNodeClient n_c:this.getChildNodes())
			content_csv+=n_c.toModuleCsv(parts);
		
		return content_csv;
	}
	
	public void resetFilter(){
		this.setFiltered(false);
		for(AssNodeClient n_c:this.getChildNodes())
			n_c.resetFilter();
	}
	
	public boolean filterWithModuleId(String moduleId){
		
		if(this.getModuleId().matches(moduleId) && this.getChildNodes().size()==0){
			this.setFiltered(true);return true;
		}
		
		boolean r_value=false;
		for(AssNodeClient n_child:this.getChildNodes()){
			if(n_child.filterWithModuleId(moduleId)){this.setFiltered(true);r_value=true;}
		}
		
		return r_value;
	}
	
	
	public float getDeltaMassContribution(){
		if(this.getChildNodes().size()==0)return 1.0f;
		else{
			float counter=0.0f;
			for(AssNodeClient c:this.getChildNodes()){
				if(c.isFiltered)counter+=c.getDeltaMassContribution();
			}
			
			return counter/( (float) this.getChildNodes().size());
			
		}
	}
	
	
	
	public float calDynWeigth(HashMap<String, PartClientImpl> parts){
		float dyn_w=0;
		//if(this.isRoot()){this.setDynWeigth(dyn_w);return dyn_w;}
		
		PartClientImpl p=parts.get(this.getId());
		if(p==null){this.setDynWeigth(dyn_w);}
		
		//Calculate the dyn Weigth dor the last assembly
		if(this.getChildNodes().size()==0){
			if(!this.isFiltered()){this.setDynWeigth(dyn_w);return dyn_w;}
			
			dyn_w=this.getWeight()+this.getDeltaMass2();
			this.setDynWeigth(dyn_w);
			return dyn_w;
		}
		
		//Calculate the dyn weight of the childs
		for(AssNodeClient n_c:this.getChildNodes()){
			if(n_c==null)continue;
			if(!n_c.isFiltered())continue;
			
			PartClientImpl p_c=parts.get(n_c.getId());
			if(p_c==null){continue;}
			
			dyn_w+=n_c.calDynWeigth(parts);
		}
		
		this.setDynWeigth(dyn_w);
		return dyn_w;
	}
	
	
	

	public void calWeigth(HashMap<String, PartClientImpl> parts){
		calWeight=0;
		
		if(!this.isRoot() && parts.containsKey(this.getId())){
			PartClientImpl p=parts.get(this.getId());
			float w=Float.parseFloat(p.getWeigth()[0]);
			if(this.getChildNodes().size()==0){
				this.weight=w;
				return;
			}
		}
		
		
		if(edgeList!=null){
			
			for(AssEdge e:edgeList){
				
				if(e.getNode()==null)continue;
				
				//get child the part
				PartClientImpl p_c=parts.get(e.getNode().getId());
				if(p_c==null) continue;
				
				//calculate the child weight
				AssNodeClient nc=(AssNodeClient) e.getNode();
				
				nc.calWeigth(parts);
				
				try{
						float w=Float.parseFloat(p_c.getWeigth()[0]);
						
						if(w>0)
							calWeight+=w;
						else
							calWeight+=e.getNode().getCalWeight();
				}
				catch(Exception ex){
					
				}
				
				
				
			}
		}
		
		
		
		
		//Calculate the child delta Mass & the weight:
		PartClientImpl p=parts.get(this.getId());
		
		try{
			float w=Float.parseFloat(p.getWeigth()[0]);
			
			//set the weight
			if(w==0)this.weight=calWeight;
			else this.weight=w;
			
			//set the delta mass
			if(edgeList.size()==0)return;
			
			if(calWeight>0 && w>0){
				float d_m=(w-calWeight)/*/edgeList.size()*/;
				
				for(AssEdge e:edgeList){
					if(e.getNode()==null)continue;
					PartClientImpl p_c=parts.get(e.getNode().getId());
					if(p_c==null)continue;
					float w_c=Float.parseFloat(p_c.getWeigth()[0]);
					if(w_c>0)
						((AssNodeClient)e.getNode()).deltaMass=d_m*w_c/calWeight;
					else 
						((AssNodeClient)e.getNode()).deltaMass=d_m*e.getNode().getCalWeight()/calWeight;
				}
				
			}
			else if(w>0){
				float d_m=(w-calWeight)/edgeList.size();
				
				for(AssEdge e:edgeList){
					if(e.getNode()==null)continue;
					((AssNodeClient)e.getNode()).deltaMass=d_m;
				}
			}
			
			
		}
		catch(Exception ex){
			
		}
	}

	
	public void calculateDeltaMass2(float node_mass){
		
		
		if( this.getChildNodes().size()==0)return;
		
		float mass_sum_childs=0;
		int number_of_mass_null=0;
		for(AssNodeClient n_c:this.getChildNodes()){
			
			if(n_c.weight==0)number_of_mass_null++;
			else mass_sum_childs+=n_c.weight;
			
		}
		
		float mass_to_distribute=node_mass-mass_sum_childs;
		
		float mass_to_distribute_to_null=mass_to_distribute*( (float) number_of_mass_null)/((float)  this.getChildNodes().size());
		
		float mass_to_distribute_to_others=mass_to_distribute-mass_to_distribute_to_null;
		
		
		for(AssNodeClient n_c:this.getChildNodes()){
			
			if(mass_to_distribute>=0){
				if(n_c.weight==0){
					n_c.deltaMass2=mass_to_distribute_to_null/((float)number_of_mass_null );
				}
				else {
					n_c.deltaMass2=mass_to_distribute_to_others*n_c.weight/mass_sum_childs;
				}
			}
			else n_c.deltaMass2=mass_to_distribute*n_c.weight/mass_sum_childs;
			
			n_c.calculateDeltaMass2(n_c.weight+n_c.deltaMass2);
			
		}
		
		
		
	}
	
	
	public void calculateFeModuleMass(AssemblyClientImpl ass_pdmu){
		
		if(this.getChildNodes().size()==0){
			//System.out.println("FE-Module: "+this.getId());
			this.weight=ass_pdmu.getRoot().getMassFromModuleId(this.getModuleId());
		}	
		
		for(AssNodeClient c:this.getChildNodes()){
			c.calculateFeModuleMass(ass_pdmu);
			this.weight+=c.getWeight();
		}
		
	}
	
	private float getMassFromModuleId(String moduleId){
		float mass=0;
		
		//System.out.println("Pdmu Node Id: "+this.getId());
		if(this.getChildNodes().size()==0 && this.getModuleId().equals(moduleId)){
			//System.out.println("Mass: "+this.getWeight()+this.getDeltaMass2());
			return this.getWeight()+this.getDeltaMass2();
		}
		
		for(AssNodeClient c:this.getChildNodes()){
			mass+=c.getMassFromModuleId( moduleId);
		}
		
		return mass;
	}
	
	
	
	
	
	public void setEdgeInstanceId(HashMap<String, Integer> instanceMap,HashMap<String, PartClientImpl> parts){
		
		PartClientImpl p= parts.get(this.getId());
		if(p!=null){
		if(instanceMap.containsKey(p.getAnsaModuleId())){
			if(this.getParent()!=null){
				this.getParent().setInstanceId(instanceMap.get(p.getAnsaModuleId())+1);
				instanceMap.put(p.getAnsaModuleId(), this.getParent().getInstanceId());
				
			}
		}
		else
			instanceMap.put(p.getAnsaModuleId(), 1);
		}
		
		for(AssNodeClient n_c:this.getChildNodes()){
			if(!n_c.isFiltered())continue;
				
			n_c.setEdgeInstanceId(instanceMap,parts);
		}
		
	}

	

	public static void setExportFilteredOnly(boolean exportFilteredOnly) {
		AssNode.exportFilteredOnly = exportFilteredOnly;
	}

	
	
	
	//TODO Combine Structure
	
	public LinkedList<String> combineStructure(AssNodeClient node){
		LinkedList<String> duplicateNodeId =new LinkedList<String>();
		
		combineStructure(this,node,duplicateNodeId);
		
		return duplicateNodeId;
	}
	
	private void combineStructure(AssNodeClient master,AssNodeClient slave ,LinkedList<String> duplicateNodeId){
		
		
		for(AssNodeClient slave_childs:slave.getChildNodes()){
			AssNodeClient master_child=master.getChildWithId(slave_childs.getId());
			if(master_child==null){
				master.addEdge(slave_childs.getParent());
			}
			else{
				duplicateNodeId.add(slave_childs.getId());
				combineStructure(master_child,slave_childs,duplicateNodeId);
			}
		}
		
	}

	private AssNodeClient getChildWithId(String node_id){
		
		for(AssNodeClient c:this.getChildNodes()){
			if(c.getId().equals(node_id))return c;
		}
		
		return null;
	}
	
}
