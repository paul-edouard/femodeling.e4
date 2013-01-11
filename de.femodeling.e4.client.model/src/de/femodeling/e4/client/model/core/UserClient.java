package de.femodeling.e4.client.model.core;

import de.femodeling.e4.model.core.User;


public abstract class UserClient extends User {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;
	
	protected boolean online=false;

	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public String getDefaultGroup(){
		for(String g:this.getGroups()){
			if(g!=null)return g;
		}
		
		return "";
	}
	
	/**
	 * type of the user
	 * three types are available "all","user","group"
	 * 
	 */
	private String type=TYPE_USER;
	public static final String TYPE="user.type";

	public static final String TYPE_ALL="all";
	public static final String TYPE_USER="user";
	public static final String TYPE_GROUP="group";

	
	public String getType() {
		String value=this.getStringParam(TYPE);
		if(!value.isEmpty())type=value;
		return type;
	}

	public void setType(String type) {
		setParam(TYPE, type);
		this.type = type;
	}
	
	

}
