package de.femodeling.e4.model.dto;

import java.util.UUID;

public class UserDTOImpl extends UserDTO{
	
	/** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The object id. */
	private String oid = UUID.randomUUID().toString();
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		if (object instanceof UserDTOImpl) {
			UserDTOImpl otherUserDTOImpl = (UserDTOImpl) object;
			return otherUserDTOImpl.getOid().equals(getOid());
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
	public void setId(String id) {
		super.setId(id);
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
