package de.femodeling.e4.client.model;

//import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.model.core.LockableEntity;




public class LockableEntityClientImpl extends LockableEntity /*implements PropertyChangeListener */{
	
	static final long serialVersionUID=1L;
	
	
	/**
	 * Property Change support
	 */

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	
	/**
	 * LockableEntity Listener
	 * 
	 */
	private List<LockableEntityListenerIF> listeners;
	
	public void addLockableEntityListener(LockableEntityListenerIF listener){
		if (listeners == null)
			listeners = new LinkedList<LockableEntityListenerIF>();
		listeners.add(listener);
	}
	
	public void removeLockableEntityListener(LockableEntityListenerIF listener) {
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty())
				listeners = null;
		}
		
	}
	
	
	protected void fireLockableEntityChanged() {
			if (listeners == null)
				return;
			Object[] rls = listeners.toArray();
			for (int i = 0; i < rls.length; i++) {
				LockableEntityListenerIF listener = (LockableEntityListenerIF) rls[i];
				if(listener!=null)listener.lockableEntityChanged(this);
			}
		
	}
	
	
	

	@Override
	public void setLockableId(String lockableId) {
		propertyChangeSupport.firePropertyChange("lockableId", this.getLockableId(),lockableId);
		fireLockableEntityChanged();
		super.setLockableId(lockableId);
	}

	@Override
	public void setSessionId(String sessionId) {
		propertyChangeSupport.firePropertyChange("sessionId", this.getSessionId(),sessionId);
		fireLockableEntityChanged();
		super.setSessionId(sessionId);
	}

	

}
