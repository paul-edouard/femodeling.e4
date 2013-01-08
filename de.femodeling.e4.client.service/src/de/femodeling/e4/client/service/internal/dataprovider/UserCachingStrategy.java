package de.femodeling.e4.client.service.internal.dataprovider;

import org.apache.log4j.Logger;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.service.internal.transform.UserTransformService;
import de.femodeling.e4.ui.dataprovider.cache.SimpleCachingStrategy;
import de.femodeling.e4.ui.dataprovider.key.IKey;

public class UserCachingStrategy extends SimpleCachingStrategy {
	
	private static Logger logger = Logger.getLogger(UserCachingStrategy.class);
	
	
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
	

}
