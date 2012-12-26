package de.femodeling.e4.model.dto;

import java.util.UUID;

public class PartDTOImpl extends PartDTO {
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The object id. */
   	private String oid = UUID.randomUUID().toString();
   	
   	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		if (object instanceof PartDTOImpl){
			PartDTOImpl otherEntityDTOImpl = (PartDTOImpl) object;
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
