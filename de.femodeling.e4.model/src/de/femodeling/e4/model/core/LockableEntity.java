package de.femodeling.e4.model.core;

import java.io.Serializable;
import java.util.UUID;


public abstract class LockableEntity implements Serializable{
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    private String lockableId=UUID.randomUUID().toString();
	private String sessionId;
	
	
	public String getLockableId() {
		return lockableId;
	}
	public void setLockableId(String lockableId) {
		this.lockableId = lockableId;
	}
	
	public boolean islocked() {
		if(sessionId==null)return false;
		if(sessionId.isEmpty())return false;
		return true;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	

}
