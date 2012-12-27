package de.femodeling.e4.server.service.transform;

import de.femodeling.e4.model.dto.AssemblyDTO;
import de.femodeling.e4.model.dto.AssemblyDTOImpl;
import de.femodeling.e4.server.internal.model.AssemblyServerImpl;



public class AssemblyTransformationService {
	
	
	
	public static AssemblyDTO transform(AssemblyServerImpl ent){
		
		if(ent==null)return null;
		
		AssemblyDTO a=new AssemblyDTOImpl(ent.getType());
		
		a.setRoot(ent.getRoot());
		//a.setType(ent.getType());
		
		//Lock entity
		a.setLockableId(ent.getLockableId());
		a.setSessionId(ent.getSessionId());
		
		
		return a;
		
	}
	
	
	public static AssemblyServerImpl transform(AssemblyDTO ent){
		
		if(ent==null)return null;
		
		AssemblyServerImpl a=new AssemblyServerImpl(ent.getType());
		
		a.setRoot(ent.getRoot());
		//a.setType(ent.getType());
		
		//Lock entity
		a.setLockableId(ent.getLockableId());
		a.setSessionId(ent.getSessionId());
		
		return a;
		
	}
	
	
	
}
