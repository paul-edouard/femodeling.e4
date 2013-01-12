package de.femodeling.e4.client.service.internal.dataprovider;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.model.core.UserClient;
import de.femodeling.e4.client.service.internal.transform.UserTransformService;
import de.femodeling.e4.model.dto.MessageDTO;
import de.femodeling.e4.model.dto.UserDTO;
import de.femodeling.e4.ui.dataprovider.cache.ICacheEntryInformation;
import de.femodeling.e4.ui.dataprovider.cache.SimpleCachingStrategy;
import de.femodeling.e4.ui.dataprovider.internal.cache.CacheEntryInformation;
import de.femodeling.e4.ui.dataprovider.key.IKey;
import de.femodeling.e4.ui.dataprovider.key.UUIDCollectionKey;
import de.femodeling.e4.ui.dataprovider.key.UUIDKey;

public class UserCachingStrategy extends SimpleCachingStrategy {
	
	private static Logger logger = Logger.getLogger(UserCachingStrategy.class);
	
	@Inject
	IEventBroker broker;
	
	@Override
	public Object updateCachedData(IKey key, Object data) {
		assert data instanceof UserClientImpl;
		UserClientImpl input=(UserClientImpl) data;
		
		logger.debug("Try to get the data from the cach: "+input.getLockableId()+" Class: "+this.getClass());
		UserClientImpl target=(UserClientImpl)this.getCachedData(key);
		
		//Copy the new Data
		UserTransformService.copyData(input, target);
		super.updateCachedData(key, data);
		
		return target;
	}


	@Override
	public void registerCachedData(IKey key, Object data,
			ICacheEntryInformation cacheEntryInformation) {
		
		if(data instanceof UserClientImpl){
			UserClientImpl input=(UserClientImpl) data;
		
			//logger.debug("Update the collection: "+input.getType());
			Collection col=(Collection) this.getCachedData(new UUIDCollectionKey(input.getType()));
			if(col!=null && !col.contains(key))
				col.add(key);
		}
		
		
		// TODO Auto-generated method stub
		super.registerCachedData(key, data, cacheEntryInformation);
	}
	
	
	@Inject
	private void update(@Optional  @UIEventTopic(IBrokerEvents.UPDATE_DATA)MessageDTO mes){
		if(mes==null)return;
		//System.out.println("---> Update recieved!!! Thanks?");
		
		if(mes.getSendingEntity() instanceof UserDTO){
			UserDTO u_dto=(UserDTO) mes.getSendingEntity();
			UserClientImpl u_client=UserTransformService.transformClient(u_dto);
			
			UserClientImpl target=(UserClientImpl)getCachedData(new UUIDKey(u_client.getId()));
			target.copyData(u_client);
			
			UUIDKey uuidKey=new UUIDKey(target.getId());
			registerCachedData(uuidKey,target,new CacheEntryInformation());
			
			//send the message
			if(target.getType().equals(UserClient.TYPE_USER))
				broker.post(IBrokerEvents.USER_UPDATE, target);
			else
				broker.post(IBrokerEvents.USER_GROUP_UPDATE, target);
		}
		
	}
	
	@Inject
	private void add(@Optional  @UIEventTopic(IBrokerEvents.ADD_DATA)MessageDTO mes){
		if(mes==null)return;
		System.out.println("---> add recieved!!! Thanks?");
		
		if(mes.getSendingEntity() instanceof UserDTO){
			UserDTO u_dto=(UserDTO) mes.getSendingEntity();
			UserClientImpl u_client=UserTransformService.transformClient(u_dto);
			
			UUIDKey uuidKey=new UUIDKey(u_client.getId());
			registerCachedData(uuidKey,u_client,new CacheEntryInformation());
			
			//send the message
			if(u_client.getType().equals(UserClient.TYPE_USER))
				broker.post(IBrokerEvents.USER_ADD, u_client);
			else
				broker.post(IBrokerEvents.USER_GROUP_ADD, u_client);
		}
		
	}
	
	@Inject
	private void remove(@Optional  @UIEventTopic(IBrokerEvents.REMOVE_DATA)MessageDTO mes){
		if(mes==null)return;
		System.out.println("---> Remove recieved!!! Thanks?");
		
		if(mes.getSendingEntity() instanceof UserDTO){
			UserDTO u_dto=(UserDTO) mes.getSendingEntity();
			UserClientImpl u_client=UserTransformService.transformClient(u_dto);
			
			UUIDKey uuidKey=new UUIDKey(u_client.getId());
			evictCachedData(uuidKey);
			
			//send the message
			if(u_client.getType().equals(UserClient.TYPE_USER))
				broker.post(IBrokerEvents.USER_REMOVE, u_client);
			else
				broker.post(IBrokerEvents.USER_GROUP_REMOVE, u_client);
		}
	}
	
	

}
