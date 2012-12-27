package de.femodeling.e4.server.internal;



import java.io.*;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.http.*;



public class FileTransferServlet extends HttpServlet
{
	
	static final long serialVersionUID=010101;
	
	//public static final int bufferSize=30720;
	public static final int bufferSize=1000;
	
	
	
	public static final String fileRecieved="File Recieved";
	
   // private static String message = "Error during Servlet processing";
    
	public FileTransferServlet()
    {
		
		 System.out.println("Upload File Service Started");
    }

   public void init()throws ServletException
   {
 }

  public void doGet(HttpServletRequest req, HttpServletResponse resp){
    
  try {	
 	 
 	// System.out.println("GET Request Started");
 	 
 	 String fileName = req.getParameter("name");
     /*
 	 String session = req.getParameter("session");
   	*/
     //System.out.println("Filename: "+fileName);
     //System.out.println("New Name: "+fileName.replaceAll("Â±", "±"));
     //System.out.println("session: "+session);
     
     
   //output
	BufferedOutputStream buffOut = new BufferedOutputStream(resp.getOutputStream());
	//input
	BufferedInputStream buffIn=new BufferedInputStream(new FileInputStream(fileName.replaceAll("Â±", "±")));
		
	//Transfer the data from input to output
	//System.out.println("Started Time: "+Calendar.getInstance().getTimeInMillis());
	int c = FileTransferServlet.bufferSize;
	byte b[] = new byte[c];
	for(int j = buffIn.read(b, 0, c); j >= 0; j = buffIn.read(b, 0, c))
		{
			 buffOut.write(b, 0, j);
			 buffOut.flush();
			// System.out.println("j:"+j+" Time: "+Calendar.getInstance().getTimeInMillis());
		}
		
	buffOut.flush();
	//System.out.println("End Time: "+Calendar.getInstance().getTimeInMillis());
	
	
	resp.setStatus(HttpServletResponse.SC_OK);
	
	buffOut.close();
    buffIn.close();
    
    
   
    
    
 	 
  } catch (IOException e) {
      try{
          resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          resp.getWriter().print(e.getMessage());
          resp.getWriter().close();
      } catch (IOException ioe) {
      }
  }
    }

  public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			System.out.println("POST Request Started: Upload!!");
			
			/*
			 * System.out.println("Number of Session: "+
			 * SessionProvider.INSTANCE.getAllSessions().size());
			 */

			String fileName = req.getParameter("name");
			String destPath = req.getParameter("path");
			String session = req.getParameter("session");

			//System.out.println("Filename: " + fileName);
			//System.out.println("destPath: " + destPath);
			//System.out.println("session: " + session);
			
			// Input
			DataInputStream dataIn = new DataInputStream(req.getInputStream());
			// output
			File destFile = new File(destPath);
			
			//Create the target Dir:
			File targetDir=new File(destFile.getParent());
			targetDir.mkdirs();
			
			
			BufferedOutputStream buffOut = new BufferedOutputStream(
					new FileOutputStream(destFile));

			//System.out.println("Started Time: "+Calendar.getInstance().getTimeInMillis());
			int c = bufferSize;
			byte b[] = new byte[c];
			for (int j = dataIn.read(b, 0, c); j >= 0; j = dataIn.read(b, 0, c)) {
				buffOut.write(b, 0, j);
				buffOut.flush();
			}
			buffOut.flush();
			//System.out.println("End Time: "+Calendar.getInstance().getTimeInMillis());
			
			buffOut.close();
			dataIn.close();

			// set the response code and write the response data
			resp.setStatus(HttpServletResponse.SC_OK);
			
			OutputStreamWriter writer = new OutputStreamWriter(
					resp.getOutputStream());

			writer.write(fileRecieved);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			try {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.getWriter().print(e.getMessage());
				resp.getWriter().close();
			} catch (IOException ioe) {
			}
		}

	}
        
}