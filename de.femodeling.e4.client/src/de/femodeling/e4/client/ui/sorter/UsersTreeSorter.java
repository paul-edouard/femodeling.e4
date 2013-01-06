package de.femodeling.e4.client.ui.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

import de.femodeling.e4.client.model.UserClientImpl;


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

			rc = p1.getId().compareTo(p2.getId());

			// If descending order, flip the direction
			if (direction == DESCENDING) {
				rc = -rc;
			}
		}
		return rc;
	}

}
