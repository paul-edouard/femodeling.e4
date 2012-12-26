package de.femodeling.e4.model.dto;

import java.io.Serializable;
import java.util.Date;

import de.femodeling.e4.model.core.Message.Type;

public abstract class MessageDTO implements Serializable{
	
	/** The serial version UID. */
	private static final long serialVersionUID = 12457L;
	
	
	private String sendingSessionId;

	private Date creatingTime;
	    
	private Type sendingType;
	    
	private LockableEntityDTO sendingEntity;
	
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

	public LockableEntityDTO getSendingEntity() {
		return sendingEntity;
	}

	public void setSendingEntity(LockableEntityDTO sendingEntity) {
		this.sendingEntity = sendingEntity;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
