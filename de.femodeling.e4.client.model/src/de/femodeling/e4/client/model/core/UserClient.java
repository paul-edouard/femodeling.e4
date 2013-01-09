package de.femodeling.e4.client.model.core;

import de.femodeling.e4.model.core.User;
import de.femodeling.e4.model.core.parameter.Parameter;


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
		Parameter par_type=this.getParameter().getChild(TYPE);
		if(par_type!=null && par_type.getType()==Parameter.Type.STRING){
			type=(String)par_type.getValue();
		}
		return type;
	}

	public void setType(String type) {
		Parameter par_type=this.getParameter().getChild(TYPE);
		if(par_type==null){
			par_type=new Parameter(TYPE,type);
			this.getParameter().addChild(par_type);
		}
		par_type.setValue(type);
		this.type = type;
	}
	
	

}
