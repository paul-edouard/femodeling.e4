package de.femodeling.e4.server.internal.transform;

import de.femodeling.e4.model.core.lockable.LockableEntity;
import de.femodeling.e4.model.core.lockable.LockableEntityProChanSupp;
import de.femodeling.e4.server.internal.model.LockableEntityServerImpl;



public class LockTransformService {
	
	public static LockableEntity transform(LockableEntityServerImpl ent){
		LockableEntity e=new LockableEntityProChanSupp();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		e.setParameter(ent.getParameter());
		
		return e;
	}
	
	public static LockableEntityServerImpl transform(LockableEntity ent){
		LockableEntityServerImpl e=new LockableEntityServerImpl();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		e.setParameter(ent.getParameter());
		
		return e;
		
	}
	
	public static LockableEntity copy(LockableEntity ent){
		LockableEntity e=new LockableEntityProChanSupp();
		
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		e.setParameter(ent.getParameter());
		
		return e;
		
	}
	

	
}
