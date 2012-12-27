/**
 * 
 */
package de.femodeling.e4.server.service.remote;

import java.io.Serializable;

/**
 * @author Paul
 *
 */
public interface SessionServiceIF extends Serializable {
	
	
	
	/**
     * 
     */
   public void keepAlive(String sessionId);

}
