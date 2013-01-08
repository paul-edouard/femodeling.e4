package de.femodeling.e4.client.model.core;

import java.io.File;

import de.femodeling.e4.model.core.part.Material;
import de.femodeling.e4.model.core.part.Part;

public abstract class PartClient extends Part {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
   
    protected String projectId;
    
    
    public String toCsvLine(){
    	String[] columns=new String[45];
    	for(int i=0;i<columns.length;i++){
    		columns[i]="";
    	}
    	
    	columns[1]="_";
    	columns[3]=this.getAnsaModuleId();
    	columns[4]=this.getRevision();
    	columns[21]=this.getDescription();
    	columns[37]=new File(this.getCadFileName()).getName().replaceAll("±", "_");
    	columns[38]=this.getOwner();
    	columns[39]=this.getStatusText();
    	
    	//Export Materials
    	for(Material mat:this.getMaterialList()){
    		columns[43]+=mat.toCsv()+";";
    	}
    	if(columns[43].endsWith(";"))
    		columns[43]=columns[43].substring(0, columns[43].length()-1);
    	
    	
    	String Line="";
    	for(int i=0;i<columns.length;i++){
    		if(i<columns.length-1)
    			Line+=columns[i]+"|";
    		else
    			Line+=columns[i];
    	}
    	
    	return Line;
    }
    
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
