package de.femodeling.e4.internal.model;

import java.util.Calendar;

import de.femodeling.e4.model.core.LockableEntity;
import de.femodeling.e4.model.core.Message;


public class MessageServerImpl extends Message {
	
	static final long serialVersionUID=10123;
	
	 public MessageServerImpl(String sendingSessionId,LockableEntity sendingEntity,Type sendingType){
	    	this.setSendingSessionId(sendingSessionId);
	    	this.setSendingEntity(sendingEntity);
	    	this.setSendingType(sendingType);
	    	
	    	this.setCreatingTime(Calendar.getInstance().getTime());
	    	
	    }
	
	 public MessageServerImpl(){
		 
	 }
	 
	
}
