package de.femodeling.e4.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import de.femodeling.e4.util.Execute;


public class FileService {
	
	private static Logger logger = Logger.getLogger(FileService.class);
	
	
	public static boolean copyfile(String srFile, String dtFile){
		  
		    try{
		      File f1 = new File(srFile);
		      File f2 = new File(dtFile);
		      InputStream in = new FileInputStream(f1);
		      
		      //For Append the file.
//		      OutputStream out = new FileOutputStream(f2,true);

		      //For Overwrite the file.
		      OutputStream out = new FileOutputStream(f2);

		      byte[] buf = new byte[1024];
		      int len;
		      while ((len = in.read(buf)) > 0){
		        out.write(buf, 0, len);
		      }
		      in.close();
		      out.close();
		      
		     
		      
		      return true;
		      
		    }
		    catch(FileNotFoundException ex){
		    	logger.info(ex.getMessage());	
		    	return false;
		      
		    }
		    catch(IOException e){
		    	logger.info(e.getMessage());	
		    	return false;
		    }
		  }	
	
	
	
	public static boolean createFileWithContent(String fileName,String content){
		
		
		 File f=new File(fileName); 
		 if(f.exists())f.delete();
			
			try{
				FileWriter output = new FileWriter(f);
				
				output.write(content);
				output.close();
				
				
			}
			catch(Exception et){
				logger.info(et.getMessage());
				return false;
			}		 
		 
		 return true;
		
	}
	
	
	public static boolean createSymbolicLink(String inputFile, String target){
		File targetFile=new File(inputFile);
		
		if(!targetFile.exists())return false;
		if(!targetFile.canRead())return false;
		
		String Temp=new Execute().getOutput("ln -s "+inputFile+" "+target);
		if(Temp.isEmpty()){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static boolean deleteDirRecu(File dir){
		
		clearDir(dir);
		return dir.delete();
		
		
	}
	
	public static void clearDir(File dir){

		File[] fileList=dir.listFiles();
		if(fileList!=null ){
			for(int i=0;i<fileList.length;i++){
				if(fileList[i].isFile())fileList[i].delete();
				else if(fileList[i].isDirectory())
					deleteDirRecu(fileList[i]);
			}
		}
	}
	
	public static boolean deleteDirRecu(String dir){
		return deleteDirRecu(new File(dir));	
	}

}
