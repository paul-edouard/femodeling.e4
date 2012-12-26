package de.femodeling.e4.util.vpmtree;

import org.w3c.dom.Element;

public class VpmProject extends VpmXmlElement{
	
	String name;
	
	
	public static String getTagName(){return "project";}
	
	public VpmProject(String name){
		this.name=name;
	}
	
	
	public VpmProject(Element el){
		init(el);
	}
	
	public void init(Element el){
		
		if(el.getTagName().equals(VpmProject.getTagName())){
			name=el.getAttribute("name");
			//System.out.println(this);
		}else{
			System.out.println("this is not a valid Vpm project element");
		}
	}

	@Override
	public String toString() {
		return "Project [name=" + name + "]";
	}

}
