package de.femodeling.e4.client.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.femodeling.e4.client.model.core.AssNodeClient;
import de.femodeling.e4.client.model.core.AssemblyClient;
import de.femodeling.e4.client.model.listener.AssemblyClientListenerIF;
import de.femodeling.e4.model.core.assembly.Assembly;



public class AssemblyClientImpl extends AssemblyClient {
	
	 /** The serial version UID. */
		static final long serialVersionUID=1L;
		
		public AssemblyClientImpl(Assembly.Type type){
	    	this.setType(type);
	    }
		
		public void setRoot(AssNodeClient root) {
			propertyChangeSupport.firePropertyChange("root", this.getRoot(),root);
			fireAssemblyChanged();
			super.setRoot(root);
			
		}
		
		public void calculateNodeWeight(HashMap<String, PartClientImpl> parts){
			this.getRoot().calWeigth(parts);
			this.getRoot().calculateDeltaMass2(this.getRoot().getCalWeight());
			
		}
		
		public void calculateFeModuleWeigth(AssemblyClientImpl ass_pdmu){
			this.getRoot().calculateFeModuleMass(ass_pdmu);
			
		}
		
		/***********************************
		 *                                 *
		 *		      LISTENER             *
		 *                                 *
		 ***********************************/
		private List<AssemblyClientListenerIF> listeners;
		
		@Override
		protected void fireLockableEntityChanged() {
			fireAssemblyChanged();
		}
		
		public void addAssemblyListener(AssemblyClientListenerIF listener){
			if (listeners == null)
				listeners = new LinkedList<AssemblyClientListenerIF>();
			listeners.add(listener);
			
		}
		
		public void removeAssemblysListener(AssemblyClientListenerIF listener){
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty())
					listeners = null;
			}
		}
		
		
		protected void fireAssemblyChanged(){
			
			//logger.warn("No Parent Found: "+this.name);
			if (listeners == null)
				return;
			Object[] rls = listeners.toArray();
			for (int i = 0; i < rls.length; i++) {
				AssemblyClientListenerIF listener =(AssemblyClientListenerIF)  rls[i];
				listener.assemblyChanged(this);
			}
			
		}
		
}
