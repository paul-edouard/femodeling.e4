package de.femodeling.e4.client.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.core.ConnectionElementClient;
import de.femodeling.e4.client.model.listener.ConnectionElementClientListernerIF;
import de.femodeling.e4.model.core.connection.ConnectionParameter;
import de.femodeling.e4.model.core.connection.ConnectionPart;
import de.femodeling.e4.model.core.connection.ConnectionSupportPoint;
import de.femodeling.e4.model.core.connection.ConnectionZSB;


public class ConnectionElementClientImpl extends ConnectionElementClient {
	
	
	
	public boolean arePartsFound(){
		
		for(ConnectionPart p:this.getPartMap().values()){
			if(!p.isFound())return false;
		}
		return true;
	}
	
	public boolean arePartsCorrected(){
		for(ConnectionPart p:this.getPartMap().values()){
			if(!p.isFound() && p.getCorrectedName()==null)return false;
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see de.porsche.femodeling.core.assembly.vta.VtaElementClient#setVtaTable(java.lang.String)
	 */
	@Override
	public void setVtaTable(String vtaTable) {
		propertyChangeSupport.firePropertyChange("vtaTable", this.getVtaTable(),vtaTable);
		fireChanged();
		super.setVtaTable(vtaTable);
	}

	/* (non-Javadoc)
	 * @see de.porsche.femodeling.core.assembly.vta.VtaElementClient#setDate(java.lang.String)
	 */
	@Override
	public void setDate(String date) {
		propertyChangeSupport.firePropertyChange("date", this.getDate(),date);
		fireChanged();
		super.setDate(date);
	}

	/* (non-Javadoc)
	 * @see de.porsche.femodeling.core.assembly.vta.VtaElementClient#setParameterList(java.util.LinkedList)
	 */
	@Override
	public void setParameterList(LinkedList<ConnectionParameter> parameterList) {
		propertyChangeSupport.firePropertyChange("parameterList", this.getParameterList(),parameterList);
		fireChanged();
		super.setParameterList(parameterList);
	}

	/* (non-Javadoc)
	 * @see de.porsche.femodeling.core.assembly.vta.VtaElementClient#setPartMap(java.util.HashMap)
	 */
	@Override
	public void setPartMap(HashMap<String, ConnectionPart> partMap) {
		propertyChangeSupport.firePropertyChange("partMap", this.getPartMap(),partMap);
		fireChanged();
		super.setPartMap(partMap);
	}

	/* (non-Javadoc)
	 * @see de.porsche.femodeling.core.assembly.vta.VtaElementClient#setZsbMap(java.util.HashMap)
	 */
	@Override
	public void setZsbMap(HashMap<String, ConnectionZSB> zsbMap) {
		propertyChangeSupport.firePropertyChange("zsbMap", this.getZsbMap(),zsbMap);
		fireChanged();
		super.setZsbMap(zsbMap);
	}

	/* (non-Javadoc)
	 * @see de.porsche.femodeling.core.assembly.vta.VtaElementClient#setSupportPointList(java.util.LinkedList)
	 */
	@Override
	public void setSupportPointList(LinkedList<ConnectionSupportPoint> supportPointList) {
		propertyChangeSupport.firePropertyChange("supportPointList", this.getSupportPointList(),supportPointList);
		fireChanged();
		super.setSupportPointList(supportPointList);
	}


	/***********************************
	 *                                 *
	 *		      LISTENER             *
	 *                                 *
	 ***********************************/	
	private List<ConnectionElementClientListernerIF> listeners;
	
	@Override
	protected void fireLockableEntityChanged() {
		fireChanged();
	}
	
	public void addPartListener(ConnectionElementClientListernerIF listener){
		if (listeners == null)
			listeners = new LinkedList<ConnectionElementClientListernerIF>();
		listeners.add(listener);
		
	}
	
	public void removeProjectsListener(ConnectionElementClientListernerIF listener){
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty())
				listeners = null;
		}
	}
	
	
	protected void fireChanged()
	{
		//logger.warn("No Parent Found: "+this.name);
		if (listeners == null)
			return;
		Object[] rls = listeners.toArray();
		for (int i = 0; i < rls.length; i++) {
			ConnectionElementClientListernerIF listener =(ConnectionElementClientListernerIF)  rls[i];
			listener.vtaElementChanged(this);
		}
		
	}
	
	
	
	
}
