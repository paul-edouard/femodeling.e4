package de.femodeling.e4.client.service.core;

import java.util.List;

import de.femodeling.e4.client.model.ConnectionElementClientImpl;
import de.femodeling.e4.client.model.ProjectClientImpl;


public interface ConnectionClientServiceIF {

	public List<ConnectionElementClientImpl> addConnections(List<ConnectionElementClientImpl> e_l,ProjectClientImpl pro);
	
	public List<ConnectionElementClientImpl> getConnections(ProjectClientImpl pro);
	
}
