package de.femodeling.e4.server.service.remote;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import de.femodeling.e4.model.dto.MessageDTO;


public interface MessageRemoteServiceIF extends Serializable{
	
	
	public LinkedList<MessageDTO> getLastMesssages( final Date lastMessageCall);
	
	public void sendMessage(final MessageDTO ent);

}
