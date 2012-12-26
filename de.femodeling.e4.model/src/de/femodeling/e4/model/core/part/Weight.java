package de.femodeling.e4.model.core.part;

import java.io.Serializable;

/**
 * @author Paul
 *
 */
public class Weight implements Serializable{
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
	
    static final String DateStr="date";
	static final String EvaluationStr="evaluation";
	static final String WeightTypStr="weight_typ";
	static final String ValueStr="value";
	static final String OriginSystemStr="origin_system";
    
    
	private String date;
	private String evaluation;
	private String weightTyp;
	private String originSystem;
	private String value;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getWeightTyp() {
		return weightTyp;
	}
	public void setWeightTyp(String weightTyp) {
		this.weightTyp = weightTyp;
	}
	public String getOriginSystem() {
		return originSystem;
	}
	public void setOriginSystem(String originSystem) {
		this.originSystem = originSystem;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static String getTagName(){
		return "weigth";
	}
	@Override
	public String toString() {
		return "Weight [date=" + date + ", evaluation=" + evaluation
				+ ", weightTyp=" + weightTyp + ", originSystem=" + originSystem
				+ ", value=" + value + "]";
	}
	
	public Weight createCopy(){
		Weight copy=new Weight();
		copy.date=this.date;
		copy.evaluation=this.evaluation;
		copy.originSystem=this.originSystem;
		copy.value=this.value;
		copy.weightTyp=this.weightTyp;
		
		return copy;
		
	}
	
	

}
