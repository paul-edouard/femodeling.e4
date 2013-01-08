package de.femodeling.e4.client.model.core;


import de.femodeling.e4.model.core.assembly.AssNode;
import de.femodeling.e4.model.core.assembly.Assembly;

public abstract class AssemblyClient extends Assembly {
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;

	@Override
	public AssNodeClient getRoot() {
		AssNode root= super.getRoot();
		if(root instanceof AssNodeClient){
			return (AssNodeClient) root;
		}
		
		return null;
	}
   
    
    
	
}
