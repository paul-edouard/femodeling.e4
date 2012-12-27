package de.femodeling.e4.server.internal.transform;

import de.femodeling.e4.model.core.LockableEntity;
import de.femodeling.e4.model.dto.LockableEntityDTO;
import de.femodeling.e4.model.dto.LockableEntityDTOImpl;
import de.femodeling.e4.server.internal.model.LockableEntityServerImpl;



public class LockTransformService {
	
	public static LockableEntityDTO transform(LockableEntity ent){
		LockableEntityDTO e=new LockableEntityDTOImpl();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		
		return e;
	}
	
	public static LockableEntityServerImpl transform(LockableEntityDTO ent){
		LockableEntityServerImpl e=new LockableEntityServerImpl();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		
		return e;
		
	}
	

	
}
