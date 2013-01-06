package de.femodeling.e4.client.service.internal.transform;

import de.femodeling.e4.client.model.LockableEntityClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.LockableEntity;
import de.femodeling.e4.model.dto.LockableEntityDTO;
import de.femodeling.e4.model.dto.LockableEntityDTOImpl;
import de.femodeling.e4.model.dto.ProjectDTOImpl;



public class LockTransformService {
	
	public static LockableEntityDTO transform(LockableEntity ent){
		LockableEntityDTO e=new LockableEntityDTOImpl();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		
		return e;
	}
	
	
	
	
	public static LockableEntityDTO transformClient(LockableEntityClientImpl ent){
		
		
		if(ent instanceof ProjectClientImpl){
			ProjectClientImpl p=(ProjectClientImpl) ent;
			return ProjectTransformService.transformClient(p);
			
		}
		else{
			LockableEntityDTO e=new LockableEntityDTOImpl();
			
			e.setLockableId(ent.getLockableId());
			e.setSessionId(ent.getSessionId());
			
			return e;
		}
		
		
	}
	
	
	public static LockableEntityClientImpl transformClient(LockableEntityDTO ent){
		
		if(ent instanceof ProjectDTOImpl){
			ProjectDTOImpl p=(ProjectDTOImpl) ent;
			
			return ProjectTransformService.transformClient(p, true);
			
		}
		else{
			LockableEntityClientImpl e=new LockableEntityClientImpl();
			e.setLockableId(ent.getLockableId());
			e.setSessionId(ent.getSessionId());
			
			return e;
			
		}
		
	}
	
}
