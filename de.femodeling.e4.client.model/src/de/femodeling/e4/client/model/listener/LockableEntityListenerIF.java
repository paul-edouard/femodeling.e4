package de.femodeling.e4.client.model.listener;

import de.femodeling.e4.client.model.LockableEntityClientImpl;


public interface LockableEntityListenerIF {
	public void lockableEntityChanged(LockableEntityClientImpl entity);
}
