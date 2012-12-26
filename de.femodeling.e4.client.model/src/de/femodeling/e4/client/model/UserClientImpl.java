package de.femodeling.e4.client.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class UserClientImpl extends UserClient {

	/** The serial version UID. */
	static final long serialVersionUID = 1L;

	//private static Logger logger = Logger.getLogger(UserClientImpl.class);

	protected Object parent;

	public UserClientImpl() {
		this.groups = new HashSet<String>();
		this.roles = new HashSet<String>();
		forename = id = location = password = phonenumber = surname = "";

	}

	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}

	@Override
	public void setForename(String forename) {
		propertyChangeSupport.firePropertyChange("forename",
				this.getForename(), forename);
		fireUserChanged();
		super.setForename(forename);
	}

	@Override
	public void setSurname(String surname) {
		propertyChangeSupport.firePropertyChange("surname", this.getSurname(),
				surname);
		fireUserChanged();
		super.setSurname(surname);
	}

	@Override
	public void setPassword(String password) {
		propertyChangeSupport.firePropertyChange("password",
				this.getPassword(), password);
		fireUserChanged();
		super.setPassword(password);
	}

	@Override
	public void setPhonenumber(String phonenumber) {
		propertyChangeSupport.firePropertyChange("phonenumber",
				this.getPhonenumber(), phonenumber);
		fireUserChanged();
		super.setPhonenumber(phonenumber);
	}

	@Override
	public void setLocation(String location) {
		propertyChangeSupport.firePropertyChange("location",
				this.getLocation(), location);
		fireUserChanged();
		super.setLocation(location);
	}

	@Override
	public void setId(String id) {
		propertyChangeSupport.firePropertyChange("id", this.getId(), id);
		fireUserChanged();
		super.setId(id);
	}

	@Override
	public void setGroups(HashSet<String> groups) {
		propertyChangeSupport.firePropertyChange("groups", this.getGroups(),
				groups);
		fireUserChanged();
		super.setGroups(groups);
	}

	@Override
	public void setRoles(HashSet<String> roles) {
		propertyChangeSupport.firePropertyChange("roles", this.getRoles(),
				roles);
		fireUserChanged();
		super.setRoles(roles);
	}

	/***********************************
	 * * ROLES * *
	 ***********************************/

	public void addRole(String role) {
		this.getRoles().add(role);
	}

	public void removeRole(String role) {
		this.getRoles().remove(role);
	}

	public boolean hasRole(String role) {
		return this.getRoles().contains(role);
	}

	/***********************************
	 * * COPY * *
	 ***********************************/
	public UserClientImpl createCopy() {
		UserClientImpl user = new UserClientImpl();

		user.setForename(this.getForename());
		user.setGroups(this.getGroups());
		user.setId(this.getId());
		user.setLocation(this.getLocation());
		user.setPassword(this.getPassword());
		user.setPhonenumber(this.getPhonenumber());
		user.setRoles(this.getRoles());
		user.setSurname(this.getSurname());

		return user;
	}

	public void copyData(UserClientImpl ent) {
		this.setForename(ent.getForename());
		this.setGroups(ent.getGroups());
		this.setId(ent.getId());
		this.setLocation(ent.getLocation());
		this.setPassword(ent.getPassword());
		this.setPhonenumber(ent.getPhonenumber());
		this.setRoles(ent.getRoles());
		this.setSurname(ent.getSurname());
	}

	/***********************************
	 * * LISTENER * *
	 ***********************************/

	private List<UserClientListenerIF> listeners;

	@Override
	protected void fireLockableEntityChanged() {
		fireUserChanged();
	}

	public void addUserListener(UserClientListenerIF listener) {

		if (listeners == null)
			listeners = new LinkedList<UserClientListenerIF>();
		listeners.add(listener);

	}

	public void removeUserListener(UserClientListenerIF listener) {

		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty())
				listeners = null;
		}
	}

	protected void fireUserChanged() {

		// logger.warn("No Parent Found: "+this.getName());
		if (listeners == null)
			return;
		Object[] rls = listeners.toArray();
		for (int i = 0; i < rls.length; i++) {
			UserClientListenerIF listener = (UserClientListenerIF) rls[i];
			listener.userChanged(this);
		}

	}

}
