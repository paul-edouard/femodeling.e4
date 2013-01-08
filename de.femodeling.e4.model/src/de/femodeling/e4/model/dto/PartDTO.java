package de.femodeling.e4.model.dto;

import de.femodeling.e4.model.core.part.Part;

public abstract class PartDTO extends Part{
	
	 /** The serial version UID. */
    private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "PartDTO [isCadFileFound()=" + isCadFileFound()
				+ ", getCadFileName()=" + getCadFileName()
				+ ", getDescription()=" + getDescription() + ", getPartId()="
				+ getPartId() + ", getName()=" + getName() + ", getOwner()="
				+ getOwner() + ", getRevision()=" + getRevision()
				+ ", getStatus()=" + getStatus() + ", getStatusText()="
				+ getStatusText() + ", getTypeText()=" + getTypeText()
				+ ", getType()=" + getType() + ", getAnsaModuleId()="
				+ getAnsaModuleId() + ", getWeightList()=" + getWeightList()
				+ ", getMaterialList()=" + getMaterialList()
				+ ", getTranslation()=" + getTranslation()
				+ ", getRepresentationList()=" + getRepresentationList()
				+ ", getLockableId()=" + getLockableId() + ", islocked()="
				+ islocked() + ", getSessionId()=" + getSessionId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
    
    
}
