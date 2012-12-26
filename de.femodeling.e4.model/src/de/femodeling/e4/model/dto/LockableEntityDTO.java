package de.femodeling.e4.model.dto;

import java.io.Serializable;
import java.util.UUID;

public abstract class LockableEntityDTO implements Serializable{

	/** The serial version UID. */
	   private static final long serialVersionUID = 12457L;
	   
	   
	   protected String lockableId=UUID.randomUUID().toString();
	   protected String sessionId;
	   
	public String getLockableId() {
		return lockableId;
	}
	public void setLockableId(String lockableId) {
		this.lockableId = lockableId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	   
	   
	
}
