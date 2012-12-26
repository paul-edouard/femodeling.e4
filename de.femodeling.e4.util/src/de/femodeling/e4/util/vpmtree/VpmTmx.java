package de.femodeling.e4.util.vpmtree;

import org.w3c.dom.Element;

import de.femodeling.e4.model.core.assembly.AssTmx;



public class VpmTmx extends VpmXmlElement{
	
	private String x,xx,xy,xz;
	private String y,yx,yy,yz;
	private String z,zx,zy,zz;
	
	private String type;
	//public static String getTagName(){return "zuordnung";}
	
	
	public VpmTmx(Element el){
		init(el);
	}
	
	public VpmTmx(String[] values,String type){
		
		this.type=type;
		
		if(values.length!=12)return;
		
		this.xx=values[0];
		this.xy=values[1];
		this.xz=values[2];
		
		this.yx=values[3];
		this.yy=values[4];
		this.yz=values[5];
		
		this.zx=values[6];
		this.zy=values[7];
		this.zz=values[8];
		
		this.x=values[9];
		this.y=values[10];
		this.z=values[11];
	}
	
	public void init(Element el){
		
		if(el.getTagName().equals("absTmx") || el.getTagName().equals("relTmx")){
			
			type=el.getTagName();
			
			x=el.getAttribute("x");
			xx=el.getAttribute("xx");
			xy=el.getAttribute("xy");
			xz=el.getAttribute("xz");
			
			y=el.getAttribute("y");
			yx=el.getAttribute("yx");
			yy=el.getAttribute("yy");
			yz=el.getAttribute("yz");
			
			z=el.getAttribute("z");
			zx=el.getAttribute("zx");
			zy=el.getAttribute("zy");
			zz=el.getAttribute("zz");
			
			//System.out.println(this);
		}else{
			System.out.println("this is not a valid Vpm material element");
		}
	}
	
	public AssTmx toAssTmx(){
		AssTmx t;
		if(type.equals("absTmx"))
			t=new AssTmx(AssTmx.Type.ABS);
		else
			t=new AssTmx(AssTmx.Type.REL);
		
		try{
		t.setX(Double.parseDouble(x));
		t.setXx(Double.parseDouble(xx));
		t.setXy(Double.parseDouble(xy));
		t.setXz(Double.parseDouble(xz));
		
		t.setY(Double.parseDouble(y));
		t.setYx(Double.parseDouble(yx));
		t.setYy(Double.parseDouble(yy));
		t.setYz(Double.parseDouble(yz));
		
		t.setZ(Double.parseDouble(z));
		t.setZx(Double.parseDouble(zx));
		t.setZy(Double.parseDouble(zy));
		t.setZz(Double.parseDouble(zz));
		}
		catch(Exception e){}
		
		return t;
		
	}

	@Override
	public String toString() {
		return "VpmTmx [x=" + x + ", xx=" + xx + ", xy=" + xy + ", xz=" + xz
				+ ", y=" + y + ", yx=" + yx + ", yy=" + yy + ", yz=" + yz
				+ ", z=" + z + ", zx=" + zx + ", zy=" + zy + ", zz=" + zz + "]";
	}
	
	


}
