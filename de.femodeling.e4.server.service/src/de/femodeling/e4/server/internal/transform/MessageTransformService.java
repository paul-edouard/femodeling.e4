package de.femodeling.e4.server.internal.transform;

import de.femodeling.e4.model.core.Message;
import de.femodeling.e4.model.core.lockable.LockableEntity;
import de.femodeling.e4.model.dto.MessageDTO;
import de.femodeling.e4.model.dto.MessageDTOImpl;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.model.dto.UserDTO;
import de.femodeling.e4.server.internal.model.MessageServerImpl;
import de.femodeling.e4.server.internal.model.PartServerImpl;
import de.femodeling.e4.server.internal.model.ProjectServerImpl;
import de.femodeling.e4.server.internal.model.UserServerImpl;


public class MessageTransformService {
	
	public static MessageDTO transform(Message mes){
		MessageDTO m=new MessageDTOImpl();
		
		m.setCreatingTime(mes.getCreatingTime());
		m.setLockableId(mes.getLockableId());
		
		//TODO
		if(mes.getSendingEntity() instanceof ProjectServerImpl){
			ProjectDTO p_dto=ProjectTransformService.transform((ProjectServerImpl)mes.getSendingEntity(), true);
			m.setSendingEntity(p_dto);
		}
		else if(mes.getSendingEntity() instanceof PartServerImpl){
			PartDTO p_dto=PartTransformService.transform((PartServerImpl)mes.getSendingEntity() );
			m.setSendingEntity(p_dto);
		}
		else if(mes.getSendingEntity() instanceof UserServerImpl){
			UserDTO u_dto=UserTransformService.transform((UserServerImpl)mes.getSendingEntity() );
			m.setSendingEntity(u_dto);
		}
		else if(mes.getSendingEntity() instanceof LockableEntity){
			LockableEntity l_dto=LockTransformService.copy( mes.getSendingEntity());
			m.setSendingEntity(l_dto);
		}
		
		m.setSendingSessionId(mes.getSendingSessionId());
		m.setSendingType(mes.getSendingType());
		m.setParentId(mes.getParentId());
		
		m.setParameter(mes.getParameter());
		
		return m;
	}
	
	
	public static Message transform(MessageDTO mes){
		Message m=new MessageServerImpl();
		
		m.setCreatingTime(mes.getCreatingTime());
		m.setLockableId(mes.getLockableId());
		
		//TODO
		if(mes.getSendingEntity() instanceof ProjectDTO){
			ProjectServerImpl p_s=ProjectTransformService.transform((ProjectDTO) mes.getSendingEntity());
			m.setSendingEntity(p_s);
		}
		else if(mes.getSendingEntity() instanceof PartDTO){
			PartServerImpl p_s=PartTransformService.transform((PartDTO)  mes.getSendingEntity());
			m.setSendingEntity(p_s);
		}
		else if(mes.getSendingEntity() instanceof UserDTO){
			UserServerImpl p_s=UserTransformService.transform((UserDTO)  mes.getSendingEntity());
			m.setSendingEntity(p_s);
		}
		else if(mes.getSendingEntity() instanceof LockableEntity){
			LockableEntity l_dto=LockTransformService.transform(mes.getSendingEntity());
			m.setSendingEntity(l_dto);
		}
		
		m.setSendingSessionId(mes.getSendingSessionId());
		m.setSendingType(mes.getSendingType());
		m.setParentId(mes.getParentId());
		
		m.setParameter(mes.getParameter());
		
		return m;
	}
	

}
