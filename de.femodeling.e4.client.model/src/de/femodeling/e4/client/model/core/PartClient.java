package de.femodeling.e4.client.model.core;

import java.io.File;
import java.util.LinkedList;

import de.femodeling.e4.client.model.LockableEntityClientImpl;
import de.femodeling.e4.model.core.part.Material;
import de.femodeling.e4.model.core.part.Part.Type;
import de.femodeling.e4.model.core.part.Representation;
import de.femodeling.e4.model.core.part.Translation;
import de.femodeling.e4.model.core.part.Weight;

public abstract class PartClient extends LockableEntityClientImpl {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    private String description;
    private String partId;
    private String name;
    private String owner;
    private String revision;
    private int status;
    private String statusText;
    private String typeText;
    private Type type;
    
    private String ansaModuleId;
   
    private LinkedList<Weight> weightList;
    private LinkedList<Material> materialList;
	private LinkedList<Representation> representationList;
    
	protected  Translation translation=new Translation();;
    
    protected String cadFileName;
    protected boolean isCadFileFound;
    
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
    
	public boolean isCadFileFound() {
		return isCadFileFound;
	}
	public void setCadFileFound(boolean isCadFileFound) {
		this.isCadFileFound = isCadFileFound;
	}
    
    
	public String getCadFileName() {
		return cadFileName;
	}
	public void setCadFileName(String cadFileName) {
		this.cadFileName = cadFileName;
	}
    
    

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public LinkedList<Weight> getWeightList() {
		if(weightList==null)weightList=new LinkedList<Weight>();
		return weightList;
	}

	public void setWeightList(LinkedList<Weight> weightList) {
		this.weightList = weightList;
	}

	public String getAnsaModuleId() {
		return ansaModuleId;
	}

	public void setAnsaModuleId(String ansaModuleId) {
		this.ansaModuleId = ansaModuleId;
	}
    
	public LinkedList<Material> getMaterialList() {
		if(materialList==null)materialList=new LinkedList<Material>();
		return materialList;
	}
	public void setMaterialList(LinkedList<Material> materialList) {
		this.materialList = materialList;
	}
	public Translation getTranslation() {
		return translation;
	}
	public void setTranslation(Translation translation) {
		this.translation = translation;
	}
	
	public void addRepresentation(Representation rep){
		
		LinkedList<Representation> toremove=new LinkedList<Representation>();
		for(Representation content:this.getRepresentationList()){
			if(content.getId()==rep.getId())
				toremove.add(content);
		}
		
		this.getRepresentationList().removeAll(toremove);
		
		this.getRepresentationList().add(rep);
		
	}
	
	public LinkedList<Representation> getRepresentationList() {
		if(representationList==null)representationList=new LinkedList<Representation>();
		return representationList;
	}
	public void setRepresentationList(LinkedList<Representation> representationList) {
		this.representationList = representationList;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
