package de.femodeling.e4.server.service.filetransfer;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

public class FileTransferService implements FileTransferServiceIF{
	
	
	private String sessionId;
	private String fileTransferService;
	
	/*
	 * initializes the session id
	 */
	public void setSessionId(String sessionId){
		this.sessionId=sessionId;
	}
	
	/*
	 * initializes the http service
	 */
	public void setHttpServicePath(String servicePath){
		fileTransferService=servicePath+"/file_transfers";
	}
	
	
	/*
	 * downloads a file from the server and returns the local file
	 * place on success or null 
	 */
	public String downloadFile(String serverFileName, String localDirectory){
		/*
		File f=new File(serverFileName);
		if(f.canRead())return serverFileName;
		*/
		
		
		//File inputFile = new File(serverFileName);
		String destFile=localDirectory+File.separator+(new File(serverFileName)).getName();
		
		File localDir = new File(localDirectory);
		if (!localDir.isDirectory() || !localDir.canWrite()) {
			System.err.println("Cannot write in the directory: "
					+ localDir.getPath());
			return null;
		}
		
		try {

			URL url = new URL(fileTransferService + "?name="
					+ URLEncoder.encode(serverFileName, "UTF-8")+ "&session="
					+ URLEncoder.encode(this.sessionId, "UTF-8"));

			URLConnection connection = url.openConnection();
			connection.setRequestProperty("REQUEST_METHOD", "GET");

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(true);

			connection.setRequestProperty("Content-Type", "multipart-formdata");
			
			//connection.getOutputStream();
			connection.connect();
			
			// Input
			DataInputStream dataIn = new DataInputStream(connection.getInputStream());
			// output
			//File f = new File(destFile);
			BufferedOutputStream buffOut = new BufferedOutputStream(
								new FileOutputStream(destFile));

			//System.out.println("Started Time: "+Calendar.getInstance().getTimeInMillis());
			int c = FileTransferServlet.bufferSize;
			byte b[] = new byte[c];

			for (int j = dataIn.read(b, 0, c); j >= 0; j = dataIn.read(b, 0, c)) {
					buffOut.write(b, 0, j);
					buffOut.flush();
			}

			buffOut.flush();
			//System.out.println("End Time: "+Calendar.getInstance().getTimeInMillis());
			
			
			buffOut.close();
			dataIn.close();
			
		
			return destFile;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	
		
	}
	
	
	
	/*
	 * uploads a file to the server
	 */
	public boolean uploadFile(String localFileName,String serverDirectory){
		
		
		File inputFile = new File(localFileName);
		if (!inputFile.exists() || inputFile.isDirectory()) {
			System.err.println("Cannot found the file to upload: "
					+ inputFile.getPath());
			return false;
		}

		String fileName = inputFile.getPath();
		
		//Create the target directory:
		/*
		File t_dir=new File(serverDirectory);
		
		if(!t_dir.exists() && !t_dir.mkdirs()){
			System.err.println("Cannot create the target directory: "
					+ serverDirectory);
			return false;
		}
		*/
		
		String destPath = serverDirectory + File.separator
				+ inputFile.getName();

		try {

			URL url = new URL(fileTransferService + "?name="
					+ URLEncoder.encode(fileName, "UTF-8") + "&path="
					+ URLEncoder.encode(destPath, "UTF-8")+ "&session="
					+ URLEncoder.encode(this.sessionId, "UTF-8"));
			
			//HttpURLConnection
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			
			//HttpURLConnection connection = url.openConnection();
			connection.setRequestProperty("REQUEST_METHOD", "POST");

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(true);

			connection.setRequestProperty("Content-Type", "multipart-formdata");
			connection.setChunkedStreamingMode(FileTransferServlet.bufferSize); 
			
			connection.connect();
			
			
			
			//output
			//BufferedOutputStream buffOut = new BufferedOutputStream(connection.getOutputStream());
			
			DataOutputStream buffOut =new DataOutputStream(connection.getOutputStream());
			
			//DataInputStream
			//input
			DataInputStream buffIn=new DataInputStream(new FileInputStream(inputFile));
			
			//Transfer the data from input to output
			//System.out.println("Started Time: "+Calendar.getInstance().getTimeInMillis());
			int c = FileTransferServlet.bufferSize;
			byte b[] = new byte[c];
			for(int j = buffIn.read(b, 0, c); j >= 0; j = buffIn.read(b, 0, c))
			{
				//Thread.sleep(50);
				 buffOut.write(b, 0, j);
				 buffOut.flush();
			}
			buffOut.flush();
			//System.out.println("End Time: "+Calendar.getInstance().getTimeInMillis());
			

			
			buffOut.close();
            buffIn.close();

         // prepare for reading the answer
            
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String message;
			String all_message="";
			while ((message = in.readLine()) != null) {
				all_message+=message;
			}
			
			in.close();
			connection.disconnect();
			
			return all_message.equals(FileTransferServlet.fileRecieved);
			
            
           // return true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		
		
	}

	
	 public static void main(String[] args) throws Exception {
		 	
		 
		 FileTransferService ft=new  FileTransferService();
		 ft.setSessionId("MySession");
		 ft.setHttpServicePath("http://localhost:8081/de.porsche.femodeling.server");
		 
		 String inputFile="D:\\Paul\\04_Programierung\\Test\\HttpFileTransfer\\Input\\qt-sdk-win-opensource-2009.01.1.exe";
		 String outputFile="D:\\Paul\\04_Programierung\\Test\\HttpFileTransfer\\Output";
		 
		 
		 if(ft.uploadFile(inputFile, outputFile)){
			 System.out.println("Upload with success:\n"+inputFile);
		 }
		 else{
			 System.out.println("Problems!!");
		 }
		 
		 
		 /*
		 if(ft.downloadFile(inputFile, outputFile)!=null){
			 System.out.println("Download with success:\n"+inputFile);
		 }
		 else{
			 System.out.println("Problems!!");
		 }
		 */
		 
	 }
		    
		    

}
