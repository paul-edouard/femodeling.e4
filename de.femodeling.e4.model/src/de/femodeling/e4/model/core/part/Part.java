package de.femodeling.e4.model.core.part;

import java.util.LinkedList;

import de.femodeling.e4.model.core.LockableEntity;


public abstract class Part extends LockableEntity{
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
   
	
	static final String GroupStr="group";
	static final String ModelStr="model";
	static final String PartialModelStr="partial_model";
	
	public enum Type { GROUP, MODEL, PARTIAL_MODEL , NONE}
	
	
	protected String description;
	protected String partId;
	protected String name;
	protected String owner;
	protected String revision;
	protected int status;
	protected String statusText;
	protected String typeText;
	protected Type type;
	
	protected String ansaModuleId;
	
	
	private LinkedList<Weight> weightList;
	private LinkedList<Material> materialList;
	private LinkedList<Representation> representationList;
	
	protected String cadFileName;
	protected Translation translation=new Translation();
	protected boolean isCadFileFound;
	    
	    
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public void setWeightList(LinkedList<Weight> weightList) {
		this.weightList = weightList;
	}
	public String getDescription() {
		return description;
	}
	public String getPartId() {
		return partId;
	}
	public String getName() {
		return name;
	}
	public String getOwner() {
		return owner;
	}
	public String getRevision() {
		return revision;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusText() {
		return statusText;
	}
	public String getTypeText() {
		return typeText;
	}
	public Type getType() {
		return type;
	}
	
	
	
	public String getAnsaModuleId() {
		return ansaModuleId;
	}
	public void setAnsaModuleId(String ansaModuleId) {
		this.ansaModuleId = ansaModuleId;
	}
	public LinkedList<Weight> getWeightList() {
		if(weightList==null)weightList=new LinkedList<Weight>();
		return weightList;
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
	
	public LinkedList<Representation> getRepresentationList() {
		if(representationList==null)representationList=new LinkedList<Representation>();
		return representationList;
	}
	public void setRepresentationList(LinkedList<Representation> representationList) {
		this.representationList = representationList;
	}
	
	
	
	//Add
	public void addMaterial(Material m){
		if(materialList==null)materialList=new LinkedList<Material>();
		materialList.add(m);
	}
	
	public void addWeigth(Weight w){
		if(weightList==null)weightList=new LinkedList<Weight>();
		weightList.add(w);
	}
	
	public void addRepresentation(Representation rep){
		if(representationList==null)representationList=new LinkedList<Representation>();
		representationList.add(rep);
	}
	
		
	public static String typeToString(Type type){
		if(type==null)return "unknow type";
		
		switch(type){
			case GROUP:return GroupStr;
			case MODEL:return ModelStr;
			case PARTIAL_MODEL:return PartialModelStr;
			default: return "unknow type";
		}
	}
	
	
	
	public static Type stringToType(String str){
		if(str==null || str.isEmpty())return Type.NONE;
		
		if(str.equals(GroupStr))
			return Type.GROUP;
		else if(str.equals(ModelStr))
			return Type.MODEL;
		else if(str.equals(PartialModelStr))
			return Type.PARTIAL_MODEL;
		else
			return Type.NONE;
	}
	
	

}
