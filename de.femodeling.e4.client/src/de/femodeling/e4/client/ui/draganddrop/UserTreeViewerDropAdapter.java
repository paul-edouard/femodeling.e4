package de.femodeling.e4.client.ui.draganddrop;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.parts.UsersPart;

public class UserTreeViewerDropAdapter extends ViewerDropAdapter {
	
	private final Viewer viewer;
	
	private UserClientImpl group;
	
	private int location;
	
	private String Modus;
	
	private IUserProvider provider;
	
	private IEventBroker broker;

	
	public UserTreeViewerDropAdapter(Viewer viewer,IUserProvider provider,IEventBroker broker) {
		super(viewer);
		this.viewer = viewer;
		this.provider=provider;
		this.broker=broker;
	}
	
	
	@Override
	public void drop(DropTargetEvent event) {
		location = this.determineLocation(event);
	    //String target = (String) determineTarget(event);
		
	    String translatedLocation ="";
	    switch (location){
	    case 1 :
	      translatedLocation = "Dropped before the target ";
	      break;
	    case 2 :
	      translatedLocation = "Dropped after the target ";
	      break;
	    case 3 :
	      translatedLocation = "Dropped on the target ";
	      break;
	    case 4 :
	      translatedLocation = "Dropped into nothing ";
	      break;
	    }
	    
	    System.out.println(translatedLocation);
	   // System.out.println("The drop was done on the element: " + target);

		super.drop(event);
	}




	@Override
	public boolean performDrop(Object data) {
		
		//System.out.println(data.toString());
		if(location==3 && group!=null){
			
			group.addRole(data.toString());
			
			if(provider.updateData(group)!=null){
				broker.send(IBrokerEvents.USER_GROUP_UPDATE, group);
			}
			
		}
		
		return false;
	}

	@Override
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		
		//System.out.println(target.toString());
		
		if(target instanceof UserClientGroup ){
			
			UserClientGroup g=(UserClientGroup) target;
			
			if( !g.getName().equals(UsersPart.ALL_USERS_GROUP)){
			
			group=g.getGroup();
			return true;
			}
		}
		
		group=null;
		return false;
	}

}
