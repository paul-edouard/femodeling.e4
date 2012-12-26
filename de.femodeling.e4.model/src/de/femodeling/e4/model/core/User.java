package de.femodeling.e4.model.core;

import java.io.Serializable;
import java.util.HashSet;

public abstract class User implements Serializable {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	public static String ADMIN = "Admin";
	public static String DATA_MANAGER = "Data Manager";
	public static String CUSTOMER = "Customer";
	public static String SUPPLIER = "Supplier";

	protected String forename;
	protected HashSet<String> groups;
	protected String id;
	protected String location;
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
		return roles;
	}

	public String getSurname() {
		return surname;
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

}
