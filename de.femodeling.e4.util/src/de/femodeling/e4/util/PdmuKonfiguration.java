package de.femodeling.e4.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.core.part.Weight;
import de.femodeling.e4.model.dto.PartDTO;



public class PdmuKonfiguration {
	
	private File konfigurationFile=null;
	
	
	private class Stueckliste{
		
		public String id;
		public String zsb;
		public String level;
		public String cadID;
		
		
		@Override
		public String toString() {
			return "Stueckliste [id=" + id + ", zsb=" + zsb + ", level="
					+ level + ", cadID=" + cadID + "]";
		}
		
		
	}
	
	private class ProduktStruktur{
		
		public String id;
		public String type;
		public String name;
		public String beschreibung;
		public float weigth=-1;
		public String cadID;
		@Override
		public String toString() {
			return "ProduktStruktur [id=" + id + ", type=" + type + ", name="
					+ name + ", beschreibung=" + beschreibung + ", weigth="
					+ weigth + ", cadID=" + cadID + "]";
		}
		
		
		
	}
	
	
	private HashMap<String, Stueckliste> StuecklisteMap=new HashMap<String, Stueckliste>();
	private HashMap<String, ProduktStruktur> ProduktStrukturMap=new HashMap<String, ProduktStruktur>();
	private HashMap<String, Float> WeightMap=new HashMap<String, Float>();
	
	
	private HashMap<String, Float> caeIdWeightMap=new HashMap<String, Float>();
	private HashMap<String, Float> NameWeightMap=new HashMap<String, Float>();
	private HashMap<String, Float> AllNameWeightMap=new HashMap<String, Float>();
	
	
	public PdmuKonfiguration(String logFileXml,String weightTableCsv){
		
		//Read the log file
		konfigurationFile=new File(logFileXml);
		readXml();
		
		//Read the weight Table
		readWeightTable(weightTableCsv);
	}
	
	
	public Weight getWeigth(PartDTO part){
		
		Weight w=new Weight();
		w.setDate(new Date().toString());
		w.setEvaluation("EIV");
		w.setWeightTyp("Calculated");
		w.setValue("");
		
		
		if(part.getType()==Part.Type.PARTIAL_MODEL)return w;
		
		if(part.getName().matches(".*.S\\d") 
				|| part.getName().matches(".*_VTA")){
			//System.out.println(part.getName());
			return w;
		}
		if(part.getTypeText().equals("Teilstruktur-V5-fix"))return w;
		
		
		
		
		if(caeIdWeightMap.containsKey(part.getPartId())){
			w.setValue(String.valueOf(caeIdWeightMap.get(part.getPartId())));
			w.setWeightTyp("Cae Id");
			//System.out.println(part+", "+w);
			return w;
		}
		else{
			for(String snr:NameWeightMap.keySet()){
				String snr_modif=snr.replaceAll(" ", "_");
				
				if(part.getName().endsWith(snr_modif)){
					
					w.setValue(String.valueOf(NameWeightMap.get(snr)));
					w.setWeightTyp("Names");
					//System.out.println(part+", "+w);
					return w;
				}
				
				
			}
			
		}
		
		
		switch (part.getType()){
			//Search the weight for groups
			case GROUP:
				
				//Check the full name
				for(String snr:AllNameWeightMap.keySet()){
					String snr_modif=snr.replaceAll(" ", "_");
					
					//System.out.println(getPartName(part.getName()));	
					if(getPartName(part.getName()).equals(snr_modif)){
							
						w.setValue(String.valueOf(AllNameWeightMap.get(snr)));
						w.setWeightTyp("Extended Names");
						return w;
					}
						
				}
				
				//Check the snr only
				for(String snr:AllNameWeightMap.keySet()){
					String snr_modif=snr.replaceAll(" ", "_");
					
					
					if(getSnr(getPartName(part.getName())).equals(getSnr(snr_modif))){
							
						w.setValue(String.valueOf(AllNameWeightMap.get(snr)));
						w.setWeightTyp("Extended SNR");
						return w;
					}
						
				}
				
				break;
				
			case MODEL:
				
				//Check the full name
				for(String snr:AllNameWeightMap.keySet()){
					String snr_modif=snr.replaceAll(" ", "_");
					
					//System.out.println(getPartName(part.getName()));	
					if(getPartName(part.getName()).equals(snr_modif)){
							
						w.setValue(String.valueOf(AllNameWeightMap.get(snr)));
						w.setWeightTyp("Extended Names");
						return w;
					}
						
				}
				
				//Check the snr only
				for(String snr:AllNameWeightMap.keySet()){
					String snr_modif=snr.replaceAll(" ", "_");
					
					
					if(getSnr(getPartName(part.getName())).equals(getSnr(snr_modif))){
							
						w.setValue(String.valueOf(AllNameWeightMap.get(snr)));
						w.setWeightTyp("Extended SNR");
						return w;
					}
						
				}
				
				break;
				
				
				
		}
		
		
		
		
		return w;
		
		
	}
	
	private String getTagName(){return "konfiguration";}
	
	/**
	 * this method reads the project parameters from a xml file
	 * 
	 * @param f
	 */
	private boolean readXml(){
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(this.konfigurationFile);
			init(doc.getDocumentElement());
			
			
			mapData();
				
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	
	
	private void init(Element Root){
		
		//System.out.println(Root.getTagName());
		
		if(Root.getTagName().equals(getTagName())){
			
		
		NodeList Children=Root.getChildNodes();

		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				//initialize a vpm Part
				if(chilElement.getTagName().equals("stueckliste")){
					//VpmPart p=new VpmPart(chilElement);
					//vpmPartMap.put(p.getId(), p);
					readStueckliste(chilElement);
					
				}
				else if(chilElement.getTagName().equals("stammdaten")){
					//WorkingDir=text;
					//VpmNode n=new VpmNode(chilElement);
					//rootNode=n;
					readStammDaten(chilElement);
					
					
					
				}
								
			}
		}
		}else{
			System.out.println("this is not a valid Konfiguration file");
			
		}
		
		
	}
	
	
	private void readStammDaten(Element s){
		NodeList Children=s.getChildNodes();

		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				if(chilElement.getTagName().equals("produktstruktur")){
					
					readProduktstruktur(chilElement);
					
				}
				
				
			}
		}
	}
	
	
	private void readProduktstruktur(Element e){
		ProduktStruktur p=new ProduktStruktur();
		
		p.beschreibung=e.getAttribute("beschreibung");
		p.name=e.getAttribute("name");
		p.type=e.getAttribute("type");
		p.id=e.getAttribute("id");
		
		//System.out.println(p);
		
		ProduktStrukturMap.put(p.id, p);
		
	}
	
	/**
	 * read the stuecklist node
	 * 
	 * @param s_l
	 */
	private void readStueckliste(Element s_l){
		String level=s_l.getAttribute("level");
		String stamm_id=s_l.getAttribute("stammdatenID");
		String zsb=s_l.getAttribute("zsb");
		String cadID=s_l.getAttribute("cadID");
		
		Stueckliste sl=new Stueckliste();
		
		sl.id=stamm_id;
		sl.level=level;
		sl.zsb=zsb;
		sl.cadID=cadID;
		
		StuecklisteMap.put(stamm_id, sl);
		
		/*
		if(!sl.cadID.isEmpty())
			System.out.println(sl);
		*/
		
		NodeList Children=s_l.getChildNodes();

		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				//initialize a vpm Part
				if(chilElement.getTagName().equals("stueckliste")){
					readStueckliste(chilElement);
				}
			}
		}
	}
	
	
	/**
	 * output data
	 * 
	 */
	
	private void mapData(){
		
		for(String k:StuecklisteMap.keySet()){
			Stueckliste s =StuecklisteMap.get(k);
			//System.out.println(s);
				
			ProduktStruktur p=ProduktStrukturMap.get(s.id);
			p.cadID=s.cadID;
			
		}

	}
	
	
	/**
	 * read Weight Table
	 * 
	 */
	private void readWeightTable(String csvFile){
		
		BufferedReader lecteurAvecBuffer = null;
		String Line;
		
		try {

			lecteurAvecBuffer = new BufferedReader(new FileReader(csvFile));

			while ((Line = lecteurAvecBuffer.readLine()) != null) {
				
				String[] inputs=Line.split(";");
				
				if(inputs.length==3){
				
					String snr=inputs[0];
					int number=Integer.parseInt(inputs[1]);
					Float weigth=Float.parseFloat(inputs[2].replace(",", "."));
					if(number==0)number=1;
					weigth=weigth/number;
					
					
					
					while(snr.startsWith(" ")){
						snr=snr.substring(1);
					}
					
					
					if(weigth.toString().equals("NaN")){
						//System.out.println(snr+", "+String.valueOf(weigth));
						weigth=new Float(0);
					}
					
					//System.out.println(snr+", "+String.valueOf(weigth));
					
					
					WeightMap.put(snr, weigth);
				
				}		
			}			

			lecteurAvecBuffer.close();

		} catch (Exception exc) {
			System.out.println(exc);
			
		}
		
		
		//set the weigth to the produktstructure
		mapWeigthToProduktStruktur();
		
	}
	
	
	private void mapWeigthToProduktStruktur(){
		
		int counter_names=0;
		int counter_snr_index=0;
		int counter_snr=0;
		
		
		//System.out.println("Weigth Length: "+WeightMap.size());
		
		//Try to map using names
		for(String k:ProduktStrukturMap.keySet()){
			ProduktStruktur p=ProduktStrukturMap.get(k);
			if(p.name.endsWith("VVV"))continue;
			if(p.type.equals("Position-Zeichnung"))continue;
			
			Float weigth=WeightMap.get(p.name);
			
			if(weigth!=null){
				p.weigth=weigth;
				WeightMap.remove(p.name);
				counter_names++;
			}
			
		}
		
		
		//Try to map using SNRs and Index
		for(String k:ProduktStrukturMap.keySet()){
			ProduktStruktur p=ProduktStrukturMap.get(k);
			if(p.name.endsWith("VVV"))continue;
			if(p.type.equals("Position-Zeichnung"))continue;
			if(p.weigth>0)continue;
			
			String snr=getSnr(p.name);
			int index=getIndex(p.name);
			
			if(index<0)continue;
			
			String keyToDelete=null;
			
			for(String key:WeightMap.keySet()){
				String snr_key=getSnr(key);
				int index_key=getIndex(key);
				
				if(index_key<0)continue;
				
				if(snr.equals(snr_key) && index==index_key){
					Float weigth=WeightMap.get(key);
					p.weigth=weigth;
					keyToDelete=key;
					counter_snr_index++;
					break;
				}
				
			}
			
			//delete the found key
			if(keyToDelete!=null)WeightMap.remove(keyToDelete);
			
		}
		
		//Try to map using SNRs
		for(String k:ProduktStrukturMap.keySet()){
			ProduktStruktur p=ProduktStrukturMap.get(k);
			if(p.name.endsWith("VVV"))continue;
			if(p.type.equals("Position-Zeichnung"))continue;
			if(p.weigth>0)continue;
			
			String snr=getSnr(p.name);
			
			String keyToDelete=null;
			
			for(String key:WeightMap.keySet()){
				String snr_key=getSnr(key);
				
				
				
				if(snr.equals(snr_key)){
					Float weigth=WeightMap.get(key);
					p.weigth=weigth;
					keyToDelete=key;
					counter_snr++;
					
					break;
				}
			}
			
			//delete the found key
			if(keyToDelete!=null)WeightMap.remove(keyToDelete);
			
		}
		
		/*
		System.out.println("Counter names: "+counter_names);
		System.out.println("Counter snr && Index: "+counter_snr_index);
		System.out.println("Counter snr: "+counter_snr);
		
		System.out.println("Weigth Length: "+WeightMap.size());
		*/
		
		int counter=0;
		
		for(String k:ProduktStrukturMap.keySet()){
			ProduktStruktur p=ProduktStrukturMap.get(k);
			
			if(p.name.endsWith("VVV"))continue;
			if(p.type.contains("Position-Zeichnung"))continue;
			//if(p.type.contains("Variantenbaukasten"))continue;
			
			if(p.weigth<0)continue;
			
		
			if(p.cadID.isEmpty()){
				NameWeightMap.put(p.name,p.weigth);
			}
			else{
				caeIdWeightMap.put(p.cadID, p.weigth);
			}
			
			AllNameWeightMap.put(p.name, p.weigth);
			
			//System.out.println(p);
			counter++;
			
		}
		
		
		/*
		private HashMap<String, Float> caeIdWeightMap=new HashMap<String, Float>();
		private HashMap<String, Float> NameWeightMap=new HashMap<String, Float>();
		
		*/
		
		
		
		System.out.println("Counter: "+counter);
		
	}
	
	
	
	/*******************************
	 * 
	 *       TOOLS                 *
	 * 
	 ******************************/
	public static String getSnr(String name){
		if(name.length()>10)
			return name.substring(0, 11);
		
		else return name;
	}
	
	private static int getIndex(String name){
		int index=-1;
		
		if(name.length()>13){
			String index_s=name.substring(12, 14);
			//System.out.println(index_s);
			
			try{
			int test_index=Integer.parseInt(index_s);
			index=test_index;
			
			}
			catch (Exception exc) {
				//System.out.println(exc);
				index=-1;
			}
			
		}
		
		
		return index;
	}
	
	public static String getPartName(String name){
		
		if(name.length()>=14){
			int diff=name.length()-14;
			
			for(int i=0;i<=diff;i++){
			
			String tempSnr=name.substring(name.length()-14-i, name.length()-i);
			if(tempSnr.matches("(\\d{3}).(\\S{3}).(\\d{3}).(\\d{2})")){
				//System.out.println("Matches: "+tempSnr);
				return tempSnr+name.substring(name.length()-i, name.length());
			}
			
			}
		}
		
		if(name.length()>=11){
			int diff=name.length()-11;
			for(int i=0;i<=diff;i++){
			
				String tempSnr=name.substring(name.length()-11-i, name.length()-i);
				if(tempSnr.matches("(\\S{3}).(\\d{3}).(\\d{3})")){
					//System.out.println("Matches: "+tempSnr);
					return tempSnr+name.substring(name.length()-i, name.length());
				}
			}
		}
		
		//String points[]=name.split("\\.");
		
		
		//if(name.contains("."))
		String names[]=name.split("_");
		//else
		//	names=name.split("_");
		
		int snr_pos=0;
		for(int i=0;i<names.length;i++){
			if(names[i].replace(".", ",").split(",").length==4){
				snr_pos=i;
			}
		}
		
		//System.out.println(names[snr_pos]);
		
		String r_name="";
		
		for(int i=snr_pos;i<names.length;i++){
			if(snr_pos==names.length-1 || i==names.length-1){
				r_name=r_name+names[i];
			}
			else
				r_name=r_name+names[i]+"_";
			
		}
		
		r_name=r_name.split("-")[0];
		
		return r_name;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		System.out.println(getSnr("999.500.023.01"));
		System.out.println(getIndex("999.500.023.01"));
		*/
		//Log File Xml
		/*
		String logFile="/proj/DPT/DPT_991/001_TEST_MODULSTRUKTUR/Konfiguration_2012-06-19_190008991.014879.xml";
		
		//Gewichttabelle
		String weightTable="/proj/DPT/DPT_991/001_TEST_MODULSTRUKTUR/Gewichtstabelle.csv";
		
		PdmuKonfiguration k=new PdmuKonfiguration(logFile,weightTable);
		
		PartDTO p= new PartDTOImpl();
		p.setName("991.501.091_VTA");
		p.setType(Part.Type.MODEL);
		
		System.out.println(k.getWeigth(p));
		*/
		String tempSnr="derf_971.145.000.00";
		
		if(tempSnr.length()>=14){
			tempSnr=tempSnr.substring(tempSnr.length()-14, tempSnr.length());
			System.out.println("1 Matches: "+tempSnr);
			if(tempSnr.matches("(\\d{3}).(\\S{3}).(\\d{3}).(\\d{2})")){
				System.out.println("Matches: "+tempSnr);
				//return tempSnr;
			}
		}
		
	}

}





















