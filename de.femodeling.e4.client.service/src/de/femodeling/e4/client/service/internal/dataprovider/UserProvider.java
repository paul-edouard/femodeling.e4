package de.femodeling.e4.client.service.internal.dataprovider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.service.internal.transform.UserTransformService;
import de.femodeling.e4.ui.dataprovider.IWritableDataProvider;
import de.femodeling.e4.ui.dataprovider.internal.cache.DataProviderCacheAdapter;
import de.femodeling.e4.ui.dataprovider.key.ICollectionKey;
import de.femodeling.e4.ui.dataprovider.key.IKey;
import de.femodeling.e4.ui.dataprovider.key.UUIDCollectionKey;
import de.femodeling.e4.ui.dataprovider.key.UUIDKey;
import de.femodeling.e4.ui.dataprovider.registery.IRegistery;

public class UserProvider implements IWritableDataProvider, IUserProvider {
	
	/** This class' Logger instance. */
	private static Logger logger = Logger
			.getLogger(ProjectProvider.class);

	
	/** The group this provider belongs to. */
	public static final String DATA_PROVIDER_GROUP = "user";

	/** The type of this provider. */
	public static final String DATA_PROVIDER_TYPE = "user.entry";
	
	/** The type of this provider. */
	public static final String DATA_ROOT = "user.root";
	
	
	private static IRegistery registery;
	
	private static IClientService service;
	
	//private static RemoteService remoteService;
	
	

	public UserProvider() {
		super();
		logger.info("User Provider Service started");
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#init(de.femodeling.e4.ui.dataprovider.registery.IRegistery, de.femodeling.e4.client.service.IClientService)
	 */
	@Override
	public void init(IRegistery registery,IClientService service){
		this.registery=registery;
		this.service=service;
		logger.info("User Provider initialized");
	}
	
	private DataProviderCacheAdapter getDefault(){	
		return (DataProviderCacheAdapter) registery.lookupDataProvider(ProjectProvider.DATA_PROVIDER_TYPE);
	}
	
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#putData(de.femodeling.e4.client.model.UserClientImpl)
	 */
	@Override
	public UserClientImpl putData(UserClientImpl p){
		Object user=getDefault().putData(new UUIDKey(p.getId()), p);
		return (UserClientImpl) user;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#updateData(de.femodeling.e4.client.model.UserClientImpl)
	 */
	@Override
	public UserClientImpl updateData(UserClientImpl p){
		Object user=getDefault().updateData(new UUIDKey(p.getId()), p);
		return (UserClientImpl) user;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#removeData(de.femodeling.e4.client.model.UserClientImpl)
	 */
	@Override
	public boolean removeData(UserClientImpl p){
		return getDefault().removeData(new UUIDKey(p.getId()));
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#getData(java.lang.String)
	 */
	@Override
	public UserClientImpl getData(String userId){
		Object user=getDefault().getData(new UUIDKey(userId));
		return (UserClientImpl) user;
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#getDataCollection(java.lang.String)
	 */
	@Override
	public Collection getDataCollection(String userGroup){
		return getDefault().getDataCollection(new UUIDCollectionKey(userGroup));
	}
	
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#getCurrentUser()
	 */
	@Override
	public UserClientImpl getCurrentUser(){
		return service.getUserClientService().getCurrentUser();
	}
	
	/* (non-Javadoc)
	 * @see de.femodeling.e4.client.service.internal.dataprovider.IUserProvider#getOnlineUserIds()
	 */
	@Override
	public Set<String> getOnlineUserIds(){
		return service.getUserClientService().getOnlineUserIds();
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
	public Object getData(IKey key) {
		assert key instanceof UUIDKey;
		UUIDKey uuidKey = (UUIDKey) key;
		logger.debug("Get the Data from the Server: "+uuidKey.getUUIDKey());
		
		Set<UserClientImpl> userSet=service.getUserClientService().getAllUsers();
		for(UserClientImpl u:userSet){
			if(u.getId().equals(uuidKey.getUUIDKey())){
				return u;
			}
		}
		
		return null;
	}

	@Override
	public Collection getDataCollection(ICollectionKey collectionKey) {
		
		Set<UserClientImpl> returnCollection=new HashSet<UserClientImpl>();
		
		//Get all users
		logger.debug("Get all users from the Server: "+collectionKey);
		Set<UserClientImpl> userSet=service.getUserClientService().getAllUsers();
		
		
		if(collectionKey==ICollectionKey.ALL){
			returnCollection=userSet;
		}
		else {
			for(UserClientImpl u:userSet){
				if(u.getGroups().contains(collectionKey.toString())){
					returnCollection.add(u);
				}
			}
		}
		
		return returnCollection;
	}

	@Override
	public IKey getKey(Object data) {
		if(data instanceof UserClientImpl){
			return new UUIDKey(((UserClientImpl) data).getId());
		}
		return null;
	}

	@Override
	public Object putData(IKey key, Object data) {
		
		assert data instanceof UserClientImpl;
		UserClientImpl user=(UserClientImpl) data;
		
		UserClientImpl savedUser=service.getUserClientService().saveUser(user);
		if(savedUser!=null){
			UserTransformService.copyData(savedUser, user);
			return user;
		}
		
		else return null;
	}

	@Override
	public boolean removeData(IKey key) {
		Object data=getData(key);
		assert data instanceof UserClientImpl;
		UserClientImpl user=(UserClientImpl) data;
		
		if(!service.getUserClientService().deleteUser(user)){
			return false;
		}else{
			//TODO delete the key from the different collection key
			return true;
		}

	}

	@Override
	public Object updateData(IKey key, Object data) {
		
		assert data instanceof UserClientImpl;
		UserClientImpl user=(UserClientImpl) data;
		logger.debug("User to Update: "+user.toString());
		
		UserClientImpl savedUser=service.getUserClientService().saveUser(user);
		if(savedUser!=null){
			UserTransformService.copyData(savedUser, user);
			return user;
		}
		
		else return null;
		
		
	}

}
