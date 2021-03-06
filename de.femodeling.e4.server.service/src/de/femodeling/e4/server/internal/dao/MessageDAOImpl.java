package de.femodeling.e4.server.internal.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import de.femodeling.e4.model.core.Message;
import de.femodeling.e4.model.core.Message.Type;
import de.femodeling.e4.model.core.lockable.LockableEntity;
import de.femodeling.e4.server.internal.context.ServerContextProvider;
import de.femodeling.e4.server.internal.model.MessageServerImpl;


public class MessageDAOImpl implements MessageDAOIF {
	
	
	LinkedList<Message> lastMessages;
	
	private OldMessageRemover messageRemover;
	
	private static Logger logger = Logger.getLogger(MessageDAOImpl.class);
	
	
	public MessageDAOImpl(){
		lastMessages =new LinkedList<Message>();
		messageRemover=new OldMessageRemover();
		messageRemover.start();
	}
	
	
	private synchronized void  removeFirstMessage(){
		if(!lastMessages.isEmpty())
			lastMessages.removeFirst();
	}
	
	public synchronized void addMessage(Message ent){
		
		
		
		Calendar now=Calendar.getInstance();
		ent.setCreatingTime(now.getTime());
		lastMessages.add(ent);
		
		//logger.info("New Message Added: "+lastMessages.size());
		
		//remove the old messages
		
	}
	
	
	/**
	 * 
	 * add a new massage
	 * 
	 */
	private synchronized void addMessage(String sendingSessionId,
			LockableEntity sendingEntity,Type sendingType,String parentId){
		Message m=new MessageServerImpl(sendingSessionId,sendingEntity,sendingType);
		m.setParentId(parentId);
		addMessage(m);
	}
	
	private synchronized void addMessage(LockableEntity sendingEntity,Type sendingType,String parentId){
		 String s_id=ServerContextProvider.getServerContext().getSessionId();
		 addMessage(s_id, sendingEntity, sendingType,parentId);
	}
	
	
	public synchronized void addAddMessage(LockableEntity sendingEntity,String parentId){
		addMessage(sendingEntity, Message.Type.ADD,parentId);
	}
	public synchronized void addRemoveMessage(LockableEntity sendingEntity,String parentId){
		addMessage(sendingEntity, Message.Type.REMOVE,parentId);
	}
	public synchronized void addUpdateMessage(LockableEntity sendingEntity,String parentId){
		addMessage(sendingEntity, Message.Type.UPDATE,parentId);
	}
	
	public synchronized void addAddMessage(LockableEntity sendingEntity){
		addMessage(sendingEntity, Message.Type.ADD,"");
	}
	public synchronized void addRemoveMessage(LockableEntity sendingEntity){
		addMessage(sendingEntity, Message.Type.REMOVE,"");
	}
	public synchronized void addUpdateMessage(LockableEntity sendingEntity){
		addMessage(sendingEntity, Message.Type.UPDATE,"");
	}
	
	
	@Override
	public synchronized LinkedList<Message> getLastMesssages(final String SessionId, final Date lastMessageCall,final String lastMessageId) {
		LinkedList<Message> returnMesList=new LinkedList<Message>();
		boolean lastMessageReached=lastMessageId.isEmpty();
		for(Message ent:lastMessages){
			
			if(lastMessageReached){
				if(!SessionId.equals(ent.getSendingSessionId()) &&  
						ent.getCreatingTime().compareTo(lastMessageCall)>=0 )
				returnMesList.add(ent);
			}
			else{
				lastMessageReached=ent.getLockableId().equals(lastMessageId);
			}
		}
		
		//Add the all list if the last message couldn't be found
		if(!lastMessageReached){
			returnMesList.addAll(lastMessages);
		}
		
		return returnMesList;
	}

	
	
	
	
	/**
	 * Delete the old Messages of the list
	 * 
	 * @author Paul
	 *
	 */
	class OldMessageRemover extends Thread
	{
	   
		 Message mes;
		 Calendar expireDate;
		 Calendar now;
		 
		 private void setFirstMessageAndExpireDate(){
			 now = Calendar.getInstance();
	    	 expireDate = Calendar.getInstance();	
			 
	    	 if(!lastMessages.isEmpty())
	    		 mes=lastMessages.getFirst();
	    	 else
	    		 mes=null;
			 if(mes!=null)expireDate.setTime(mes.getCreatingTime());
			 expireDate.add(Calendar.SECOND, Message.MESSAGE_EXPIRATION);
		 }
		 
		 
	   public void run()
	   {
	       
		   boolean deleteMessages=true;
		   while(deleteMessages){
	    	   
			   try{
				  // this.wait((int)MESSAGE_EXPIRATION/2);
				   sleep((int)Message.MESSAGE_EXPIRATION*1000);
			   }
			   catch(InterruptedException e){
				   logger.info("InterruptedException: "+e.getMessage());
				   deleteMessages=false;
				   continue;
			   }
			   
	    	   
	    	  // logger.info("Deleting old messages: Before (Messages Size="+lastMessages.size()+")");
	    	   
	    	   setFirstMessageAndExpireDate();
	    	   
    		   while(mes!=null && !expireDate.after(now)){
    			   removeFirstMessage();
    			   setFirstMessageAndExpireDate();
    		   }
	    	   
    		  // logger.info("Deleting old messages: After (Messages Size="+lastMessages.size()+")");
	    	   
	       }
		   
		   
	   }
	}

}
