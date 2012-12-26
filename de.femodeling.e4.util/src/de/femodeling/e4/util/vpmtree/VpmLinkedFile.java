package de.femodeling.e4.util.vpmtree;

import org.w3c.dom.Element;

public class VpmLinkedFile extends VpmXmlElement{
	
	private String exported;
	private String format;
	private String name;
	private String path;
	private String size;
	
	public static String getTagName(){return "file";}
	
	public String getFormat() {
		return format;
	}


	public VpmLinkedFile(Element el){
		init(el);
	}
	
	public VpmLinkedFile(String exported,String format,String name,String path,String size){
		this.exported=exported;
		this.format=format;
		this.name=name;
		this.path=path;
		this.size=size;
	}
	
	
	
	
	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getSize() {
		return size;
	}

	public void init(Element el){
		
		if(el.getTagName().equals("file")){
		
			exported=el.getAttribute("exported");
			format=el.getAttribute("format");
			name=el.getAttribute("name");
			path=el.getAttribute("path");
			size=el.getAttribute("size");
			
			//System.out.println(this);

		}else{
			System.out.println("this is not a valid Vpm file element");
			
		}
	}


	@Override
	public String toString() {
		return "LinkedFile [exported=" + exported + ", format=" + format
				+ ", name=" + name + ", path=" + path + ", size=" + size
				+ "]";
	}
	
	
	
}
