package de.femodeling.e4.model.dto;

import de.femodeling.e4.model.core.User;

public abstract class UserDTO extends User{
	
	 /** The serial version UID. */
   private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "UserDTO [getForename()=" + getForename() + ", getGroups()="
				+ getGroups() + ", getId()=" + getId() + ", getLocation()="
				+ getLocation() + ", getPassword()=" + getPassword()
				+ ", getPhonenumber()=" + getPhonenumber() + ", getRoles()="
				+ getRoles() + ", getSurname()=" + getSurname()
				+ ", getLockableId()=" + getLockableId() + ", islocked()="
				+ islocked() + ", getSessionId()=" + getSessionId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
   
  
	
	
	
}