package de.femodeling.e4.internal.model;

import de.femodeling.e4.model.core.assembly.AssEdge;
import de.femodeling.e4.model.core.assembly.AssNode;
import de.femodeling.e4.model.core.assembly.Assembly;



public class AssemblyServerImpl extends Assembly {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    public AssemblyServerImpl(Assembly.Type type){
    	AssNode n=new AssNode();
    	n.setId(AssNode.ROOT_ID);
    	
    	setRoot(n);
    	
    	setType(type);
    }
    
    
    public void addSubAssembly(AssNode n){
    	AssEdge e=new AssEdge();
    	e.setId(AssEdge.ROOT_ID);
    	e.setNode(n);
    	e.setDescription("Sub-Assembly");
    	
    	getRoot().addEdge(e);
    	
    	
    }
    
    public String getXmlFileName(){
		if(this.getType()==Type.PDMU)
			return "Assembly.xml";
		else if(this.getType()==Type.FE_MODULE)
			return "Fe_module.xml";
		else
			return "";
	}

}
