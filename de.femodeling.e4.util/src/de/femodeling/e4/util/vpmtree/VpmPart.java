package de.femodeling.e4.util.vpmtree;


import java.util.HashMap;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.part.Material;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.core.part.Weight;
import de.femodeling.e4.model.dto.PartDTO;
import de.femodeling.e4.model.dto.PartDTOImpl;


public class VpmPart extends VpmXmlElement{

	
	private String description;
	private String id;
	private String name;
	private String owner;
	private String revision;
	private String status;
	private String statusText;
	private String type;
	//private LinkedList<LinkedFile> fileList;
	private HashMap<String, VpmLinkedFile> fileMap;
	private LinkedList<VpmProject> projectList;
	private LinkedList<VpmWeight> weightList;
	private LinkedList<VpmMaterial> materialList;
	
	
	
	
	public PartDTO convertToPart(){
		
		PartDTO p = new PartDTOImpl();
		/*
		try{
		int intStatus=Integer.parseInt(status);
		}
		catch(Exception e){
			
		}
		*/
		//part.
		p.setDescription(description);
		p.setName(name);
		p.setOwner(owner);
		p.setPartId(id);
		p.setRevision(revision);
		try{
		p.setStatus(Integer.parseInt(status));
		}catch(Exception e){	}
		p.setStatusText(statusText);
		if(type.equals("Modell-ZSB") ){
			p.setType(Part.Type.MODEL);
		}
		else if(type.equals("Modell")){
			p.setType(Part.Type.PARTIAL_MODEL);
		}
		else if( type.equals("ZSB-manuell")){
			p.setType(Part.Type.GROUP);
		}
		else if(type.contains("Modell") || type.contains("Teilstruktur-V5-fix")|| type.equals("Teilstruktur-V5")){
			p.setType(Part.Type.MODEL);
		}
		else if(type.contains("Teilmodell")){
			p.setType(Part.Type.PARTIAL_MODEL);
		}
		else{
			p.setType(Part.Type.GROUP);
		}
		
		
		
		p.setTypeText(type);
		
		

		//Weight list
		LinkedList<Weight> w_l=new LinkedList<Weight>();
		
		for(VpmWeight v_w : weightList){
			Weight w=new Weight();
			w.setDate(v_w.getDate());
			w.setEvaluation(v_w.getEvaluation());
			w.setOriginSystem(v_w.getOriginSystem());
			w.setValue(v_w.getValue());
			w.setWeightTyp(v_w.getWeightTyp());
			w_l.add(w);
		}
		
		p.setWeightList(w_l);
		
		//Material List
		LinkedList<Material> m_l=new LinkedList<Material>();
		
		for(VpmMaterial mat:materialList){
			Material m=new Material();
			m.setAnteil(mat.getAnteil());
			m.setNormbezeichnung(mat.getNormbezeichnung());
			m.setText(mat.getText());
			m.setWerkstoff(mat.getWerkstoff());
			m_l.add(m);
		}
		
		p.setMaterialList(m_l);
		
		//CAD File Name
		for(VpmLinkedFile file:fileMap.values()){
			if(file.getFormat().contains("CGR"))continue;
			p.setCadFileName(file.getName());
			
			if(type.isEmpty() && !file.getFormat().isEmpty()){
				if(file.getFormat().equals("CATIA")){
					p.setType(Part.Type.PARTIAL_MODEL);
				}
				else if(file.getFormat().equals("asm")){
					p.setType(Part.Type.GROUP);
				}
				else if(file.getFormat().equals("prt")){
					p.setType(Part.Type.PARTIAL_MODEL);
				}
			}
			
		}
		
		
		return p;
		
	}
	
	public  boolean isFeModule(){
		return name.matches("(\\d{1}).(\\d{2}).(\\d{2})_(.*)");
	}
	
	public String getModuleId(){
		return name.substring(0, 7);
	}
	
	public String getModuleName(){
		return name.substring(8, name.length());
	}
	
	
	public static String getTagName(){return "data";}
	
	public String getId() {
		return id;
	}
	
	
	public String getDescription() {
		return description;
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

	public String getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusText;
	}

	public String getType() {
		return type;
	}
	
	

	public void setDescription(String description) {
		this.description = description;
	}


	public void setId(String id) {
		this.id = id;
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


	public void setStatus(String status) {
		this.status = status;
	}


	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setFileMap(HashMap<String, VpmLinkedFile> fileMap) {
		this.fileMap = fileMap;
	}


	public void setProjectList(LinkedList<VpmProject> projectList) {
		this.projectList = projectList;
	}


	public void setWeightList(LinkedList<VpmWeight> weightList) {
		this.weightList = weightList;
	}


	public void setMaterialList(LinkedList<VpmMaterial> materialList) {
		this.materialList = materialList;
	}


	public LinkedList<VpmMaterial> getMaterialList() {
		if(materialList==null)materialList=new LinkedList<VpmMaterial>();
		return materialList;
	}

	public VpmPart(Element el){
		fileMap=new HashMap<String, VpmLinkedFile>();
		projectList=new LinkedList<VpmProject>();
		weightList=new LinkedList<VpmWeight>();
		materialList=new LinkedList<VpmMaterial>();
		
		init(el);
		
	}
	
	public VpmPart(){
		
		description="";
		id="";
		name="";
		owner="";
		revision="";
		status="";
		statusText="";
		type="";
		
		fileMap=new HashMap<String, VpmLinkedFile>();
		projectList=new LinkedList<VpmProject>();
		weightList=new LinkedList<VpmWeight>();
		materialList=new LinkedList<VpmMaterial>();
		
	}
	
	public VpmMaterial getMaterialWithTheMajorPercentage(){
		
		VpmMaterial mat=null;
		
		float anteil=0;
		
		for(VpmMaterial m:materialList){
			float currentAnteil=Float.parseFloat(m.getAnteil());
			
			if(currentAnteil>anteil){
				mat=m;
				anteil=currentAnteil;
			}
			
		}
		
		return mat;
		
		
	}
	
		

	public void init(Element el){
		
		//System.out.println(Root.getTagName());
		
		if(el.getTagName().equals(VpmPart.getTagName())){
		
			
			description=el.getAttribute("description");
			id=el.getAttribute("id");
			name=el.getAttribute("name");
			owner=el.getAttribute("owner");
			revision=el.getAttribute("revision");
			status=el.getAttribute("status");
			statusText=el.getAttribute("statusText");
			type=el.getAttribute("type");
			
			//if(name.length()>=40)System.out.println(this);
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					
					//initialize a vpm Part
					if(childElement.getTagName().equals(VpmLinkedFile.getTagName())){
						VpmLinkedFile f=new VpmLinkedFile(childElement);
						fileMap.put(f.getFormat(), f);
					}
					else if(childElement.getTagName().equals(VpmProject.getTagName())){
						VpmProject p=new VpmProject(childElement);
						projectList.add(p);
					}
					
					else if(childElement.getTagName().equals("gewichte")){
						
						NodeList Children2=childElement.getChildNodes();

						for(int j=0;j<Children2.getLength();j++){
							Node child2 = Children2.item(j);
							if(child2 instanceof Element){
								Element childElement2=(Element)child2;
								if(childElement2.getTagName().equals(VpmWeight.getTagName())){
									
									VpmWeight m=new VpmWeight(childElement2);
									weightList.add(m);
									
								}
								
							}
						}
					}
					
					else if(childElement.getTagName().equals("werkstoffe")){
						
						
						NodeList Children2=childElement.getChildNodes();

						for(int j=0;j<Children2.getLength();j++){
							Node child2 = Children2.item(j);
							if(child2 instanceof Element){
								Element childElement2=(Element)child2;
								if(childElement2.getTagName().equals(VpmMaterial.getTagName())){
									
									VpmMaterial m=new VpmMaterial(childElement2);
									
									materialList.add(m);
									
								}
								
							}
						}
					}
				
				
				}
			}
			
			
		}else{
			System.out.println("this is not a valid Vpm Part");
			
		}
		
		
		
		
	}
	
	/**
	 * add a material to the list
	 */
	public void addMaterial(VpmMaterial mat){
		if(mat!=null)this.getMaterialList().add(mat);
	}
	
	
	
	
	@Override
	public String toString() {
		return "VpmPart [description=" + description + ", id=" + id + ", name="
				+ name + ", owner=" + owner + ", revision=" + revision
				+ ", status=" + status + ", statusText=" + statusText
				+ ", type=" + type + ", fileMap=" + fileMap + ", projectList="
				+ projectList + ", weightList=" + weightList
				+ ", materialList=" + materialList + "]";
	}

	public String toCsvString() {
		return 	name.trim()+";"
				+getPartFileName().trim()+";"
				+owner.replaceAll(";", "_").trim()+";"
				+revision.replaceAll(";", "_").trim()+";"
				+statusText.replaceAll(";", "_").trim()+";"
				+getDescriptionIfSpecified().replaceAll(";", "_").trim()+";"
				+type.replaceAll(";", "_").trim();
	}

	
	/**
	 * return the description if specified
	 */
	private String getDescriptionIfSpecified(){
		if(description.contains("NOT SPECIFIED"))return "";
		
		return description;
	}
	
	/**
	 * this function return the file name of the part
	 */
	public String getPartFileName(){
		//HashMap<String, VpmLinkedFile> fileMap
		String fileName="";
		for(String key:fileMap.keySet()){
			fileName=fileMap.get(key).getName();
		}
		
		for(String key:fileMap.keySet()){
			if(!fileMap.get(key).getFormat().contains("CGR"))
				fileName=fileMap.get(key).getName();
		}
		
		return fileName;
		
		
	}
	
	public void addFile(VpmLinkedFile f){
		fileMap.put(f.getFormat(), f);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
