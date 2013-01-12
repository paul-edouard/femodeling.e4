package de.femodeling.e4.client.service.internal.dataprovider;



import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.service.internal.transform.ProjectTransformService;
import de.femodeling.e4.model.dto.MessageDTO;
import de.femodeling.e4.model.dto.ProjectDTO;
import de.femodeling.e4.ui.dataprovider.cache.SimpleCachingStrategy;
import de.femodeling.e4.ui.dataprovider.internal.cache.CacheEntryInformation;
import de.femodeling.e4.ui.dataprovider.key.IKey;
import de.femodeling.e4.ui.dataprovider.key.UUIDKey;




public class ProjectCachingStrategy extends
		SimpleCachingStrategy {
	
	private static Logger logger = Logger.getLogger(ProjectCachingStrategy.class);
	
	@Inject
	IEventBroker broker;
	
	public ProjectCachingStrategy(){
		super();
		
	//	initMessageListener();
	}
	
	
	/*
	
	private void initMessageListener(){
		
		//logger.info("Initalization of Message listener");
		
		MessageSession.INSTANCE.getMessagesJob().addJobChangeListener(new JobChangeAdapter(){
			public void done(final IJobChangeEvent event){
				
				MessagesJob job=(MessagesJob) event.getJob();
				if(job.getNewMessages()!=null && !job.getNewMessages().isEmpty()){
					
					//logger.info("***********Message Recieved!!! "+job.getNewMessages().size());
					
					for(MessageDTO ent: job.getNewMessages()){
						if(ent.getSendingEntity() instanceof ProjectDTO){
							
							//logger.info("***********Project Message Received!!!");
							ProjectDTO pro=(ProjectDTO) ent.getSendingEntity();
							
							if(ent.getSendingType()==Type.ADD){
								logger.debug("PROJECT ADD");
								
								ProjectClientImpl parent=(ProjectClientImpl)getCachedData(new UUIDKey(ent.getParentId()));
								ProjectClientImpl child=ProjectTransformService.transformClient(pro, false);
				
								if(parent!=null){
									//logger.info("Child add to parent: "+parent.toString());
									parent.addChild(child);
									UUIDKey uuidKey=new UUIDKey(child.getLockableId());
									registerCachedData(uuidKey,child,new CacheEntryInformation());
									
								}
							}
							
							else if(ent.getSendingType()==Type.REMOVE){
								logger.debug("PROJECT REMOVE");
								
								ProjectClientImpl child=(ProjectClientImpl)getCachedData(new UUIDKey(pro.getLockableId()));
								ProjectClientImpl parent=(ProjectClientImpl)getCachedData(new UUIDKey(child.getParent().getLockableId()));
								
								if(parent!=null && child !=null){
									parent.removeChild(child);
									ProjectManagableCachingStrategy.this.evictCachedData(new UUIDKey(child.getLockableId()));
								}
							}
							
							else if(ent.getSendingType()==Type.UPDATE){
								logger.debug("PROJECT UPDATE!!");
								
								ProjectClientImpl target=(ProjectClientImpl)getCachedData(new UUIDKey(pro.getLockableId()));
								ProjectTransformService.copyData(ProjectTransformService.transformClient(pro, false), target);
								
								UUIDKey uuidKey=new UUIDKey(target.getLockableId());
								registerCachedData(uuidKey,target,new CacheEntryInformation());
								
							}
							
						}
						else if(ent.getSendingEntity() instanceof LockableEntityDTO){
							logger.debug("Lockable entity catched!!");
							LockableEntityDTO l_ent=(LockableEntityDTO)ent.getSendingEntity();
							
							ProjectClientImpl target=(ProjectClientImpl)getCachedData(new UUIDKey(l_ent.getLockableId()));
							if(target!=null){
								target.setSessionId(l_ent.getSessionId());
							}
							
						}
						else {
							logger.debug("Unknown message!"+ent.getSendingEntity().getClass());
						}
					}
				}
				
			}
		});
	}
	*/
	

	@Override
	public Object updateCachedData(IKey key, Object data) {
		assert data instanceof ProjectClientImpl;
		ProjectClientImpl input=(ProjectClientImpl) data;
		
		logger.debug("Try to get the data from the cach: "+input.getLockableId()+" Class: "+this.getClass());
		ProjectClientImpl target=(ProjectClientImpl)this.getCachedData(key);
		
		//Copy the new Data
		ProjectTransformService.copyData(input, target);
		super.updateCachedData(key, data);
		
		
		return target;
	}
	
	
	
	@Inject
	private void update(@Optional  @UIEventTopic(IBrokerEvents.UPDATE_DATA)MessageDTO mes){
		if(mes==null)return;
		System.out.println("---> Update recieved!!! Thanks?");
		
		if(mes.getSendingEntity() instanceof ProjectDTO){
			
			ProjectDTO pro=(ProjectDTO) mes.getSendingEntity();
			
			ProjectClientImpl target=(ProjectClientImpl)getCachedData(new UUIDKey(pro.getLockableId()));
			ProjectTransformService.copyData(ProjectTransformService.transformClient(pro, false), target);
			
			UUIDKey uuidKey=new UUIDKey(target.getLockableId());
			registerCachedData(uuidKey,target,new CacheEntryInformation());
			
			//send the message
			broker.post(IBrokerEvents.PROJECT_UPDATE, target.getLockableId());
			
		}
		
	}
	
	@Inject
	private void add(@Optional  @UIEventTopic(IBrokerEvents.ADD_DATA)MessageDTO mes){
		if(mes==null)return;
		System.out.println("---> add recieved!!! Thanks?");
		
		if(mes.getSendingEntity() instanceof ProjectDTO){
			ProjectDTO pro=(ProjectDTO) mes.getSendingEntity();
			
			ProjectClientImpl parent=(ProjectClientImpl)getCachedData(new UUIDKey(mes.getParentId()));
			ProjectClientImpl child=ProjectTransformService.transformClient(pro, false);

			if(parent!=null){
				//logger.info("Child add to parent: "+parent.toString());
				parent.addChild(child);
				UUIDKey uuidKey=new UUIDKey(child.getLockableId());
				registerCachedData(uuidKey,child,new CacheEntryInformation());
				
			}
			
			//send the message
			broker.post(IBrokerEvents.PROJECT_ADD, child);
			
		}
		
	}
	
	@Inject
	private void remove(@Optional  @UIEventTopic(IBrokerEvents.REMOVE_DATA)MessageDTO mes){
		if(mes==null)return;
		System.out.println("---> Remove recieved!!! Thanks?");
		
		if(mes.getSendingEntity() instanceof ProjectDTO){
			ProjectDTO pro=(ProjectDTO) mes.getSendingEntity();
			ProjectClientImpl child=(ProjectClientImpl)getCachedData(new UUIDKey(pro.getLockableId()));
			ProjectClientImpl parent=(ProjectClientImpl)getCachedData(new UUIDKey(child.getParent().getLockableId()));
			
			if(parent!=null && child !=null){
				parent.removeChild(child);
				evictCachedData(new UUIDKey(child.getLockableId()));
			}
			
			
			//send the message
			broker.post(IBrokerEvents.USER_REMOVE, child);
			
		}
	}
	
	
}
