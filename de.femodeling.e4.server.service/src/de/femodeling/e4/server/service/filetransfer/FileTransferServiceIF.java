package de.femodeling.e4.server.service.filetransfer;

public interface FileTransferServiceIF {
	
	
	/*
	 * initializes the session id
	 */
	public void setSessionId(String sessionId);
	
	
	/*
	 * initializes the http service
	 */
	public void setHttpServicePath(String servicePath);
	
	
	/*
	 * downloads a file from the server and returns the local file
	 * place on success or null 
	 */
	public String downloadFile(String serverFileName, String localDirectory);
	
	
	/*
	 * uploads a file to the server
	 */
	public boolean uploadFile(String localFileName,String serverDirectory);
	
}
