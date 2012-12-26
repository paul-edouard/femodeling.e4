package de.femodeling.e4.model.core.assembly;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.xml.XmlElementIF;



public class AssTmx implements XmlElementIF, Serializable{
	
	static final long serialVersionUID=1L;
	
	
	private static Logger logger = Logger.getLogger(AssTmx.class);
	
	
	private double x,xx,xy,xz;
	private double y,yx,yy,yz;
	private double z,zx,zy,zz;

	
	private Type type;
	
	public enum Type { ABS, REL}
	
	
	public AssTmx(Element el,Type type){
		this.type=type;
		x=0;xx=1;xy=0;xz=0;
		y=0;yx=0;yy=1;yz=0;
		z=0;zx=0;zy=0;zz=1;
		init(el);
	}
	
	public AssTmx(Type type){
		this.type=type;
		x=0;xx=1;xy=0;xz=0;
		y=0;yx=0;yy=1;yz=0;
		z=0;zx=0;zy=0;zz=1;
		
	}
	
	
	public String getTagName(){
		if(type==Type.ABS)
			return "absTmx";
		else
			return "relTmx";
	}
	
	/**
	 * cpmares two transformation matrix
	 * 
	 * @param tmx
	 * @param delta_pos
	 * @param delta_rot
	 * @return
	 */
	public boolean equals(AssTmx tmx,float delta_pos, float delta_rot){
		
		//System.out.println("Compare: "+this+"\n"+tmx);
		
		if(tmx==null)return false;
		
		if(Math.abs(x-tmx.x)>delta_pos)return false;
		if(Math.abs(y-tmx.y)>delta_pos)return false;
		if(Math.abs(z-tmx.z)>delta_pos)return false;
			
		if(Math.abs(xx-tmx.xx)>delta_rot)return false;
		if(Math.abs(xy-tmx.xy)>delta_rot)return false;
		if(Math.abs(xz-tmx.xz)>delta_rot)return false;
		
		if(Math.abs(yx-tmx.yx)>delta_rot)return false;
		if(Math.abs(yy-tmx.yy)>delta_rot)return false;
		if(Math.abs(yz-tmx.yz)>delta_rot)return false;
		
		if(Math.abs(zx-tmx.zx)>delta_rot)return false;
		if(Math.abs(zy-tmx.zy)>delta_rot)return false;
		if(Math.abs(zz-tmx.zz)>delta_rot)return false;
		
		//System.out.println("Compare: OK");
		
		return true;	
	}
	
	
	public boolean equals(AssTmx tmx){
		return  equals(tmx,0.01f,0.0001f);
	}
	
	
	public void setX(double x) {
		this.x = x;
	}

	public void setXx(double xx) {
		this.xx = xx;
	}

	public void setXy(double xy) {
		this.xy = xy;
	}

	public void setXz(double xz) {
		this.xz = xz;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setYx(double yx) {
		this.yx = yx;
	}

	public void setYy(double yy) {
		this.yy = yy;
	}

	public void setYz(double yz) {
		this.yz = yz;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setZx(double zx) {
		this.zx = zx;
	}

	public void setZy(double zy) {
		this.zy = zy;
	}

	public void setZz(double zz) {
		this.zz = zz;
	}
	
	

	public double getX() {
		return x;
	}

	public double getXx() {
		return xx;
	}

	public double getXy() {
		return xy;
	}

	public double getXz() {
		return xz;
	}

	public double getY() {
		return y;
	}

	public double getYx() {
		return yx;
	}

	public double getYy() {
		return yy;
	}

	public double getYz() {
		return yz;
	}

	public double getZ() {
		return z;
	}

	public double getZx() {
		return zx;
	}

	public double getZy() {
		return zy;
	}

	public double getZz() {
		return zz;
	}

	@Override
	public String toString() {
		return "AssTmx [x=" + x + ", xx=" + xx + ", xy=" + xy + ", xz=" + xz
				+ ", y=" + y + ", yx=" + yx + ", yy=" + yy + ", yz=" + yz
				+ ", z=" + z + ", zx=" + zx + ", zy=" + zy + ", zz=" + zz
				+ ", type=" + type + "]";
	}

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			x=Double.parseDouble(el.getAttribute("x"));
			xx=Double.parseDouble(el.getAttribute("xx"));
			xy=Double.parseDouble(el.getAttribute("xy"));
			xz=Double.parseDouble(el.getAttribute("xz"));
			
			y=Double.parseDouble(el.getAttribute("y"));
			yx=Double.parseDouble(el.getAttribute("yx"));
			yy=Double.parseDouble(el.getAttribute("yy"));
			yz=Double.parseDouble(el.getAttribute("yz"));
			
			z=Double.parseDouble(el.getAttribute("z"));
			zx=Double.parseDouble(el.getAttribute("zx"));
			zy=Double.parseDouble(el.getAttribute("zy"));
			zz=Double.parseDouble(el.getAttribute("zz"));			
			
			
			
		}else{
			logger.warn("this is not a valid pmc tmx element");
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
		
		e.setAttribute("x", String.valueOf(this.x));
		e.setAttribute("xx", String.valueOf(this.xx));
		e.setAttribute("xy", String.valueOf(this.xy));
		e.setAttribute("xz", String.valueOf(this.xz));
		
		e.setAttribute("y", String.valueOf(this.y));
		e.setAttribute("yx", String.valueOf(this.yx));
		e.setAttribute("yy", String.valueOf(this.yy));
		e.setAttribute("yz", String.valueOf(this.yz));
		
		e.setAttribute("z", String.valueOf(this.z));
		e.setAttribute("zx", String.valueOf(this.zx));
		e.setAttribute("zy", String.valueOf(this.zy));
		e.setAttribute("zz", String.valueOf(this.zz));
		

		return e;
	}
	

}
