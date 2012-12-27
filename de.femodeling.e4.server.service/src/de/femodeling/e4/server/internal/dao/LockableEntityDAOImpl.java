package de.femodeling.e4.server.internal.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlFile;
//import org.apache.log4j.Logger;
import de.femodeling.e4.server.internal.model.LockableEntityServerImpl;


public class LockableEntityDAOImpl extends XmlFile implements LockableEntityDAOIF {
	
	
	
	private HashMap<String,LockableEntityServerImpl> entities=null;
	private String fileName;
	
	
	//private static Logger logger = Logger.getLogger(LockableEntityDAOImpl.class);
	
	public LockableEntityDAOImpl(String fileName){
		super();
		this.entities=new HashMap<String, LockableEntityServerImpl>();
		this.fileName = fileName;
		saveAsXml(fileName);
	}
	
	@Override
	synchronized public boolean lockEntity(LockableEntityServerImpl ent) {
		if(!readFromXml(fileName))return false;
		entities.put(ent.getLockableId(), ent);
		if(!saveAsXml(fileName))return false;
		
		return true;
	}

	@Override
	synchronized public boolean unlockEntity(LockableEntityServerImpl ent) {
		if(!readFromXml(fileName))return false;
		LockableEntityServerImpl d=entities.remove(ent.getLockableId());
		if(d==null)return false;
		if(!saveAsXml(fileName))return false;
		return true;
	}

	@Override
	synchronized public LockableEntityServerImpl isEntityLocked(LockableEntityServerImpl ent) {
		//logger.info("Debug 00");
		if(!readFromXml(fileName))return null;
		//logger.info("Debug 01");
		return entities.get(ent.getLockableId());
	}

	@Override
	synchronized public Set<LockableEntityServerImpl> getAllLockedEntities() {
		if(!readFromXml(fileName))return null;
		Set<LockableEntityServerImpl> set =new HashSet<LockableEntityServerImpl>();
		set.addAll(entities.values());
		return set;
	}
	
	@Override
	synchronized public void unlockAllEntitiesFromSession(String sessionId){
		if(!readFromXml(fileName))return ;
		HashMap<String,LockableEntityServerImpl> restEntities=new HashMap<String,LockableEntityServerImpl>();
		for (LockableEntityServerImpl ent : entities.values()) {
			if(!ent.getSessionId().equals(sessionId)){
				restEntities.put(ent.getLockableId(), ent);
			}
		}
		entities.clear();
		entities=restEntities;
		saveAsXml(fileName);
		
	}
	
	
	/**
	 * return the TAG Name used in the xml file
	 */
	public String getTagName(){return "lockedEntitiesList";}
	
	/**
	 * initializes the users map from a xml element
	 */
	public void init(Element Root){
		
		if(Root.getTagName().equals(this.getTagName())){
			
			entities.clear();
			
			NodeList Children=Root.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					
					//Seach for lockable entities
					LockableEntityServerImpl ent=new LockableEntityServerImpl();
					//initialize
					if(childElement.getTagName().equals(ent.getTagName())){
						
						ent.init(childElement);
						entities.put(ent.getLockableId(), ent);
						
					}
				}
			}
		
		}
	}
	
	
	/**
	 * export the user map in a xml element
	 */
	public Element toDomElement(Document doc){
		Element list=doc.createElement(this.getTagName());
			
			for(String key:entities.keySet()){	
				list.appendChild(entities.get(key).toDomElement(doc));
			}
			
			return list;
	  }
	

}
