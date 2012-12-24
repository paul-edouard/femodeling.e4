package de.femodeling.e4.client.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;

public class LifeCycleManager {
	
	@PostContextCreate
	void postContextCreate(){
		System.out.println("PostContextCreate called");
	}

}
