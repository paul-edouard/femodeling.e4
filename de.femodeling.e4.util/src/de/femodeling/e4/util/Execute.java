package de.femodeling.e4.util;

import java.io.*;

public class Execute {
	
	
	private String OutputMessage="";
	
	
	/**
	 * 
	 * this starts a linux command and write the output in Console Windows
	 * 
	 *
	 */
	public int go(String command){


		try
	       {
			
		           Runtime rt = Runtime.getRuntime();
		           Process proc = rt.exec(command);
		         
		          
		         //  UiControl.jTabbedPane.setSelectedIndex(1);
		           // any error message?
		           StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

		           // any output?
		           StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

		           // kick them off
		           errorGobbler.start();
		           outputGobbler.start();

		           // any error???
		           int exitVal = proc.waitFor();
		          // SwingUtilities.invokeLater(new RefreshTextArea(Output,"ExitValue: " + exitVal+"\n"));  
		           return exitVal;
		           
		        
		 }
		 catch (Throwable t)
	         {
	         	  t.printStackTrace();
	         	  return -1;
	         }


	}
	
	/**
	 * 
	 * this starts e command and writes the output in Console Windows
	 * 
	 *
	 */
	public String getOutput(String command){

		OutputMessage="";

		try
	       {
					Runtime rt = Runtime.getRuntime();
					Process proc = rt.exec(command);

		           // any output?
		           StreamGobblerToString outputGobbler = new StreamGobblerToString(proc.getInputStream() );

		           outputGobbler.start();

		           proc.waitFor();
		           
		           while(outputGobbler.isAlive()){
		        	  Thread.sleep(10);
		           }
		           
 		           
		        
		 }
		 catch (Throwable t)
	         {
	         	  t.printStackTrace();
	         }

		 return OutputMessage;
		 
	}
	
	
	/**
	 * 
	 * this class catch the result from InputStream
	 * as long as InputStream is open and write itn a JTextArea
	 * 
	 *
	 */
	class StreamGobbler extends Thread
	{
	   InputStream is;
	   String type;
	 

	   StreamGobbler(InputStream is, String type)
	   {
	       this.is = is;
	       this.type = type;
	   }

	   public void run()
	   {
	       try
	       {
	           InputStreamReader isr = new InputStreamReader(is);
	           BufferedReader br = new BufferedReader(isr);
	           String line=null;
	           while ( (line = br.readLine()) != null){
	        	   //SwingUtilities.invokeLater(new RefreshTextArea(JTA,type + ">" + line+"\n"));
	        	   System.out.println(line);
	           }
			
	        } catch (IOException ioe)
	             {
	               ioe.printStackTrace();
	             }
	   }
	}
	
	/**
	 * 
	 * this class catch the result from InputStream
	 * as long as InputStream is open and write itn a JTextArea
	 * 
	 *
	 */
	class StreamGobblerToString extends Thread
	{
	   InputStream is;

	   StreamGobblerToString(InputStream is)
	   {
	       this.is = is;
	   }

	   public void run()
	   {
	       try
	       {
	           InputStreamReader isr = new InputStreamReader(is);
	           BufferedReader br = new BufferedReader(isr);
	           String line=null;
	           while ( (line = br.readLine()) != null){
	        	
	        	   OutputMessage+=  line+"\n";
	        	  //System.out.println(Message);
	           }
			
	        } catch (IOException ioe)
	             {
	               ioe.printStackTrace();
	             }
	   }
	}
	
	
	
}
