package de.femodeling.e4.server.internal.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.core.connection.ConnectionElement;
import de.femodeling.e4.model.core.connection.ConnectionParameter;
import de.femodeling.e4.model.core.connection.ConnectionPart;
import de.femodeling.e4.model.core.connection.ConnectionSupportPoint;
import de.femodeling.e4.model.core.connection.ConnectionZSB;
import de.femodeling.e4.model.xml.XmlElementIF;


public class ConnectionElementServerImpl extends ConnectionElement implements XmlElementIF{
	
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    private static Logger logger = Logger.getLogger(ConnectionElementServerImpl.class);
	
    
    public String getXmlFileName(){
		return this.getParamValue(ConnectionParameter.Element).replace(" ", "_")+".xml";
	}
    
    
    /***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "Element";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals("Parameters")){
						readParameters(chilElement);
					}
					else if(chilElement.getTagName().equals("Parts")){
						readParts(chilElement);
					}
					else if(chilElement.getTagName().equals("ZSBs")){
						readZsbs(chilElement);
					}
					else if(chilElement.getTagName().equals("SupportPoints")){
						readSupportPoints(chilElement);
					}
				}
			}
			
		}else{
			logger.warn("this is not a valid "+this.getTagName()+ "element");
		}
	}
	
	
	private void readParameters(Element el){
		NodeList Children=el.getChildNodes();
		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				
				if(chilElement.getTagName().equals("Parameter")){
					ConnectionParameter param=new ConnectionParameter();
					param.init(chilElement);
					this.getParameterList().add(param);
					
				}
				
			}
		}
		
	}
	
	
	private void readParts(Element el){
		NodeList Children=el.getChildNodes();
		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				
				if(chilElement.getTagName().equals("Part")){
					ConnectionPart part=new ConnectionPart();
					part.init(chilElement);
					this.getPartMap().put(part.getIdx(), part);
					
				}
				
			}
		}
	}
	
	private void readZsbs(Element el){
		NodeList Children=el.getChildNodes();
		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				
				if(chilElement.getTagName().equals("ZSB")){
					ConnectionZSB e=new ConnectionZSB();
					e.init(chilElement);
					this.getZsbMap().put(e.getIdx(), e);
					
				}
				
			}
		}
	}
	
	private void readSupportPoints(Element el){
	//	System.out.println("In Support Points!");
		
		NodeList Children=el.getChildNodes();
		for(int i=0;i<Children.getLength();i++){
			Node child = Children.item(i);
			if(child instanceof Element){
				Element chilElement=(Element)child;
				
				if(chilElement.getTagName().equals("SupportPoint")){
	//				System.out.println("In Support Point!");
					ConnectionSupportPoint e=new ConnectionSupportPoint();
					e.init(chilElement);
					this.getSupportPointList().add(e);
					
				}
				
			}
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
		
		//Parameters
		Element e_c=Doc.createElement("Parameters");
		e.appendChild(e_c);
		for(ConnectionParameter param:this.getParameterList()){
			Element e_param=param.toDomElement(doc);
			e_c.appendChild(e_param);
		}
		
		//Parts
		e_c=Doc.createElement("Parts");
		e.appendChild(e_c);
		for(String key:this.getPartMap().keySet()){
			ConnectionPart part=this.getPartMap().get(key);
			Element e_part=part.toDomElement(doc);
			e_c.appendChild(e_part);
		}
		
		//ZSBs
		e_c=Doc.createElement("ZSBs");
		e.appendChild(e_c);
		for(String key:this.getZsbMap().keySet()){
			ConnectionZSB zsb=this.getZsbMap().get(key);
			Element e_zsb=zsb.toDomElement(doc);
			e_c.appendChild(e_zsb);
		}
		
		//ZSBs
		e_c=Doc.createElement("SupportPoints");
		e.appendChild(e_c);
		for(ConnectionSupportPoint point:this.getSupportPointList()){
			Element e_point=point.toDomElement(doc);
			e_c.appendChild(e_point);
		}
		
		
		
		return e;
	}
    
    
}
