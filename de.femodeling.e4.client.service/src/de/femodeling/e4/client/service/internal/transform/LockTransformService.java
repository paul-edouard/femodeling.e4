package de.femodeling.e4.client.service.internal.transform;


import de.femodeling.e4.model.core.lockable.LockableEntity;
import de.femodeling.e4.model.core.lockable.LockableEntityProChanSupp;



public class LockTransformService {
	
	public static LockableEntity transform(LockableEntity ent){
		LockableEntityProChanSupp e=new LockableEntityProChanSupp();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		e.setParameter(ent.getParameter());
		
		return e;
	}
	
	
	
	
	
	
	
	
}
