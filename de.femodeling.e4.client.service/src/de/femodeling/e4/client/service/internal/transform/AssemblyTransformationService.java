package de.femodeling.e4.client.service.internal.transform;

import de.femodeling.e4.client.model.AssemblyClientImpl;
import de.femodeling.e4.client.model.core.AssNodeClient;
import de.femodeling.e4.model.core.assembly.AssEdge;
import de.femodeling.e4.model.core.assembly.AssNode;
import de.femodeling.e4.model.dto.AssemblyDTO;
import de.femodeling.e4.model.dto.AssemblyDTOImpl;


public class AssemblyTransformationService {
	
	
	
	public static AssemblyClientImpl transformClient(AssemblyDTO ent){
		
		if(ent==null)return null;
		
		AssemblyClientImpl a=new AssemblyClientImpl(ent.getType());
		
		a.setRoot(transformClient(ent.getRoot(),true));
		//a.setType(ent.getType());
		
		//Lock entity
		a.setLockableId(ent.getLockableId());
		a.setSessionId(ent.getSessionId());
		a.setParameter(ent.getParameter());
		
		return a;
		
	}
	
	public static AssemblyDTO transformClient(AssemblyClientImpl ent){
		
		if(ent==null)return null;
		
		AssemblyDTO a=new AssemblyDTOImpl(ent.getType());
		
		a.setRoot(ent.getRoot());
		//a.setType(ent.getType());
		
		//Lock entity
		a.setLockableId(ent.getLockableId());
		a.setSessionId(ent.getSessionId());
		a.setParameter(ent.getParameter());
		
		return a;
		
	}
	
	
	public static AssNodeClient transformClient(AssNode ent,boolean recursive){
		
		AssNodeClient a_n=new AssNodeClient();
		
		a_n.setDynWeigth(ent.getDynWeigth());
		a_n.setCalWeight(ent.getCalWeight());
		//a_n.setFeModule(ent.isFeModule());
		a_n.setId(ent.getId());
		a_n.setModuleId(ent.getModuleId());
		a_n.setUuid(ent.getUuid());
		a_n.setWeight(ent.getWeight());
		a_n.setParameter(ent.getParameter());
		
		if(recursive){
			for(AssEdge c:ent.getEdgeList())
				a_n.addEdge(createCopy(c,recursive));
		}
		
		return a_n;
		
	}
	
	
	public static AssNode createCopy(AssNode ent,boolean recursive){
		AssNode a_n=new AssNode();
		
		a_n.setDynWeigth(ent.getDynWeigth());
		a_n.setCalWeight(ent.getCalWeight());
		//a_n.setFeModule(ent.isFeModule());
		a_n.setId(ent.getId());
		a_n.setModuleId(ent.getModuleId());
		a_n.setUuid(ent.getUuid());
		a_n.setWeight(ent.getWeight());
		a_n.setParameter(ent.getParameter());
		
		if(recursive){
			for(AssEdge c:ent.getEdgeList())
				a_n.addEdge(createCopy(c,recursive));
		}
		
		return a_n;
		
	}
	
	public static AssEdge transformClient(AssEdge ent,boolean recursive){
		
		AssEdge a_e=new AssEdge();
		
		a_e.setAbsTmx(ent.getAbsTmx());
		a_e.setDescription(ent.getDescription());
		a_e.setId(ent.getId());
		a_e.setInstanceId(ent.getInstanceId());
		a_e.setRelTmx(ent.getRelTmx());
		
		if(recursive && ent.getNode()!=null){
			a_e.setNode(transformClient(ent.getNode(),recursive));
		}
			
		
		return a_e;
		
	}
	
	public static AssEdge createCopy(AssEdge ent,boolean recursive){
		
		AssEdge a_e=new AssEdge();
		
		a_e.setAbsTmx(ent.getAbsTmx());
		a_e.setDescription(ent.getDescription());
		a_e.setId(ent.getId());
		a_e.setInstanceId(ent.getInstanceId());
		a_e.setRelTmx(ent.getRelTmx());
		
		if(recursive && ent.getNode()!=null){
			a_e.setNode(createCopy(ent.getNode(),recursive));
		}
			
		
		return a_e;
		
	}
	
	
	

}
