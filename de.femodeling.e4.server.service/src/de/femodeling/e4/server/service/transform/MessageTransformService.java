package de.femodeling.e4.server.service.transform;

import de.femodeling.e4.model.core.LockableEntity;
import de.femodeling.e4.model.core.Message;
import de.femodeling.e4.model.dto.LockableEntityDTO;
import de.femodeling.e4.model.dto.MessageDTO;
import de.femodeling.e4.model.dto.MessageDTOImpl;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.internal.model.MessageServerImpl;
import de.femodeling.e4.server.internal.model.PartServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;


public class MessageTransformService {
	
	public static MessageDTO transform(Message mes){
		MessageDTO m=new MessageDTOImpl();
		
		m.setCreatingTime(mes.getCreatingTime());

		//TODO
		if(mes.getSendingEntity() instanceof ProjectServerImpl){
			ProjectDTO p_dto=ProjectTransformService.transform((ProjectServerImpl)mes.getSendingEntity(), true);
			m.setSendingEntity(p_dto);
		}
		else if(mes.getSendingEntity() instanceof PartServerImpl){
			PartDTO p_dto=PartTransformService.transform((PartServerImpl)mes.getSendingEntity() );
			m.setSendingEntity(p_dto);
		}
		else if(mes.getSendingEntity() instanceof LockableEntity){
			LockableEntityDTO l_dto=LockTransformService.transform( mes.getSendingEntity());
			m.setSendingEntity(l_dto);
		}
		
		m.setSendingSessionId(mes.getSendingSessionId());
		m.setSendingType(mes.getSendingType());
		m.setParentId(mes.getParentId());
		
		return m;
	}
	
	
	public static Message transform(MessageDTO mes){
		Message m=new MessageServerImpl();
		
		m.setCreatingTime(mes.getCreatingTime());
		
		//TODO
		if(mes.getSendingEntity() instanceof ProjectDTO){
			ProjectServerImpl p_s=ProjectTransformService.transform((ProjectDTO) mes.getSendingEntity());
			m.setSendingEntity(p_s);
		}
		else if(mes.getSendingEntity() instanceof PartDTO){
			PartServerImpl p_s=PartTransformService.transform((PartDTO)  mes.getSendingEntity());
			m.setSendingEntity(p_s);
		}
		else if(mes.getSendingEntity() instanceof LockableEntityDTO){
			LockableEntity l_dto=LockTransformService.transform(mes.getSendingEntity());
			m.setSendingEntity(l_dto);
		}
		
		m.setSendingSessionId(mes.getSendingSessionId());
		m.setSendingType(mes.getSendingType());
		m.setParentId(mes.getParentId());
		
		return m;
	}
	

}
