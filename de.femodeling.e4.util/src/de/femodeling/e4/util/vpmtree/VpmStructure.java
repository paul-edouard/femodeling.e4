package de.femodeling.e4.util.vpmtree;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.assembly.AssEdge;
import de.femodeling.e4.model.core.assembly.AssNode;
import de.femodeling.e4.model.dto.PartDTO;

/*
import de.porsche.femodeling.core.assembly.part.PmcPart;
*/


public class VpmStructure extends VpmXmlElement {
	
	private static Logger logger = Logger.getLogger(VpmStructure.class);

	
	private File structureFile=null;
	
	//private String date;
	//private String dateText;
	
	private HashMap<String, VpmPart> vpmPartMap;
	private VpmNode rootNode;
	
	
	
	public HashMap<String, VpmPart> getVpmPartMap() {
		return vpmPartMap;
	}

	public VpmNode getRootNode() {
		return rootNode;
	}

	public static String getTagName(){return "ExportCadStruktur";}
	
	
	
	public VpmStructure(File structureFile) {
		this.structureFile = structureFile;
		vpmPartMap=new HashMap<String, VpmPart>();
		rootNode=null;
	}
	
	public void setStructureFile(File structureFile) {
		this.structureFile = structureFile;
	}

	public List<PartDTO> convertToParts(){
		
		List<PartDTO> p_l=new LinkedList<PartDTO>();
		
		for(String key:vpmPartMap.keySet()){
			VpmPart vpart=vpmPartMap.get(key);
			p_l.add(vpart.convertToPart());
		}
		
		return p_l;
		
	}
	
	public void transmitMaterialFromParentToChild(){
		this.getRootNode().transmitMaterialsToChild(this.getVpmPartMap(), null);
	}
	
	/**
	 * return the fe modules only
	 * 
	 * @return
	 */
	public List<PartDTO> getFeModules(){
		List<PartDTO> p_l=new LinkedList<PartDTO>();
		
		for(String key:vpmPartMap.keySet()){
			VpmPart vpart=vpmPartMap.get(key);
			if(vpart.isFeModule()){
				PartDTO p_dto=vpart.convertToPart();	
				p_dto.setName(vpart.getModuleId());
				p_dto.setDescription(vpart.getModuleName());
				p_dto.setAnsaModuleId(vpart.getModuleId());
				p_dto.setPartId(vpart.getModuleId());
				p_dto.setOwner("FE");
				p_l.add(p_dto);
					
			}
		}
		
		return p_l;
	}
	
	public AssNode getFeModuleStruc(){
		
		return rootNode.toFeModuleNode(vpmPartMap);
		
	}

	public void init(Element Root){
		
		//System.out.println(Root.getTagName());
		
		if(Root.getTagName().equals(VpmStructure.getTagName())){
			
		//date=Root.getAttribute("date");
		//dateText=Root.getAttribute("dateText");
		
		NodeList Children=Root.getChildNodes();

		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				//initialize a vpm Part
				if(chilElement.getTagName().equals(VpmPart.getTagName())){
					VpmPart p=new VpmPart(chilElement);
					vpmPartMap.put(p.getId(), p);
				}
				else if(chilElement.getTagName().equals(VpmNode.getTagName())){
					//WorkingDir=text;
					VpmNode n=new VpmNode(chilElement);
					rootNode=n;
				}
								
			}
		}
		}else{
			System.out.println("this is not a valid Vpm Tree");
			
		}
		
		
	}
	
	
	//public 
	
		
	/**
	 * this method reads the project parameters from a xml file
	 * 
	 * @param f
	 */
	public boolean readXml(){
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(this.structureFile);
			init(doc.getDocumentElement());
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * this method reads a csv file for the creation of the VpmStructure 
	 * 
	 * @return
	 */
	public boolean readCsv(){
		
		BufferedReader lecteurAvecBuffer = null;
		String Line;
		
		HashMap<Integer, VpmNode> lastParent=new HashMap<Integer, VpmNode>();
		
		try {

			lecteurAvecBuffer = new BufferedReader(new FileReader(this.structureFile));

			while ((Line = lecteurAvecBuffer.readLine()) != null) {
				
				if(!Line.startsWith("#")){
				
				String[] inputs=Line.split("\\|",-1);
				
				
				int level=CsvStructure.getLevel(inputs);
				VpmPart p=CsvStructure.getPart(inputs);
				VpmEdge e=CsvStructure.getEdge(inputs);
				VpmNode n=CsvStructure.getNode(inputs);
				
				//System.out.println(p);
				
				//Test if it is a module:
				if(p.isFeModule()){
					System.out.println(p);
					System.out.println(p.getModuleId());
					System.out.println(p.getModuleName());
				}
				
				//create the structure
				if(level==0){
					rootNode=n;
				}
				else if(lastParent.containsKey(level-1)){
					VpmNode parent=lastParent.get(level-1);
					if(parent!=null){
						parent.addEdge(e);
						e.setNode(n);
					}
					
				}
				
				//create the parts
				if(!vpmPartMap.containsKey(p.getId())){
					vpmPartMap.put(p.getId(), p);
				}
				
				lastParent.put(level, n);
				
				}
				
			}
			
			
			lecteurAvecBuffer.close();
			
			return true;
			
		
		} catch (Exception exc) {
			System.out.println(exc);
			return false;
		}
		
	}
	
	
	public static boolean writeInFile(String outputFileName,String content){
		//Try to open the connection File to write in
		File f=new File(outputFileName); 
		
		try{
			FileWriter output = new FileWriter(f);
			output.write(content);
			output.close();
			return true;
		}
		catch(Exception exc){
			System.out.println(exc);
			return false;
		}
		
	}
	
	
	public String searchModuleId(AssEdge edge){
		String moduleId="";
		return recursiveModuleIdSearch(edge,rootNode,moduleId);
	}
	
	private String recursiveModuleIdSearch(AssEdge edge,VpmNode n,String moduleId){
		
		VpmPart p=vpmPartMap.get(n.getId());
		if(p!=null && p.isFeModule())moduleId=p.getModuleId();
		
		for(VpmEdge e:n.getEdgeList()){
			if(e==null)continue;
			
			if(e.getId().equals(edge.getId())/* && 
					e.toAssEdge().getAbsTmx().equals(edge.getAbsTmx()) */){
				logger.info("2 Current Edge: "+edge.getId()+" compare to: "+e.getId()+"Module Id: "+moduleId);
				
				return moduleId;
			}
			
			if(e.getNode()==null)continue;
			
			VpmPart p_c=vpmPartMap.get(e.getNode().getId());
			if(p_c==null)continue;
				
			//if(p_c.isFeModule()){
				String r_id=recursiveModuleIdSearch(edge,e.getNode(),moduleId);
				if(!r_id.isEmpty())return r_id;
			//}
			
		}
		
		return "";
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(!(args.length==1 || args.length==2)){
			System.out.println("the function takes 1 or 2 arguments: XmlFileName, target directory");
			//System.out.println("Modus={PartInfo,IdCheck,MatInfo}");
			return ;
		}
		
		String targetDir="";
		String xmlFileName=args[0];
		//String modus=args[1];
		
		if(args.length==2){
			targetDir=args[1];
		}
		
		
		//check the first arguments
		File f=new File(xmlFileName);
		VpmStructure s=new VpmStructure(f);
		if(!s.readXml()){
			System.out.println("Cannot read the Xml file!");return;
		}
		
		//check the second arguments
		/*
		if(!(modus.equals("PartInfo") || modus.equals("IdCheck") || modus.equals("MatInfo"))){
			System.out.println("Cannot read the second argument");
			System.out.println("Modus={PartInfo,IdCheck,MatInfo}");
			return;
		}
		*/
		
		
		s.getRootNode().transmitMaterialsToChild(s.getVpmPartMap(), null);
		
		
		HashMap<String, VpmPart> partMap=s.getVpmPartMap();
		HashSet<String> shortedKeySet=new HashSet<String>();
		
		
		String content_PartInfo="";
		String content_IdCheck="";
		String content_MatInfo="";
		
		for(String key: partMap.keySet()){
			
			VpmPart part=partMap.get(key);
			
			if(part.getPartFileName().endsWith(".model") || part.getPartFileName().endsWith(".CATPart")){	
					//System.out.println(part.getPartFileName());
					
					//create the PartInfo content		
					
					content_PartInfo+=part.toCsvString()+"\n";
					
					
					//test if the there are conflicts with module ids
					String shortedKey=part.getName();
					if(shortedKey.length()>39)shortedKey=part.getName().substring(0, 38);
						
					if(!shortedKeySet.add(shortedKey)){
						content_IdCheck+="Allready used key:"+ shortedKey+" Part: " + part.getName()+"\n";
					}
					
					
					//create the MatInfo content
					if( part.getMaterialList().size()>0){
						if(part.getMaterialWithTheMajorPercentage()!=null){
							content_MatInfo+=part.getPartFileName()+";"+"All;"+
												part.getMaterialWithTheMajorPercentage().getWerkstoff()+";"+
												part.getMaterialWithTheMajorPercentage().getNormbezeichnung()+"\n";
							
							}
					}
				
			}
			
			
		}
		
		
		//create the export file name;
		String exportFileName_PartInfo="";
		String exportFileName_IdCheck="";
		String exportFileName_MatInfo="";
		
		if(f.getParent()==null){
			exportFileName_PartInfo="PartInfo.csv";
			exportFileName_IdCheck="IdCheck.csv";
			exportFileName_MatInfo="MatInfo.csv";
		}
		else{
				exportFileName_PartInfo=f.getParent()+File.separator+"PartInfo.csv";
				exportFileName_IdCheck=f.getParent()+File.separator+"IdCheck.csv";
				exportFileName_MatInfo=f.getParent()+File.separator+"MatInfo.csv";
			
		}
		
		
		if(args.length==2){
			exportFileName_PartInfo=targetDir+File.separator+"PartInfo.csv";
			exportFileName_IdCheck=targetDir+File.separator+"IdCheck.csv";
			exportFileName_MatInfo=targetDir+File.separator+"MatInfo.csv";
		}
		
		//Write the part Info file
		if(!writeInFile(exportFileName_PartInfo,content_PartInfo)){
			System.out.println("Cannot write the file:"+exportFileName_PartInfo);
		}
		else{
			System.out.println(exportFileName_PartInfo+" created with success");
		}
		
		//write the IdCheck info
		if(!writeInFile(exportFileName_IdCheck,content_IdCheck)){
			System.out.println("Cannot write the file:"+exportFileName_IdCheck);
		}
		else{
			System.out.println(exportFileName_IdCheck+" created with success");
		}
		
		
		//write the Mat info file
		if(!writeInFile(exportFileName_MatInfo,content_MatInfo)){
			System.out.println("Cannot write the file:"+exportFileName_MatInfo);
		}
		else{
			System.out.println(exportFileName_MatInfo+" created with success");
		}
		
		

	}

}
