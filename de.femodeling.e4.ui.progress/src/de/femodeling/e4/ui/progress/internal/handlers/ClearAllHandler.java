 
package de.femodeling.e4.ui.progress.internal.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class ClearAllHandler {
	@Execute
	public void execute() {
		//TODO Your code goes here
		System.out.println("Clear All pressed!");
	}
	
	
	@CanExecute
	public boolean canExecute() {
		//TODO Your code goes here
		return true;
	}
		
}