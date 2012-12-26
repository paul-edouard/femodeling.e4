package de.femodeling.e4.model.core;

import java.io.Serializable;
import java.util.Date;



public abstract class Message  implements Serializable{

	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public static final int MESSAGE_EXPIRATION = 240;
    
    public enum Type { ADD, REMOVE, UPDATE }

    private String sendingSessionId;

    private Date creatingTime;
    
    private Type sendingType;
    
    private LockableEntity sendingEntity;
    
    private String parentId;

	public String getSendingSessionId() {
		return sendingSessionId;
	}

	public void setSendingSessionId(String sendingSessionId) {
		this.sendingSessionId = sendingSessionId;
	}

	public Date getCreatingTime() {
		return creatingTime;
	}

	public void setCreatingTime(Date creatingTime) {
		this.creatingTime = creatingTime;
	}

	public Type getSendingType() {
		return sendingType;
	}

	public void setSendingType(Type sendingType) {
		this.sendingType = sendingType;
	}

	public LockableEntity getSendingEntity() {
		return sendingEntity;
	}

	public void setSendingEntity(LockableEntity sendingEntity) {
		this.sendingEntity = sendingEntity;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
    
    
    
	
}
