package de.femodeling.e4.ui.dataprovider.key;

public class UUIDCollectionKey implements ICollectionKey, Comparable<UUIDCollectionKey> {
	
	
	private String uuidKey;
	
	
	public UUIDCollectionKey(String uuidKey){
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
		return uuidKey.hashCode();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof UUIDCollectionKey) {
			UUIDCollectionKey otherPrimaryKey = (UUIDCollectionKey) object;
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
	public int compareTo(UUIDCollectionKey otherPrimaryKey) {
		return uuidKey.compareTo(otherPrimaryKey.getUUIDKey());
	}
	
}
