package de.femodeling.e4.server.internal.dao;

import java.util.Date;
import java.util.LinkedList;

import de.femodeling.e4.model.core.Message;
import de.femodeling.e4.model.core.lockable.LockableEntity;

public interface MessageDAOIF {
	
	/**
	 * return all the messages collected from the server since the last call
	 * 
	 * @param lastMessageCall
	 * @return
	 */
	public LinkedList<Message> getLastMesssages( final String SessionId, final Date lastMessageCall,final String lastMessageId);
	
	
	/**
	 * add a new message
	 * 
	 * @param ent
	 */
	public void addMessage(final Message ent);
	
	
	/**
	 * 
	 * add a new massage
	 * 
	 */
	//public void addMessage(String sendingSessionId,LockableEntity sendingEntity,Type sendingType);
	
	//public void addMessage(LockableEntity sendingEntity,Type sendingType);
	
	
	public void addAddMessage(LockableEntity sendingEntity,String parentId);
	public void addRemoveMessage(LockableEntity sendingEntity,String parentId);
	public void addUpdateMessage(LockableEntity sendingEntity,String parentId);
	
	public void addAddMessage(LockableEntity sendingEntity);
	public void addRemoveMessage(LockableEntity sendingEntity);
	public void addUpdateMessage(LockableEntity sendingEntity);
	
}
