package de.femodeling.e4.client.service.internal.transform;

import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.ConnectionElementClientImpl;
import de.femodeling.e4.model.dto.ConnectionElementDTO;
import de.femodeling.e4.model.dto.ConnectionElementDTOImpl;


public class VtaElementTransformService {
	
	
	
	
	/***********************************
	 *                                 *
	 *		       CLIENT              *
	 *                                 *
	 ***********************************/
	public static ConnectionElementDTO transformClient(ConnectionElementClientImpl ent){
		
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
	
	public static List<ConnectionElementDTO> transformClientList(List<ConnectionElementClientImpl> ent_l){
		List<ConnectionElementDTO> e_l=new LinkedList<ConnectionElementDTO>();
		
		for(ConnectionElementClientImpl ent:ent_l){
			e_l.add(transformClient(ent));
		}
		
		return e_l;
	}
	
	
	
	
	public static ConnectionElementClientImpl transformClient(ConnectionElementDTO ent){
		
		ConnectionElementClientImpl e=new ConnectionElementClientImpl();
		
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
	
	public static List<ConnectionElementClientImpl> transformClientDTOList(List<ConnectionElementDTO> ent_l){
		List<ConnectionElementClientImpl> e_l=new LinkedList<ConnectionElementClientImpl>();
		
		for(ConnectionElementDTO ent:ent_l){
			e_l.add(transformClient(ent));
		}
		
		return e_l;
	}
	

}
