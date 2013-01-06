package de.femodeling.e4.client.model.core;


import de.femodeling.e4.client.model.LockableEntityClientImpl;
import de.femodeling.e4.model.core.assembly.Assembly.Type;

public abstract class AssemblyClient extends LockableEntityClientImpl {
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    private Type type;
    
    private AssNodeClient root;
    
    

	public AssNodeClient getRoot() {
		return root;
	}

	public void setRoot(AssNodeClient root) {
		this.root = root;
	}
    
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
