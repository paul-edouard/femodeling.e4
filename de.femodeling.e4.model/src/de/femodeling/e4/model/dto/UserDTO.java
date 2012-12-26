package de.femodeling.e4.model.dto;

import java.io.Serializable;
import java.util.HashSet;

public abstract class UserDTO implements Serializable{
	
	 /** The serial version UID. */
   private static final long serialVersionUID = 1L;
   
   protected String forename;
   protected String surname;
   protected String password;
   protected String phonenumber;
	protected String location;
	protected String id;
	protected HashSet<String> groups;
	protected HashSet<String> roles;
	
	
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HashSet<String> getGroups() {
		return groups;
	}
	public void setGroups(HashSet<String> groups) {
		this.groups = groups;
	}
	public HashSet<String> getRoles() {
		return roles;
	}
	public void setRoles(HashSet<String> roles) {
		this.roles = roles;
	}
	
	
	
}