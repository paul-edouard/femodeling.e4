package de.femodeling.e4.client.service;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.server.service.RemoteService;
import de.femodeling.e4.ui.dataprovider.registery.IRegistery;

public interface IProjectProvider {

	public abstract void init(IRegistery registery, IClientService service,
			RemoteService remoteService);
	
	public abstract ProjectClientImpl getRoot();

	public abstract ProjectClientImpl putData(ProjectClientImpl p);

	public abstract ProjectClientImpl updateData(ProjectClientImpl p);

	public abstract boolean removeData(ProjectClientImpl p);

	public abstract int getNumberOfParts(ProjectClientImpl p, Part.Type type);
	
	public abstract boolean rename(ProjectClientImpl p, String newProName );

	public abstract boolean hasAssembly(ProjectClientImpl p, Assembly.Type type);

	public abstract boolean hasConnections(ProjectClientImpl p);

}