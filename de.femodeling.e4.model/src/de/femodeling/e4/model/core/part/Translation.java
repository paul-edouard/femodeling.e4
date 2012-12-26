package de.femodeling.e4.model.core.part;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.xml.XmlElementIF;

public class Translation implements XmlElementIF,Serializable{
	
	private static Logger logger = Logger.getLogger(Translation.class);
	
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    public enum State { TRANSLATED , NO_TRANSLATED/*, KILLED*/, FAILED,WAITING}
    
    public static final String TRANSLATION_FILE="original.ansa";
    
    protected String creation;
	protected String ansaVersion;
	protected String ansaFile;
	protected int representationId;
	protected String lib;
	protected String log;
	protected State state;
	protected String error;
	protected long translationTime;
	
	
	
	
	
	public String getError() {
		return error;
	}




	public void addError(String error) {
		if(this.error.isEmpty())
		this.error += error;
		else this.error +=", "+ error;
	}




	public State getState() {
		return state;
	}
	
	
	
	
	public String getAnsaFile() {
		return ansaFile;
	}




	public void setAnsaFile(String ansaFile) {
		this.ansaFile = ansaFile;
	}




	public long getTranslationTime() {
		return translationTime;
	}


	public void setTranslationTime(long translationTime) {
		this.translationTime = translationTime;
	}



	public void setState(State state) {
		this.state = state;
	}
	
	public Translation(){
		this.state=State.NO_TRANSLATED;
		ansaVersion=lib=log="";
		representationId=-1;
		translationTime=0;
		error="";
		
		creation=new Date().toString();
	}
	
	public String getAnsaVersion() {
		return ansaVersion;
	}
	
	public void registerCreation(){
		creation=new Date().toString();
	}
	
	

	public String getCreation() {
		return creation;
	}

	public void setAnsaVersion(String ansaVersion) {
		this.ansaVersion = ansaVersion;
	}

	public int getRepresentationId() {
		return representationId;
	}

	public void setRepresentationId(int representationId) {
		this.representationId = representationId;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
	public String getLib() {
		return lib;
	}

	public void setLib(String lib) {
		this.lib = lib;
	}
	
	

	@Override
	public String toString() {
		return "Translation [date=" + creation + ", ansaVersion=" + ansaVersion
				+ ", representationId=" + representationId + ", log=" + log
				+ "]";
	}
	
	public String stateToString(){
		if(state==null)return  "NO_TRANSLATED";
		
		switch(state){
			case TRANSLATED:return "TRANSLATED";
			case NO_TRANSLATED:return "NO_TRANSLATED";
			case WAITING:return "WAITING";
			case FAILED:return "FAILED";
			default: return "NO_TRANSLATED";
		}
	}
	
	
	public void stringToState(String str){
		if(str==null || str.isEmpty())return;
		
		if(str.equals("TRANSLATED"))
			state=State.TRANSLATED;
		else if(str.equals("NO_TRANSLATED"))
			state=State.NO_TRANSLATED;
		else if(str.equals("WAITING"))
			state=State.WAITING;
		else if(str.equals("FAILED"))
			state=State.FAILED;
	}
	
	
	public Translation createCopy(){
		Translation copy=new Translation();
		copy.ansaVersion=this.ansaVersion;
		copy.creation=this.creation;
		copy.lib=this.lib;
		copy.log=this.log;
		copy.representationId=this.representationId;
		copy.state=this.state;
		copy.translationTime=this.translationTime;
		
		return copy;
	}

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "Translation";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			creation=el.getAttribute("Creation");
			error=el.getAttribute("Error");
			ansaVersion=el.getAttribute("AnsaVersion");
			representationId=Integer.parseInt(el.getAttribute("RepresentationId"));
			translationTime=Long.parseLong(el.getAttribute("TranslationTime"));
			stringToState(el.getAttribute("State"));
			log=el.getTextContent();
			
		}else{
			logger.warn("this is not a valid "+this.getTagName()+ "element");
		}
	}	
	
	
	
	/***********************************
	 *                                 *
	 *	    RETURN AS ELEMENT          *
	 *                                 *
	 ***********************************/	
	
	
	public Element toDomElement(Document doc){
		Document Doc=doc;
		Element e=Doc.createElement(this.getTagName());
		
		e.setAttribute("Creation", this.creation);
		e.setAttribute("Error", this.error);
		e.setAttribute("AnsaVersion", this.ansaVersion);
		e.setAttribute("RepresentationId", String.valueOf(this.representationId));
		e.setAttribute("TranslationTime", String.valueOf(this.translationTime));
		e.setAttribute("State", this.stateToString());
		e.setTextContent(this.log);
		
		return e;
	}
    
	
}
