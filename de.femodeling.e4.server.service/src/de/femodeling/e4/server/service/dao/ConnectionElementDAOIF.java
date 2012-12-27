package de.femodeling.e4.server.service.dao;

import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.internal.model.ConnectionElementServerImpl;
import de.femodeling.e4.internal.model.ProjectServerImpl;



public interface ConnectionElementDAOIF {
	
	
	public boolean saveVtaElement(ConnectionElementServerImpl e, ProjectServerImpl proj);
	
	public List<ConnectionElementServerImpl> saveVtaElements(List<ConnectionElementServerImpl> e_l, ProjectServerImpl proj);
	
	public LinkedList<ConnectionElementServerImpl> getConnectionElements(ProjectServerImpl proj);
	
}
