package de.femodeling.e4.client.model.broker;

public interface IBrokerEvents {
	
	/**********************
	 *    ONLINE STATE    *
	 **********************/
	public static final String ONLINE_STATE="ONLINE_STATE";
	
	
	
	/**********************
	 *  SERVER MESSAGE    *
	 **********************/
	public static final String SERVER_MESSAGE="SERVER_MESSAGE_";
	
	public static final String ADD_DATA=SERVER_MESSAGE+"ADD_DATA";
	public static final String UPDATE_DATA=SERVER_MESSAGE+"UPDATE_DATA";
	public static final String REMOVE_DATA=SERVER_MESSAGE+"REMOVE_DATA";
	
	
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
    
    
    /**********************
	 *     PROJECT        *
	 **********************/
    public static final String PROJECT="PROJECT_BROKER_SERVICE_";
    public static final String PROJECT_ADD=PROJECT+"ADD";
    public static final String PROJECT_REMOVE=PROJECT+"REMOVE";
    public static final String PROJECT_UPDATE=PROJECT+"UPDATE";

}
