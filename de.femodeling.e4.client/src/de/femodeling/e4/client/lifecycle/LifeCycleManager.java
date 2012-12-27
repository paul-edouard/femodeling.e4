package de.femodeling.e4.client.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;

import de.femodeling.e4.server.service.RemoteService;

public class LifeCycleManager {
	
	@PostContextCreate
	void postContextCreate(RemoteService remoteService){
		System.out.println("PostContextCreate called");
		remoteService.init("coucou");
	}

}
