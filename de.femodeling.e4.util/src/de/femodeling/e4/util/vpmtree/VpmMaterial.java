package de.femodeling.e4.util.vpmtree;

import org.w3c.dom.Element;

public class VpmMaterial extends VpmXmlElement{
	
	
	private String anteil;
	private String normbezeichnung;
	private String text="";
	private String werkstoff;
	
	
	public VpmMaterial(String mat_in){
		String[] mats=mat_in.split(";",-1);
		if(mats.length>2){
			anteil=mats[2];
			normbezeichnung=mats[0];
			werkstoff=mats[1];
			
		}
		
	}
	
	
	
	
	public static String getTagName(){return "zuordnung";}
	
	
	
	
	public String getAnteil() {
		return anteil;
	}




	public String getNormbezeichnung() {
		return normbezeichnung;
	}




	public String getText() {
		return text;
	}




	public String getWerkstoff() {
		
		return werkstoff;
		
	}




	public VpmMaterial(Element el){
		init(el);
	}
	
	public void init(Element el){
		
		if(el.getTagName().equals(VpmMaterial.getTagName())){
			
			anteil= String.valueOf((int) (Float.parseFloat(el.getAttribute("anteil"))*100));
			normbezeichnung=el.getAttribute("normbezeichnung");
			text=el.getAttribute("text");
			werkstoff=el.getAttribute("werkstoff");
			
			//System.out.println(this);
		}else{
			System.out.println("this is not a valid Vpm material element");
		}
	}


	@Override
	public String toString() {
		return "VpmMaterial [anteil=" + anteil + ", normbezeichnung="
				+ normbezeichnung + ", text=" + text + ", werkstoff="
				+ werkstoff + "]";
	}
	
	
	

}
