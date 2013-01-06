package de.femodeling.e4.client.model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.client.model.core.AssNodeClient;



public class AssemblyClientExport {
	
	private AssNodeClient root;
	private HashMap<String, PartClientImpl> parts;
	
	public AssemblyClientExport(AssNodeClient root, HashMap<String, PartClientImpl> parts){
		
		this.root=root;
		this.parts=parts;
		
		
	}
	
	/**
	 * export the Client Assembly into a Xml file
	 * this exported file will be used from ansa to import the structure there
	 * 
	 * @param filename
	 * @param filtered if true only the filtered elements will be exported
	 * @return
	 */
	public boolean exportToXml(String filename,boolean filtered){
		
		File f=new File(filename);
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try{
			
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.newDocument();
		doc.appendChild(toDomElement(doc,filtered));
		
		Transformer t=TransformerFactory.newInstance().newTransformer();
	
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(f)));
		
		
		return true;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	private Element toDomElement(Document doc,boolean filtered){
		
		Element e=doc.createElement("ExportCadStruktur");
		
		e.setAttribute("date", new Date().toString());
		
		
		//Export the parts
		//LinkedList<PartClientImpl> p_l=new LinkedList<PartClientImpl>();
		for(String key:parts.keySet()){
			
			boolean exportPart=false;
			PartClientImpl p=parts.get(key);
			
			if(filtered){
				if(p!=null && isPartFiltered(p, root)){
					exportPart=true;
				}
			}
			else if(p!=null){exportPart=true;}
			
			//export the part
			if(exportPart){
				e.appendChild(p.toDomElement(doc));
			}
			
			
		}
		
		
		//Export the structure
		AssNodeClient.setExportFilteredOnly(true);
		e.appendChild(root.toDomElement(doc));
		
		//reset the export Modus to all
		AssNodeClient.setExportFilteredOnly(false);
		

		return e;
	  }
	
	/**
	 * 
	 * test if the part will be exported or not
	 * @param p
	 * @param n
	 * @return
	 */
	private boolean isPartFiltered(PartClientImpl p,AssNodeClient n){
		if(n.getId().equals(p.getPartId()) && n.isFiltered())return true;
		
		for(AssNodeClient n_c:n.getChildNodes()){
			if(isPartFiltered(p,n_c))return true;
		}
		
		return false;
		
	}
	

}
