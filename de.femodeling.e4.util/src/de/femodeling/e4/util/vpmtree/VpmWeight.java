package de.femodeling.e4.util.vpmtree;

import org.w3c.dom.Element;

public class VpmWeight extends VpmXmlElement{
	
	private String date;
	private String evaluation;
	private String weightTyp;
	private String originSystem;
	private String value;
	
	public static String getTagName(){return "gewicht";}
	
	
	public VpmWeight(Element el){
		init(el);
	}
	
	public VpmWeight(String mat_in){
		String[] mats=mat_in.split(";",-1);
		if(mats.length>4){
			date=mats[3];
			weightTyp=mats[0];
			value=mats[1];
			originSystem=mats[4];
			evaluation=mats[2];
		}
		
	}
	
	public void init(Element el){
		
		if(el.getTagName().equals(VpmWeight.getTagName())){
			
			date=el.getAttribute("datum");
			evaluation=el.getAttribute("ermittlung");
			weightTyp=el.getAttribute("gewichtsArt");
			originSystem=el.getAttribute("quellsystem");
			value=el.getAttribute("wert");
			
			//System.out.println(this);
		}else{
			System.out.println("this is not a valid Vpm weigth element");
		}
	}


	@Override
	public String toString() {
		return "VpmWeight [date=" + date + ", evaluation=" + evaluation
				+ ", weightTyp=" + weightTyp + ", originSystem=" + originSystem
				+ ", value=" + value + "]";
	}


	public String getDate() {
		return date;
	}


	public String getEvaluation() {
		return evaluation;
	}


	public String getWeightTyp() {
		return weightTyp;
	}


	public String getOriginSystem() {
		return originSystem;
	}


	public String getValue() {
		return value;
	}
	
	
	
	

}
