package de.femodeling.e4.client.model;

import java.util.HashSet;


public abstract class UserClient extends LockableEntityClientImpl {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	protected String forename;
	protected HashSet<String> groups;
	protected String id;
	protected String location;
	protected boolean online;
	protected String password;
	protected String phonenumber;
	protected HashSet<String> roles;
	protected String surname;

	public String getForename() {
		return forename;
	}

	public HashSet<String> getGroups() {
		return groups;
	}

	public String getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public String getPassword() {
		return password;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public HashSet<String> getRoles() {
		if (roles == null)
			roles = new HashSet<String>();
		return roles;
	}

	public String getSurname() {
		return surname;
	}

	public boolean isOnline() {
		return online;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setGroups(HashSet<String> groups) {
		this.groups = groups;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setRoles(HashSet<String> roles) {
		this.roles = roles;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "UserClient [forename=" + forename + ", groups=" + groups
				+ ", id=" + id + ", location=" + location + ", online="
				+ online + ", password=" + password + ", phonenumber="
				+ phonenumber + ", roles=" + roles + ", surname=" + surname
				+ "]";
	}

}
