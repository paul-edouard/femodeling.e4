package de.femodeling.e4.client.ui.labelprovider;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.ui.IImageKeys;


public class UsersTreeLabelProvider extends StyledCellLabelProvider {

	// Images
	private Image userGroupImage;
	private Image userImage;
	
	
	

	public UsersTreeLabelProvider(IBundleResourceLoader loader) {
		super();
		
		userGroupImage=loader.loadImage(getClass(), IImageKeys.USER_GROUP);
		userImage=loader.loadImage(getClass(), IImageKeys.USER);
	}




	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		StyledString text = new StyledString();

		if (element instanceof UserClientGroup) {
			UserClientGroup group = (UserClientGroup) element;
			text.append(group.getName());
			// cell.setImage(PlatformUI.getWorkbench().getSharedImages()
			// .getImage(ISharedImages.IMG_OBJ_FOLDER));
			cell.setImage(userGroupImage);

			text.append(" (" + group.getContentList().size() + ") ",
					StyledString.COUNTER_STYLER);
		} else if (element instanceof UserClientImpl) {
			UserClientImpl user = (UserClientImpl) element;
			text.append(user.getId() + ": " + user.getForename() + ", "
					+ user.getSurname());
			// cell.setImage(PlatformUI.getWorkbench().getSharedImages()
			// .getImage(ISharedImages.IMG_OBJ_FILE));
			cell.setImage(userImage);
			if (user.isOnline()) {
				text.append(" (" + "online" + ") ", StyledString.COUNTER_STYLER);
			}

		}
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}

}
