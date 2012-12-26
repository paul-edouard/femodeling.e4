package de.femodeling.e4.model.core.connection;

import java.util.HashMap;
import java.util.LinkedList;

import de.femodeling.e4.model.core.LockableEntity;

public class ConnectionElement extends LockableEntity {
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;
	
	protected String vtaTable;
	protected String date;
	
	protected LinkedList<ConnectionParameter> parameterList=new LinkedList<ConnectionParameter>();
	protected HashMap<String, ConnectionPart> partMap=new HashMap<String, ConnectionPart>();
	protected HashMap<String, ConnectionZSB> zsbMap=new HashMap<String, ConnectionZSB>();
	protected LinkedList<ConnectionSupportPoint> supportPointList=new LinkedList<ConnectionSupportPoint>();
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VtaElement [vtaTable=" + vtaTable + ", date=" + date
				+ ", parameterList=" + parameterList + "\npartMap=" + partMap
				+ "\nzsbMap=" + zsbMap + "\nsupportPointList="
				+ supportPointList + "]";
	}
	
	
	
	

}
