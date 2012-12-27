package de.femodeling.e4.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import de.femodeling.e4.model.core.connection.ConnectionParameter;
import de.femodeling.e4.model.core.connection.ConnectionPart;
import de.femodeling.e4.model.core.connection.ConnectionSupportPoint;
import de.femodeling.e4.model.core.connection.ConnectionZSB;


public abstract class ConnectionElementDTO extends LockableEntityDTO implements
Serializable{
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
    
    
    private String vtaTable;
    private String date;
	
    private LinkedList<ConnectionParameter> parameterList=new LinkedList<ConnectionParameter>();
    private HashMap<String, ConnectionPart> partMap=new HashMap<String, ConnectionPart>();
    private HashMap<String, ConnectionZSB> zsbMap=new HashMap<String, ConnectionZSB>();
    private LinkedList<ConnectionSupportPoint> supportPointList=new LinkedList<ConnectionSupportPoint>();
	
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

}
