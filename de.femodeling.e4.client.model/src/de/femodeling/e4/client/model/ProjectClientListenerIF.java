package de.femodeling.e4.client.model;

public interface ProjectClientListenerIF {
	public void projectsChanged(ProjectClientImpl current, ProjectClientImpl child);
}
