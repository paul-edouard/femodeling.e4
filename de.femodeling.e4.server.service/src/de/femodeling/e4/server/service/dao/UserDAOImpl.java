package de.femodeling.e4.server.service.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlFile;
import de.femodeling.e4.server.internal.model.UserServerImpl;
import de.femodeling.e4.util.Execute;
import de.femodeling.e4.util.Utils;


public class UserDAOImpl extends XmlFile implements UserDAOIF {

	private HashMap<String, UserServerImpl> users = null;
	private String fileName;

	public UserDAOImpl(String userFileName) {
		super();
		this.users = new HashMap<String, UserServerImpl>();
		this.fileName = userFileName;
	}

	@Override
	public Set<UserServerImpl> loadAll() {
		if (!readFromXml(fileName))
			return null;
		Set<UserServerImpl> set = new HashSet<UserServerImpl>();
		set.addAll(users.values());
		return set;
	}

	@Override
	public UserServerImpl loadUser(String id) {
		if (!readFromXml(fileName))
			return null;
		return users.get(id);
	}

	@Override
	synchronized public UserServerImpl saveUser(UserServerImpl user) {
		if (!readFromXml(fileName))
			return null;
		if (user.getId().isEmpty())
			return null;
		users.put(user.getId(), user);
		if (!saveAsXml(fileName))
			return null;
		return user;
	}

	@Override
	synchronized public boolean deleteUser(UserServerImpl user) {
		if (!readFromXml(fileName))
			return false;
		users.remove(user.getId());
		if (!saveAsXml(fileName))
			return false;
		return true;
	}

	/**
	 * return the TAG Name used in the xml file
	 */
	public String getTagName() {
		return "userList";
	}

	/**
	 * initializes the users map from a xml element
	 */
	public void init(Element Root) {

		if (Root.getTagName().equals(this.getTagName())) {

			users.clear();

			NodeList Children = Root.getChildNodes();

			for (int i = 0; i < Children.getLength(); i++) {
				Node child = Children.item(i);
				if (child instanceof Element) {
					Element childElement = (Element) child;

					UserServerImpl u = new UserServerImpl();
					// initialize a vpm Part
					if (childElement.getTagName().equals(u.getTagName())) {

						u.init(childElement);
						u.setGroups(getUserGroups(u.getId()));
						users.put(u.getId(), u);

					}
				}
			}

		}
	}

	private HashSet<String> getUserGroups(String userId) {
		HashSet<String> set = new HashSet<String>();
		if (Utils.isWIndow())
			set.add("Window");
		else {
			String output = new Execute().getOutput("groups " + userId);
			String[] groups = output.split(" ");
			if (!output.isEmpty() && groups.length > 0) {
				for (int i = 0; i < groups.length; i++) {
					set.add(groups[i]);
				}
			}
		}

		return set;
	}

	/**
	 * export the user map in a xml element
	 */
	public Element toDomElement(Document doc) {
		Element userList = doc.createElement(this.getTagName());

		for (String key : users.keySet()) {
			userList.appendChild(users.get(key).toDomElement(doc));
		}

		return userList;
	}

	public static void main(String[] args) {

		UserServerImpl u = new UserServerImpl();
		u.setId("p3235187");
		u.setForename("Paul");
		u.setSurname("Munch");

		// UserProvider.INSTANCE.setFileName("/v/ceplx213/fs2/scr/scr1/p323518/Prog/TestDir/Proj/UserList.xml");
		// UserProvider.INSTANCE.addUser(u);
	}

}
