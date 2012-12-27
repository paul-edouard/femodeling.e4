package de.femodeling.e4.model.dto;

import java.io.Serializable;

import de.femodeling.e4.model.core.assembly.AssNode;
import de.femodeling.e4.model.core.assembly.Assembly.Type;


public abstract class AssemblyDTO extends LockableEntityDTO implements
		Serializable {
	
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    private AssNode root;
	private Type type;

	public AssNode getRoot() {
		return root;
	}


	public void setRoot(AssNode root) {
		this.root = root;
	}
    
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
    

}
