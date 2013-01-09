package de.femodeling.e4.client.model.broker;

public interface IBrokerEvents {
	
	/**********************
	 *    ONLINE STATE    *
	 **********************/
	public static final String ONLINE_STATE="ONLINE_STATE";
	
	
	/**********************
	 *        USER        *
	 **********************/
	public static final String USER="USER_BROKER_SERVICE_";
    public static final String USER_ADD=USER+"ADD";
    public static final String USER_REMOVE=USER+"REMOVE";
    public static final String USER_UPDATE=USER+"UPDATE";
    
    public static final String USER_GROUP="USER_GROUP_BROKER_SERVICE";
    public static final String USER_GROUP_ADD=USER_GROUP+"ADD";
    public static final String USER_GROUP_REMOVE=USER_GROUP+"REMOVE";
    public static final String USER_GROUP_UPDATE=USER_GROUP+"UPDATE";
	

}
