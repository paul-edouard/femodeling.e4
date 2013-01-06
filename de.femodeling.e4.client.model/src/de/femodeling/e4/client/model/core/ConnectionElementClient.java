package de.femodeling.e4.client.model.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.LockableEntityClientImpl;
import de.femodeling.e4.client.model.ConnectionElementClientImpl;
import de.femodeling.e4.model.core.connection.ConnectionParameter;
import de.femodeling.e4.model.core.connection.ConnectionPart;
import de.femodeling.e4.model.core.connection.ConnectionSupportPoint;
import de.femodeling.e4.model.core.connection.ConnectionZSB;


public abstract class ConnectionElementClient extends LockableEntityClientImpl {
	
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    protected String vtaTable;
	protected String date;
	
	protected LinkedList<ConnectionParameter> parameterList=new LinkedList<ConnectionParameter>();
	protected HashMap<String, ConnectionPart> partMap=new HashMap<String, ConnectionPart>();
	protected HashMap<String, ConnectionZSB> zsbMap=new HashMap<String, ConnectionZSB>();
	protected LinkedList<ConnectionSupportPoint> supportPointList=new LinkedList<ConnectionSupportPoint>();
	
	
	private ConnectionElementClientImpl parent;
	    
	private List<ConnectionElementClientImpl> childs;
	
	
	public ConnectionElementClientImpl getParent() {
		return parent;
	}

	protected void setParent(ConnectionElementClientImpl parent) {
		this.parent = parent;
	}

	public List<ConnectionElementClientImpl> getChilds() {
		if(childs==null)childs=new LinkedList<ConnectionElementClientImpl>(); 
		return childs;
	}

	public void setChilds(List<ConnectionElementClientImpl> childs) {
		this.childs = childs;
	}
	
	public void addChild(ConnectionElementClientImpl e){
		 getChilds().add(e);
		 e.setParent((ConnectionElementClientImpl) this);
	}
	
	public String getVtaTable() {
		return vtaTable;
	}
	public void setVtaTable(String vtaTable) {
		this.vtaTable = vtaTable;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public LinkedList<ConnectionParameter> getParameterList() {
		return parameterList;
	}
	public void setParameterList(LinkedList<ConnectionParameter> parameterList) {
		this.parameterList = parameterList;
	}
	public HashMap<String, ConnectionPart> getPartMap() {
		return partMap;
	}
	public void setPartMap(HashMap<String, ConnectionPart> partMap) {
		this.partMap = partMap;
	}
	public HashMap<String, ConnectionZSB> getZsbMap() {
		return zsbMap;
	}
	public void setZsbMap(HashMap<String, ConnectionZSB> zsbMap) {
		this.zsbMap = zsbMap;
	}
	public LinkedList<ConnectionSupportPoint> getSupportPointList() {
		return supportPointList;
	}
	public void setSupportPointList(LinkedList<ConnectionSupportPoint> supportPointList) {
		this.supportPointList = supportPointList;
	}
	
	public String getParamValue(String param_key){
		for(ConnectionParameter param:this.getParameterList()){
			if(param.getName().equals(param_key))return param.getValue();
		}
		
		return "";
	}

}
