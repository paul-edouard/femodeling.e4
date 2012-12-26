package de.femodeling.e4.model.core.connection;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.model.xml.XmlElementIF;

public class ConnectionSupportPoint implements XmlElementIF, Serializable{
	
	static final long serialVersionUID=1L;
	
	
	static final String Point_X="Point X";
	static final String Point_Y="Point Y";
	static final String Point_Z="Point Z";
	static final String Normal_X="Normal X";
	static final String Normal_Y="Normal Y";
	static final String Normal_Z="Normal Z";
	static final String Tangent_X="Tangent X";
	static final String Tangent_Y="Tangent Y";
	static final String Tangent_Z="Tangent Z";
	
	
	private static Logger logger = Logger.getLogger(ConnectionSupportPoint.class);
	
	private double[] point=new double[3];
	private double[] normal=new double[3];
	private double[] tangent=new double[3];
	public double[] getPoint() {
		return point;
	}
	public void setPoint(double[] point) {
		this.point = point;
	}
	public double[] getNormal() {
		return normal;
	}
	public void setNormal(double[] normal) {
		this.normal = normal;
	}
	public double[] getTangent() {
		return tangent;
	}
	public void setTangent(double[] tangent) {
		this.tangent = tangent;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VtaSupportPoint [point=" + Arrays.toString(point) + ", normal="
				+ Arrays.toString(normal) + ", tangent="
				+ Arrays.toString(tangent) + "]";
	}
	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "SupportPoint";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			NodeList Children=el.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element chilElement=(Element)child;
					
					if(chilElement.getTagName().equals("SupportPointParameter")){
						String attr=chilElement.getAttribute("Name");
						
						if(attr.equals(Point_X))this.point[0]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						else if(attr.equals(Point_Y))this.point[1]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						else if(attr.equals(Point_Z))this.point[2]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						
						else if(attr.equals(Normal_X))this.normal[0]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						else if(attr.equals(Normal_Y))this.normal[1]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						else if(attr.equals(Normal_Z))this.normal[2]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						
						else if(attr.equals(Tangent_X))this.tangent[0]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						else if(attr.equals(Tangent_Y))this.tangent[1]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						else if(attr.equals(Tangent_Z))this.tangent[2]=Double.parseDouble(chilElement.getTextContent().replaceAll(",", "."));
						
						
					}
				}
			}
			
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
		
		//point
		Element e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Point_X);
		e_c.setTextContent(String.valueOf(this.point[0]));
		e.appendChild(e_c);
		
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Point_Y);
		e_c.setTextContent(String.valueOf(this.point[1]));
		e.appendChild(e_c);
		
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Point_Z);
		e_c.setTextContent(String.valueOf(this.point[2]));
		e.appendChild(e_c);
		
		//normal
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Normal_X);
		e_c.setTextContent(String.valueOf(this.normal[0]));
		e.appendChild(e_c);
		
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Normal_Y);
		e_c.setTextContent(String.valueOf(this.normal[1]));
		e.appendChild(e_c);
		
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Normal_Z);
		e_c.setTextContent(String.valueOf(this.normal[2]));
		e.appendChild(e_c);
		
		
		//tangent
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Tangent_X);
		e_c.setTextContent(String.valueOf(this.tangent[0]));
		e.appendChild(e_c);
				
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Tangent_Y);
		e_c.setTextContent(String.valueOf(this.tangent[1]));
		e.appendChild(e_c);
				
		e_c=Doc.createElement("SupportPointParameter");
		e_c.setAttribute("Name", Tangent_Z);
		e_c.setTextContent(String.valueOf(this.tangent[2]));
		e.appendChild(e_c);
		
		
		
		return e;
	}
	

}
