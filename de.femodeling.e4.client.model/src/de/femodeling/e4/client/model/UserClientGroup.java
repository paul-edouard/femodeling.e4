package de.femodeling.e4.client.model;

import java.util.LinkedList;

public class UserClientGroup {
	
	
	 protected String name;
	 
	 LinkedList<Object> contentList;
	 
	 protected Object parent;
	 
	 public UserClientGroup(String name){
		 this.name=name;
		 contentList=new LinkedList<Object>();
	 }

	public String getName() {
		return name;
	}

	public LinkedList<Object> getContentList() {
		return contentList;
	}
	 
	public void addUser(UserClientImpl user){
		contentList.add(user);
		user.setParent(this);
	}
	
	public void clearList(){
		contentList.clear();
	}
	
	public void addGroup(UserClientGroup group){
		contentList.add(group);
		group.setParent(this);
	}

	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}
	
	
	 
}
