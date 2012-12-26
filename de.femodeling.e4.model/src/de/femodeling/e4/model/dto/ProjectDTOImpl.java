package de.femodeling.e4.model.dto;

import java.util.UUID;

import de.femodeling.e4.model.core.Project.State;
import de.femodeling.e4.model.core.Project.Type;

public class ProjectDTOImpl extends ProjectDTO {
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The object id. */
	private String oid = UUID.randomUUID().toString();
	
	public ProjectDTOImpl(){
		
		this.setState(State.STARTED);
		this.setGroup("none");
		this.setType(Type.NONE);
		this.setName("");
		this.setPath("");
		this.setSessionId("");
		
	}
	
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		if (object instanceof ProjectDTOImpl) {
			ProjectDTOImpl otherEntityDTOImpl = (ProjectDTOImpl) object;
			return otherEntityDTOImpl.getOid().equals(getOid());
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return oid.hashCode();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setLockableId(String id) {
		super.setLockableId(id);
		if (id != null) {
			oid = id.toString();
		}
	}
	
	/**
	 * @return the oid
	 */
	protected String getOid() {
		return oid;
	}
	
	

}
