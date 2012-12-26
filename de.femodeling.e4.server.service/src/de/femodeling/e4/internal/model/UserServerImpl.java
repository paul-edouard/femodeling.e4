package de.femodeling.e4.internal.model;

import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.core.User;
import de.femodeling.e4.model.xml.XmlElementIF;



public class UserServerImpl extends User implements XmlElementIF {

	static final long serialVersionUID = 010101;

	public UserServerImpl() {
		setGroups(new HashSet<String>());
		setRoles(new HashSet<String>());
	}

	public void addRole(String roleName) {
		this.roles.add(roleName);
	}

	public void removeRole(String roleName) {
		this.roles.remove(roleName);
	}

	public void addGroup(String groupName) {
		this.groups.add(groupName);
	}

	public void removeGroup(String groupName) {
		this.groups.remove(groupName);
	}

	public String getTagName() {
		return "user";
	}

	public Element toDomElement(Document doc) {
		Document Doc = doc;
		Element Project = Doc.createElement(this.getTagName());

		// Name,WorkingDir,Group

		Project.setAttribute("id", this.id);
		Project.setAttribute("password", this.password);
		Project.setAttribute("forename", this.forename);
		Project.setAttribute("surname", this.surname);
		Project.setAttribute("phonenumber", this.phonenumber);
		Project.setAttribute("location", this.location);

		Project.setAttribute("groups", this.groups.toString());
		Project.setAttribute("roles", this.roles.toString());

		return Project;
	}

	public void init(Element el) {

		if (!el.getTagName().equals(this.getTagName()))
			return;

		this.id = el.getAttribute("id");
		this.forename = el.getAttribute("forename");
		this.surname = el.getAttribute("surname");
		this.phonenumber = el.getAttribute("phonenumber");
		this.location = el.getAttribute("location");
		this.password = el.getAttribute("password");

		this.groups = stringToSet(el.getAttribute("groups"));
		this.roles = stringToSet(el.getAttribute("roles"));

	}

	private HashSet<String> stringToSet(String input) {
		HashSet<String> returnSet = new HashSet<String>();

		if (input.length() > 2 && input.startsWith("[") && input.endsWith("]")) {
			input = input.substring(1, input.length() - 1);
			String[] list = input.split(", ");
			for (int i = 0; i < list.length; i++)
				returnSet.add(list[i]);
		}

		return returnSet;
	}

}
