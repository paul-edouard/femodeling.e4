package de.femodeling.e4.client.model.listener;

import de.femodeling.e4.client.model.ProjectClientImpl;

public interface ProjectClientListenerIF {
	public void projectsChanged(ProjectClientImpl current, ProjectClientImpl child);
}
