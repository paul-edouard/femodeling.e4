package de.femodeling.e4.server.service.transform;

import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.internal.model.ConnectionElementServerImpl;
import de.femodeling.e4.model.dto.ConnectionElementDTO;
import de.femodeling.e4.model.dto.ConnectionElementDTOImpl;

public class ConnectionElementTransformService {
	
	/***********************************
	 *                                 *
	 *		       SERVER              *
	 *                                 *
	 ***********************************/
	
	public static ConnectionElementDTO transform(ConnectionElementServerImpl ent){
		
		ConnectionElementDTO e=new ConnectionElementDTOImpl();
		
		e.setDate(ent.getDate());
		e.setParameterList(ent.getParameterList());
		e.setPartMap(ent.getPartMap());
		e.setSupportPointList(ent.getSupportPointList());
		e.setVtaTable(ent.getVtaTable());
		e.setZsbMap(ent.getZsbMap());
		
		//Lock entity
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		
		return e;
	}
	
	
	public static List<ConnectionElementDTO> transformServerList(List<ConnectionElementServerImpl> ent_l){
		List<ConnectionElementDTO> e_l=new LinkedList<ConnectionElementDTO>();
		
		for(ConnectionElementServerImpl ent:ent_l){
			e_l.add(transform(ent));
		}
		
		return e_l;
	}
	
	
	public static ConnectionElementServerImpl transform(ConnectionElementDTO ent){
		ConnectionElementServerImpl e=new ConnectionElementServerImpl();
		
		e.setDate(ent.getDate());
		e.setParameterList(ent.getParameterList());
		e.setPartMap(ent.getPartMap());
		e.setSupportPointList(ent.getSupportPointList());
		e.setVtaTable(ent.getVtaTable());
		e.setZsbMap(ent.getZsbMap());
		
		//Lock entity
		e.setLockableId(ent.getLockableId());
		e.setSessionId(ent.getSessionId());
		
		return e;
	}
	
	
	public static List<ConnectionElementServerImpl> transformDTOList(List<ConnectionElementDTO> ent_l){
		List<ConnectionElementServerImpl> e_l=new LinkedList<ConnectionElementServerImpl>();
		
		for(ConnectionElementDTO ent:ent_l){
			e_l.add(transform(ent));
		}
		
		return e_l;
	}
	
	
	
	

}
