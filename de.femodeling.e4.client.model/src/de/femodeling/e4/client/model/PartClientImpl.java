package de.femodeling.e4.client.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.client.model.core.PartClient;
import de.femodeling.e4.client.model.listener.PartClientListenerIF;
import de.femodeling.e4.model.core.part.Material;
import de.femodeling.e4.model.core.part.Part.Type;
import de.femodeling.e4.model.core.part.Property;
import de.femodeling.e4.model.core.part.RepFile;
import de.femodeling.e4.model.core.part.Representation;
import de.femodeling.e4.model.core.part.Translation;
import de.femodeling.e4.model.core.part.Weight;
import de.femodeling.e4.util.PdmuKonfiguration;




public class PartClientImpl extends PartClient{

	 /** The serial version UID. */
	static final long serialVersionUID=1L;
	
	private static String DM_ROOT_NAME="ANSA_DM";
	private static String PARTS_PATH="parts";
	
	public static String getLocalPartsPath(){
		return DM_ROOT_NAME+File.separator+PARTS_PATH;
	}
	
	
	private LinkedList<PartClientImpl> childs;
	private PartClientImpl parent=null;
	
	
	public LinkedList<PartClientImpl> getChilds() {
		if(childs==null)childs=new LinkedList<PartClientImpl>();
		return childs;
	}
	
	public void addChild(PartClientImpl child){
		child.setParent(this);
		if(!this.getChilds().contains(child))
			this.getChilds().add(child);
	}
	

	public PartClientImpl getParent() {
		return parent;
	}

	protected void setParent(PartClientImpl parent) {
		this.parent = parent;
	}

	public void setDescription(String description) {
		propertyChangeSupport.firePropertyChange("description", this.getDescription(),description);
		fireChanged(this);
		super.setDescription(description);
	}
	
	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name",this.getName(),name);
		fireChanged(this);
		super.setName(name);
	}
	

	@Override
	public void setCadFileFound(boolean isCadFileFound) {
		propertyChangeSupport.firePropertyChange("isCadFileFound",this.isCadFileFound(),isCadFileFound);
		fireChanged(this);
		super.setCadFileFound(isCadFileFound);
	}

	@Override
	public void setCadFileName(String cadFileName) {
		propertyChangeSupport.firePropertyChange("cadFileName",this.getCadFileName(),cadFileName);
		fireChanged(this);
		super.setCadFileName(cadFileName);
	}

	@Override
	public void setPartId(String partId) {
		propertyChangeSupport.firePropertyChange("partId",this.getPartId(),partId);
		fireChanged(this);
		super.setPartId(partId);
	}

	@Override
	public void setOwner(String owner) {
		super.setOwner(owner);
	}

	@Override
	public void setRevision(String revision) {
		propertyChangeSupport.firePropertyChange("revision",this.getRevision(),revision);
		fireChanged(this);
		super.setRevision(revision);
	}

	@Override
	public void setStatus(int status) {
		propertyChangeSupport.firePropertyChange("status",this.getStatus(),status);
		fireChanged(this);
		super.setStatus(status);
	}

	@Override
	public void setStatusText(String statusText) {
		propertyChangeSupport.firePropertyChange("statusText",this.getStatusText(),statusText);
		fireChanged(this);
		super.setStatusText(statusText);
	}

	@Override
	public void setTypeText(String typeText) {
		propertyChangeSupport.firePropertyChange("typeText",this.getTypeText(),typeText);
		fireChanged(this);
		super.setTypeText(typeText);
	}

	@Override
	public void setType(Type type) {
		propertyChangeSupport.firePropertyChange("type",this.getType(),type);
		fireChanged(this);
		super.setType(type);
	}

	@Override
	public void setAnsaModuleId(String ansaModuleId) {
		propertyChangeSupport.firePropertyChange("ansaModuleId",this.getAnsaModuleId(),ansaModuleId);
		fireChanged(this);
		super.setAnsaModuleId(ansaModuleId);
	}

	@Override
	public void setTranslation(Translation translation) {
		propertyChangeSupport.firePropertyChange("translation",this.getTranslation(),translation);
		fireChanged(this);
		super.setTranslation(translation);
	}

	@Override
	public void setRepresentationList(
			LinkedList<Representation> representationList) {
		propertyChangeSupport.firePropertyChange("representationList",this.getRepresentationList(),representationList);
		fireChanged(this);
		super.setRepresentationList(representationList);
	}
	
	
	/**
	 * return the file path of the local repfile
	 * 
	 * @return
	 */
	public String getLocalRepFilePath(int repId,String fileName,String wordir){
		Representation rep=null;
		for(Representation rep_t:this.getRepresentationList()){
			if(rep_t.getId()==repId){rep=rep_t;break;}
		}
		
		return getLocalRepFilePath(rep,fileName,wordir);
		
	}
	
	
	
	public String getLocalRepFilePath(Representation rep,String fileName,String wordir){
		
		if(rep==null)return "";
		
		String repPath=this.getName()+File.separator+this.getRevision()+File.separator+
				String.valueOf(rep.getId())+File.separator+"repr";
		
		if(!rep.getFileMap().containsKey(fileName))return "";
		
		String ansaName=rep.getFileMap().get(fileName).getName();
		//String transRootDir=GeneralPreferences.INSTANCE.getWorkDir();
		String localAnsaPath=wordir+File.separator+PartClientImpl.getLocalPartsPath()+File.separator+repPath;
		
		return localAnsaPath+File.separator+ansaName;
		
		
	}
	
	public String getServerRepFilePath(ProjectClientImpl proj, int repId,String fileName){
		
		Representation rep=null;
		for(Representation rep_t:this.getRepresentationList()){
			if(rep_t.getId()==repId){rep=rep_t;break;}
		}
		
		return getServerRepFilePath(proj, rep,fileName);
		
	}
	
	public String getServerRepFilePath(ProjectClientImpl proj, Representation rep,String fileName){
		//TODO ask the server to get the file name
		//create a corresponding server service
		return null;
		/*
		if(rep==null)return "";
		
		if(!rep.getFileMap().containsKey(fileName))return "";
		
		if(proj==null)return "";
		
		ProjectServerImpl s_proj=ProjectTransformService.transform(ProjectTransformService.transformClient(proj));
		
		String serverPartPath=s_proj.getPartsDir()+File.separator+PartServerImpl.typeToDirectory(this.getType());
		String repPath=this.getName()+File.separator+this.getRevision()+File.separator+
				String.valueOf(rep.getId())+File.separator+"repr";
		
		String ansaName=rep.getFileMap().get(Translation.TRANSLATION_FILE).getName();
		
		String targetPath=serverPartPath+File.separator+repPath;
		
		return targetPath+File.separator+ansaName;
		*/
	}
	
	
	public Representation getTranslationRep(){
		if(this.getTranslation()==null)return null;
		
		for(Representation rep:this.getRepresentationList()){
			if(rep.getId()==this.getTranslation().getRepresentationId())
				 return rep;
		}
		
			
		return null;	
	}
	
	public HashMap<Integer, Property> getTranslationProperties(){
		if( this.getTranslation()!=null && this.getTranslationRep()!=null){
			RepFile file=this.getTranslationRep().getFileMap().get(Translation.TRANSLATION_FILE);
			if(file==null)return null;
		
			return file.getPropertyMap();
		}
		
		return null;
	}
	
	
	//TODO Export MatInfo Csv
	public String createMatInfoCsv(String firstColumn){
		String matInfoCsv="";
		
		String MatCsv="";
		for(Material mat:this.getMaterialList()){
			MatCsv+=mat.toCsv();
		}
		
		String numberOfProp="";
		if(this.getTranslationProperties()==null)numberOfProp="0";
		else numberOfProp=String.valueOf(this.getTranslationProperties().size());
		
		matInfoCsv+=firstColumn+"|"+this.getName()+"|"+"All"+"|"+MatCsv+"|"+
					String.valueOf(getPercentMatPart())+"|"+String.valueOf(this.getPercentMatBody())+"|"
					+numberOfProp+"\n";
		if(this.getTranslationProperties()!=null){
		for(Property prop:this.getTranslationProperties().values()){
			if(prop.getOriginMaterial().equals("body"))
				matInfoCsv+=firstColumn+"|"+this.getName()+"|"+prop.getName()+"|"+prop.getCadMaterial()+"|"+String.valueOf(getPercentMatPart())+"|"+"100"+"\n";
			else
				matInfoCsv+=firstColumn+"|"+this.getName()+"|"+prop.getName()+"|"                      +"|"+String.valueOf(getPercentMatPart())+"|"+"0"+"\n";
		}
		}
		
		return matInfoCsv;
	}
	
	
	public int getPercentMat(){
		
		if(this.isEmpty())return 0;
		
		if(this.getPercentMatPart()==0)
			return this.getPercentMatBody();
		else return this.getPercentMatPart();
	}
	
	private int getPercentMatPart(){
		if(this.getMaterialList().isEmpty())return 0;
		else return 100;
	}
	
	private int getPercentMatBody(){
		
		if(this.getTranslation()!=null &&
				this.getTranslation().getState()==Translation.State.TRANSLATED){
			HashMap<Integer, Property> propMap=this.getTranslationProperties();
			if(propMap!=null && propMap.size()>0){
				
				int k=0;
				for(Property prop:propMap.values()){
					if(!prop.getCadMaterial().isEmpty() && 
							prop.getOriginMaterial().equals("body"))k++;
				}
				
				return k*100/propMap.size();
				
			}
			else return 0;
			
		}
		else return 0;
		
	}
	
	
	public boolean isEmpty(){
		HashMap<Integer, Property> propMap=this.getTranslationProperties();
		if(propMap!=null){
			if(propMap.size()>0)
			return false;
			else return true;
		}
		else  return true;
	}
	
	
	public boolean isVTA(){
		return (this.getName().matches(".*.S\\d.*") && !this.getName().matches(".*TS\\d.*")) 
				|| this.getName().matches(".*_VTA.*") || this.getDescription().matches(".*VTA.*");
	}
	
	public boolean isStandartPart(){
		return this.getName().startsWith("900") || this.getName().startsWith("999")
				|| this.getName().startsWith("N") || this.getName().startsWith("WHT");
	}
	
	public String getVTAString(){
		if(isVTA())return "[VTA]";
		else return "";
	}
	
	public String getStandardPartString(){
		if(isStandartPart())return "[NORMTEIL]";
		else return "";
	}
	
	
	public String[] getWeigth(){
		String[] r=new String[3];
		
		r[0]="0.0";
		r[1]="None";
		r[2]="";
		
		if(this.getWeightList()==null)return r;
		
		
		if(isVTA()){
			r[1]="VTA";
			return r;
		}
		
		int prio=1;
		for(Weight w:this.getWeightList()){
			
			if(w.getEvaluation().equals("EIV")){
				r=createWeigthStr(w);
				return r;
			}
			else if(w.getWeightTyp().equals("IST")){
				r=createWeigthStr(w);
				prio=4;
				continue;
			}
			else if(w.getWeightTyp().equals("Q") && prio<4){
				r=createWeigthStr(w);
				prio=3;
				continue;
			}
			else if(w.getWeightTyp().equals("VSI") && prio<3){
				r=createWeigthStr(w);
				prio=2;
				continue;
			}
			else if(w.getWeightTyp().equals("ZIEL") && prio<2){
				r=createWeigthStr(w);
				continue;
			}
			
		}
		
		return r;
		
	}
	
	private String[] createWeigthStr(Weight w){
		String[] r=new String[3];
		r[0]="0.0";
		r[1]="None";
		r[2]="";
		
		
		String val=w.getValue().replaceAll(",", ".");
	//	System.out.println(val);
		
		try{
			
			float v=Float.parseFloat(val);
			if(v>0){
				r[0]=w.getValue().replaceAll(",", ".");
				r[1]=w.getEvaluation();
				r[2]=w.getWeightTyp();
			}
		}
		catch(Exception e){
			
		}
		
		return r;
		
	}
	
	

	public void createUniqueAnsaModuleId(HashSet<String> shortedKeySet){
		
		
		String shortedKey=this.getName();
		
		if(this.getType()==Type.GROUP && !this.getTypeText().contains("Teilstruktur")){
			
			//System.out.println("Group:"+this.getName()+" Modif: "+PdmuKonfiguration.getPartName(this.getName()));
			shortedKey=this.getTypeText().replaceFirst("manuell", "M")+"_"+PdmuKonfiguration.getPartName(this.getName());
			
		}
		
		//String shortedKey=this.getName();
		if(shortedKey.length()>39)shortedKey=shortedKey.substring(0, 38);
		
		this.setAnsaModuleId(shortedKey);
		int instance=0;
		
		int origineLength=this.getAnsaModuleId().length();
		
		while(shortedKeySet.contains(this.getAnsaModuleId())){
			
			//System.out.println("Old Module Id: "+shortedKey);
			String instance_str="_"+String.valueOf(instance);
			String new_ansa_mid=this.getAnsaModuleId();
			
			if(new_ansa_mid.length()>origineLength)
				new_ansa_mid=new_ansa_mid.substring(0,origineLength);
			
			while(new_ansa_mid.length()+instance_str.length()>39){
				int diff=new_ansa_mid.length()+instance_str.length()-39;
				new_ansa_mid=new_ansa_mid.substring(0,new_ansa_mid.length()-diff);
			}
			
			new_ansa_mid=new_ansa_mid+instance_str;
			
			this.setAnsaModuleId(new_ansa_mid);
			
			//System.out.println("New Module Id: "+new_ansa_mid);
			
			instance++;		
		}
		
		shortedKeySet.add(this.getAnsaModuleId());
	}
	
	
	/***********************************
	 *                                 *
	 *	            XML                *
	 *                                 *
	 ***********************************/	
	
	public Element toDomElement(Document doc){
		Document Doc=doc;
		Element e=Doc.createElement("data");
		
		e.setAttribute("description", this.getDescription());
		e.setAttribute("id", this.getPartId());
		e.setAttribute("name", this.getName());
		e.setAttribute("owner", this.getOwner());
		e.setAttribute("revision", this.getRevision());
		e.setAttribute("statusText", this.getStatusText());
		e.setAttribute("type", this.getTypeText());
		e.setAttribute("cad_filename", this.getCadFileName());
	
		return e;
	}
	
	
	
	/***********************************
	 *                                 *
	 *		      LISTENER             *
	 *                                 *
	 ***********************************/	
	private List<PartClientListenerIF> listeners;
	
	@Override
	protected void fireLockableEntityChanged() {
		fireChanged(this);
	}
	
	public void addPartListener(PartClientListenerIF listener){
		if (getParent() != null)
			getParent().addPartListener(listener);
		else {
			if (listeners == null)
				listeners = new LinkedList<PartClientListenerIF>();
			listeners.add(listener);
		}
	}
	
	public void removePartListener(PartClientListenerIF listener){
		if (getParent() != null)
			getParent().removePartListener(listener);
		else {
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty())
					listeners = null;
			}
		}
	}
	
	
	protected void fireChanged(PartClientImpl entry)
	{
		if (getParent() != null){
			//logger.warn("Parent Found For: "+this.getName());
			getParent().fireChanged(entry);
		}
		else {
		if (listeners == null)
			return;
		Object[] rls = listeners.toArray();
		for (int i = 0; i < rls.length; i++) {
			PartClientListenerIF listener =(PartClientListenerIF)  rls[i];
			listener.partChanged(entry);
		}
		}
		
	}
	
}
