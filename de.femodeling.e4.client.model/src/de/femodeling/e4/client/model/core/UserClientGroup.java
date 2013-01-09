package de.femodeling.e4.client.model.core;

import java.util.Collection;
import java.util.LinkedList;

import de.femodeling.e4.client.model.UserClientImpl;

public class UserClientGroup {
	
	
	 protected String name;
	 
	 protected UserClientImpl group;
	 
	 LinkedList<Object> contentList;
	 
	 protected Object parent;
	 
	 /*
	 public UserClientGroup(String name){
		 this.name=name;
		 contentList=new LinkedList<Object>();
	 }
	 */
	 
	 public UserClientGroup(UserClientImpl group){
		 this.group=group;
		 this.name=group.getForename();
		 contentList=new LinkedList<Object>();
	 }
	 
	 
	 public static UserClientGroup createGroup(String name){
		 
		 UserClientImpl u=new UserClientImpl();
		 u.setId(name);
		 u.setForename(name);
		 
		 UserClientGroup g=new UserClientGroup(u);
		 
		 return g;
	 }
	 
	 
	 
	public UserClientImpl getGroup() {
		if(group==null)group=new UserClientImpl();
		return group;
	}



	public String getName() {
		return name;
	}

	public LinkedList<Object> getContentList() {
		return contentList;
	}
	 
	
	public void addUser(UserClientImpl user){
		contentList.add(user);
		group.addRole(user.getId());
		user.setParent(this);
	}
	
	public void removeUser(UserClientImpl user){
		contentList.remove(user);
		group.removeRole(user.getId());
	}
	
	
	public void clearList(){
		contentList.clear();
	}
	
	
	public void addGroup(UserClientGroup group){
		contentList.add(group);
		this.group.addRole(group.getGroup().getId());
		group.setParent(this);
	}
	
	public void removeGroup(UserClientGroup group){
		contentList.remove(group);
		this.group.removeRole(group.getGroup().getId());
	}

	
	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}
	
	public int getNumberOfOnlineUsers(){
		int count=0;
		for(Object o:contentList){
			if(o instanceof UserClientImpl){
				if(((UserClientImpl)o).isOnline())
					count++;
			}
		}
		
		
		return count;
	}
	
	public void initUserChilds(Collection<UserClientImpl> users){
		for(UserClientImpl u:users){
			if(group.getRoles().contains(u.getId())){
				addUser(u.createCopy());
			}
				
		}
	}
	
	public void initGroupChilds(Collection<UserClientGroup> groups){
		for(UserClientGroup g:groups){
			if(group.getRoles().contains(g.getGroup().getId())){
				addGroup(g);
			}
		}
	}
	
	 
}
