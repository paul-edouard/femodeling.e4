package de.femodeling.e4.internal.model;

import java.io.File;

import de.femodeling.e4.model.core.part.Part;


public class PartServerImpl extends Part{
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    static final String GroupDirectory="01_Group";
	static final String ModelDirectory="02_Model";
	static final String PartialModelDirectory="03_PartialModel";
	
	public String getXmlFileName(){
		return typeToDirectory()+File.separator+this.getFileName()+".xml";
	}
	
	public String getDirectory(){
		return typeToDirectory()+File.separator+this.getFileName();
	}
	
	private String getFileName(){
		return this.name.replace(" ", "_")+"_"+this.revision.replace(" ", "_");
	}
	
	private String typeToDirectory(){
		return typeToDirectory(type);
		
	}
	
	
	
	public static String typeToDirectory(Part.Type type){
		
		switch(type){
		case GROUP:return GroupDirectory;
		case MODEL:return ModelDirectory;
		case PARTIAL_MODEL:return PartialModelDirectory;
		default: return "";
		}
		
	}
	
}
