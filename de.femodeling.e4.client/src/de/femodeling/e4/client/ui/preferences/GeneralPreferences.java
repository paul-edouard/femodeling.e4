package de.femodeling.e4.client.ui.preferences;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.e4.core.di.extensions.Preference;

import de.femodeling.e4.client.model.ClientSession;

public enum GeneralPreferences {
	
	INSTANCE;
	
	public static final String AUTO_LOGIN = "prefs_auto_login";
	public static final String WORKING_DIR = "prefs_working_directory";
	
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.AUTO_LOGIN)
	Boolean auto_login;
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.WORKING_DIR)
	String workDir;
	
	@Inject
	ClientSession session;
	
	
	public String getWorkDir(){
		if(workDir.isEmpty()){
			return getCreateDefaultDirname();
		}
		return workDir;
		
	}
	
	
	public  String getCreateDefaultDirname(){
		if(System.getenv().containsKey("HOSTTYPE")){
			if(System.getenv().get("HOSTTYPE").contains("linux")){
				
				String sep=File.separator;
				String userDir=sep+"scr1"+sep+session.getConnectionDetails().getUserId();
				userDir+=sep+"FE_Modeling_Client";
				
				File uDir=new File(userDir);
				if(!uDir.exists())if(!uDir.mkdirs())return "";
				
				return userDir;
				
			}
		}
		
		return "";
	}
	
}
