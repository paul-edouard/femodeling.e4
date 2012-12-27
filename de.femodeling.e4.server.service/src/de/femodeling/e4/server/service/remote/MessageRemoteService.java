package de.femodeling.e4.server.service.remote;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import de.femodeling.e4.internal.context.ServerContextProvider;
import de.femodeling.e4.model.core.Message;
import de.femodeling.e4.model.dto.MessageDTO;
import de.femodeling.e4.server.service.dao.MessageDAOIF;
import de.femodeling.e4.server.service.dao.MessageDAOImpl;
import de.femodeling.e4.server.service.dao.RegisterServerDAOService;
import de.femodeling.e4.server.service.transform.MessageTransformService;



public class MessageRemoteService implements MessageRemoteServiceIF {
	
	static final long serialVersionUID=1L;
	
	
	/** The logger instance */
	private static final Logger logger = Logger.getLogger(MessageRemoteService.class);
		
	
	//private LockableEntityDAOImpl lockableEntityDAO;
	
	
	public MessageRemoteService(){
		//BasicConfigurator.configure();
		logger.info("------->Message Service started");
		MessageDAOIF messageDAO=new MessageDAOImpl();
		RegisterServerDAOService.INSTANCE.setMessageDAO(messageDAO);
		
	}
	
	
	@Override
	public LinkedList<MessageDTO> getLastMesssages( final Date lastMessageCall) {
		//logger.info("Get the last Messages");
		String s_id=ServerContextProvider.getServerContext().getSessionId();
		
		LinkedList<Message> m_list=RegisterServerDAOService.INSTANCE.getMessageDAO().getLastMesssages(s_id, lastMessageCall);
		LinkedList<MessageDTO> m_dto_list=new LinkedList<MessageDTO>();
		for (Message message : m_list) {
			m_dto_list.add(MessageTransformService.transform(message));
		}
		
		return m_dto_list;
	}
	
	public void sendMessage(final MessageDTO ent){
		logger.info("New Message");
		RegisterServerDAOService.INSTANCE.getMessageDAO().addMessage(MessageTransformService.transform(ent));
		//MessageProvider.INSTANCE.addMessage(ent);
	}

}
