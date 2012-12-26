package de.femodeling.e4.model.core;

import java.io.Serializable;

import java.lang.String;

import java.util.Date;


public class Session implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    private String userId;

    private String sessionId;

    private Date lastAccess;
    
    


    /**
     * @return the {@link java.lang.String} to get
     */
    public String getUserId() {
        return userId;
    }

    /**
     *  @param userId The {@link java.lang.String} to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the {@link java.lang.String} to get
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     *  @param sessionId The {@link java.lang.String} to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     *  @param lastAccess The {@link java.util.Date} to set
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

	@Override
	public String toString() {
		return "Session [userId=" + userId + ", sessionId=" + sessionId
				+ ", lastAccess=" + lastAccess + "]";
	}
    
    
    
    
}