package de.femodeling.e4.client.service.internal.dataprovider;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.service.internal.transform.ProjectTransformService;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.server.service.RemoteService;
import de.femodeling.e4.ui.dataprovider.IWritableDataProvider;
import de.femodeling.e4.ui.dataprovider.internal.cache.DataProviderCacheAdapter;
import de.femodeling.e4.ui.dataprovider.key.ICollectionKey;
import de.femodeling.e4.ui.dataprovider.key.IKey;
import de.femodeling.e4.ui.dataprovider.key.UUIDKey;
import de.femodeling.e4.ui.dataprovider.registery.IRegistery;



public class ProjectProvider implements IWritableDataProvider, IProjectProvider {
	
	/** This class' Logger instance. */
	private static Logger logger = Logger
			.getLogger(ProjectProvider.class);

	
	/** The group this provider belongs to. */
	public static final String DATA_PROVIDER_GROUP = "project";

	/** The type of this provider. */
	public static final String DATA_PROVIDER_TYPE = "project.entry";
	
	/** The type of this provider. */
	public static final String DATA_ROOT = "project.root";
	
	
	private ProjectClientImpl rootProject;
	
	
	private static IRegistery registery;
	
	private static IClientService service;
	
	private static RemoteService remoteService;
	
	
	
	public ProjectProvider() {
		super();
		logger.info("Project Provider Service started");
	}


	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#init(de.femodeling.e4.ui.dataprovider.registery.IRegistery, de.femodeling.e4.client.service.IClientService, de.femodeling.e4.server.service.RemoteService)
	 */
	@Override
	public void init(IRegistery registery,IClientService service,RemoteService remoteService){
		this.registery=registery;
		this.service=service;
		this.remoteService=remoteService;
		logger.info("Project Provider initialized");
	}
	
	
	public  ProjectClientImpl getRoot() {
		
		if(rootProject==null){
		
		DataProviderCacheAdapter dataProvider = (DataProviderCacheAdapter) registery
				.lookupDataProvider(ProjectProvider.DATA_PROVIDER_TYPE);
		
		ProjectClientImpl p=(ProjectClientImpl) dataProvider.getData(new UUIDKey(ProjectProvider.DATA_ROOT));
		rootProject=p;
		
		//logger.info("User Groups:"+remoteService.getUserService().getCurrentUser().getId());
		
		fillProjectCache(dataProvider,p);
		
		}
		
		return rootProject;
	}
	
	private static void fillProjectCache(DataProviderCacheAdapter dataProvider,ProjectClientImpl p){
		
		for(ProjectClientImpl c:p.getChilds()){
				UUIDKey key=new UUIDKey(c.getLockableId());
				dataProvider.flushCacheForUpdate(key, c);
				fillProjectCache(dataProvider,c);
		}
		
	}
	
	
	/**
	 * Static 
	 * @return
	 */
	
	private DataProviderCacheAdapter getDefault(){
		
		return (DataProviderCacheAdapter) registery.lookupDataProvider(ProjectProvider.DATA_PROVIDER_TYPE);
	}
	
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#putData(de.femodeling.e4.client.model.ProjectClientImpl)
	 */
	@Override
	public ProjectClientImpl putData(ProjectClientImpl p){
		
		Object proj=getDefault().putData(new UUIDKey(p.getLockableId()), p);
		return (ProjectClientImpl) proj;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#updateData(de.femodeling.e4.client.model.ProjectClientImpl)
	 */
	@Override
	public ProjectClientImpl updateData(ProjectClientImpl p){
		Object proj=getDefault().updateData(new UUIDKey(p.getLockableId()), p);
		return (ProjectClientImpl) proj;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#removeData(de.femodeling.e4.client.model.ProjectClientImpl)
	 */
	@Override
	public boolean removeData(ProjectClientImpl p){
		return getDefault().removeData(new UUIDKey(p.getLockableId()));
	}
	
	public ProjectClientImpl getData(String projectId){
		Object proj=getDefault().getData(new UUIDKey(projectId));
		return (ProjectClientImpl) proj;
	}
	
	
	public boolean rename(ProjectClientImpl pro, String newProName ){
		return service.getProjectClientService().renameProject(pro, newProName);
	}
	
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#getNumberOfParts(de.femodeling.e4.client.model.ProjectClientImpl, de.femodeling.e4.model.core.part.Part.Type)
	 */
	@Override
	public int getNumberOfParts(ProjectClientImpl p,Part.Type type){
		return service.getProjectClientService().getNumberOfParts(p, type);
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#hasAssembly(de.femodeling.e4.client.model.ProjectClientImpl, de.femodeling.e4.model.core.assembly.Assembly.Type)
	 */
	@Override
	public boolean hasAssembly(ProjectClientImpl p,Assembly.Type type){
		return service.getProjectClientService().hasAssembly(p, type);
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.dataprovider.IProjectProvider#hasConnections(de.femodeling.e4.client.model.ProjectClientImpl)
	 */
	@Override
	public boolean hasConnections(ProjectClientImpl p){
		return service.getProjectClientService().hasConnections(p);
	}
	

	@Override
	public String getType() {
		return ProjectProvider.DATA_PROVIDER_TYPE;
	}

	@Override
	public String getProviderGroup() {
		return ProjectProvider.DATA_PROVIDER_GROUP;
	}

	@Override
	/*
	 * get the data from the server
	 * 
	 * (non-Javadoc)
	 * @see de.porsche.femodeling.ui.dataprovider.IDataProvider#getData(de.porsche.femodeling.ui.dataprovider.key.IKey)
	 */
	public Object getData(IKey key) {
		assert key instanceof UUIDKey;
		UUIDKey uuidKey = (UUIDKey) key;
		
		//get the root project from the server
		logger.debug("Get the Data from the Server: "+uuidKey.getUUIDKey());
		ProjectDTO p=remoteService.getProjectService().getRootProject();
		
		
		//Reduce the projects in function of the User group
		if(remoteService.getUserService().getCurrentUser()!=null){
			HashSet<String> userGroups=remoteService.getUserService().getCurrentUser().getGroups();
			reduceChildFromProject(p,userGroups);
		}
		
		HashMap<String, String> lockedEntMap=service.getLockClientService().getAllLockedEntities();
		
		ProjectClientImpl p_c=ProjectTransformService.transformClient(p, true);
		setProjectLockedStatus(p_c,lockedEntMap);
		
		if(uuidKey.getUUIDKey().equals(ProjectProvider.DATA_ROOT)){
			return p_c;
		}
		else{
			
			return seachProjectWithId(p_c,uuidKey.getUUIDKey());
		}
		
	}
	
	private void  reduceChildFromProject(ProjectDTO p,HashSet<String> userGroups){
		
		//Get the project to delete
		HashSet<ProjectDTO> toDelete=new HashSet<ProjectDTO>();
		
		for(ProjectDTO c:p.getChilds()){
			if(!userGroups.contains(c.getGroup())){
				toDelete.add(c);
			}
		}
		
		
		p.getChilds().removeAll(toDelete);
		
		for(ProjectDTO c:p.getChilds()){
			reduceChildFromProject(c,userGroups);
		}
		
		
		
	}
	
	private void setProjectLockedStatus(ProjectClientImpl p,HashMap<String, String> lockedEntMap){
		
		if(lockedEntMap.containsKey(p.getLockableId()))
			p.setSessionId(lockedEntMap.get(p.getLockableId()));
		
		for(ProjectClientImpl c:p.getChilds()){
			setProjectLockedStatus(c,lockedEntMap);
		}
		
	}
	
	private ProjectClientImpl seachProjectWithId(ProjectClientImpl p,String uuid){
		for(ProjectClientImpl c:p.getChilds()){
			if(c.getLockableId().equals(uuid))return c;
			
			ProjectClientImpl r=seachProjectWithId(c,uuid);
			if(r!=null)return r;
		}		
		return null;
	}
	

	@Override
	public Collection getDataCollection(ICollectionKey collectionKey) {
		return null;
	}

	@Override
	public IKey getKey(Object data) {
		if(data instanceof ProjectClientImpl){
			return new UUIDKey(((ProjectClientImpl) data).getLockableId());
		}
		return null;
	}
	
	
	
	
	
	@Override
	public Object putData(IKey key, Object data){
		
		assert data instanceof ProjectClientImpl;
		ProjectClientImpl p=(ProjectClientImpl) data;
		
		//Put Project
		ProjectClientImpl savedPro=service.getProjectClientService().addProject(p.getParent(),p);
		
		if(savedPro!=null){
			ProjectTransformService.copyData(savedPro, p);
			//logger.debug("Project to add: "+p.toString());
			return p;
		}
		
		else return null;
		
	}

	@Override
	public boolean removeData(IKey key){
		Object data=getData(key);
		assert data instanceof ProjectClientImpl;
		ProjectClientImpl pro=(ProjectClientImpl) data;
		
		if(!service.getProjectClientService().removeProject(pro)){
			return false;
		}
		else{
			pro.getParent().removeChild(pro);return true;
		}
	}


	@Override
	public Object updateData(IKey key, Object data) {
		assert data instanceof ProjectClientImpl;
		ProjectClientImpl input=(ProjectClientImpl) data;
		logger.debug("Project to Update: "+input.toString());
		if(!service.getProjectClientService().updateProject(input)){
			logger.debug("Problem by updating the data on the server!");
			return null;
		}
		return input;
		
		
	}
	
	

}
