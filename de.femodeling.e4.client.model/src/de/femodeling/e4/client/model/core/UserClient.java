package de.femodeling.e4.client.model.core;

import de.femodeling.e4.model.core.User;


public abstract class UserClient extends User {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;
	
	protected boolean online;
	
	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}

}
