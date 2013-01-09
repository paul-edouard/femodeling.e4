package de.femodeling.e4.client.ui.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.core.UserClientGroup;


public class UsersTreeSorter extends ViewerSorter {

	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public UsersTreeSorter() {

		direction = DESCENDING;
	}

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int rc = 0;
		if (e1 instanceof UserClientImpl && e2 instanceof UserClientImpl) {
			UserClientImpl p1 = (UserClientImpl) e1;
			UserClientImpl p2 = (UserClientImpl) e2;

			rc = p2.getId().compareTo(p1.getId());

			// If descending order, flip the direction
			if (direction == DESCENDING) {
				rc = -rc;
			}
		}
		else if(e1 instanceof UserClientGroup && e2 instanceof UserClientGroup){
			UserClientGroup p1 = (UserClientGroup) e1;
			UserClientGroup p2 = (UserClientGroup) e2;

			rc = p2.getName().compareTo(p1.getName());

			// If descending order, flip the direction
			if (direction == DESCENDING) {
				rc = -rc;
			}
		}
		return rc;
	}

}
