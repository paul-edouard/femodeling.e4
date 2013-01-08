package de.femodeling.e4.model.core.lockable;

import java.io.Serializable;
import java.util.UUID;

import de.femodeling.e4.model.core.parameter.Parameter;


public abstract class LockableEntity implements Serializable{
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    private String lockableId=UUID.randomUUID().toString();
	private String sessionId;
	
	
	protected Parameter parameter;
	    
	public Parameter getParameter() {
			if(parameter==null)parameter=Parameter.createRoot(this.getClass());
			return parameter;
	}

	public void setParameter(Parameter parameter) {
			this.parameter = parameter;
	}
	
	
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
