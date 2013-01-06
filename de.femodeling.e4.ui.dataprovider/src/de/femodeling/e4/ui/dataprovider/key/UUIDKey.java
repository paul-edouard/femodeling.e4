package de.femodeling.e4.ui.dataprovider.key;

public class UUIDKey implements IKey, Comparable<UUIDKey> {
	
	
	private String uuidKey="";
	
	public UUIDKey(String uuidKey){
		super();
		this.uuidKey=uuidKey;
		
	}

	public String getUUIDKey() {
		return uuidKey;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(uuidKey!=null)
		return uuidKey.hashCode();
		else return 0;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof UUIDKey) {
			UUIDKey otherPrimaryKey = (UUIDKey) object;
			return getUUIDKey().equals(otherPrimaryKey.getUUIDKey());
		}
		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return uuidKey;
	}

	/** {@inheritDoc} */
	public int compareTo(UUIDKey otherPrimaryKey) {
		return uuidKey.compareTo(otherPrimaryKey.getUUIDKey());
	}

}
