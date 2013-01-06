package de.femodeling.e4.client.handlers;
 

import java.io.File;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.client.ui.preferences.GeneralPreferences;
import de.femodeling.e4.util.file.FileService;

public class ClearWorkspaceHandler {
	@Execute
	public void execute(
			@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.WORKING_DIR)
			String workDir,Shell shell) {
		
		
		boolean res = MessageDialog.openConfirm(shell, "Clear workspace?",
				"Do you really want to clear the directory:\n"+workDir+"?");
		
		if(!res)return;
		
		File dir=new File(workDir);
		if(dir.isDirectory()){
			//TODO Test if the workspace is used from a other session
			FileService.clearDir(dir);
			System.out.println("Workspace is now clear!");
		}
		else
			System.out.println("Cannot clear the worspace!!");
		
		
	}
	
	
	@CanExecute
	public boolean canExecute() {
		//TODO Your code goes here
		return true;
	}
		
}