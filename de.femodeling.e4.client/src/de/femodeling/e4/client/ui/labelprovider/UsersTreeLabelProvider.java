package de.femodeling.e4.client.ui.labelprovider;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.ui.IImageKeys;


public class UsersTreeLabelProvider extends StyledCellLabelProvider {

	// Images
	private Image userGroupImage;
	private Image userImage;
	
	Styler online;
	

	public UsersTreeLabelProvider(IBundleResourceLoader loader) {
		super();
		
		userGroupImage=loader.loadImage(getClass(), IImageKeys.USER_GROUP);
		userImage=loader.loadImage(getClass(), IImageKeys.USER);
		
		online=new Styler() {
			
			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.foreground=new Color(null, 0, 0, 150);
			}
		};
		
	}




	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		StyledString text = new StyledString();

		if (element instanceof UserClientGroup) {
			UserClientGroup group = (UserClientGroup) element;
			text.append(group.getName());
			
			cell.setImage(userGroupImage);
			text.append(" (" + group.getNumberOfOnlineUsers() +"/"+group.getContentList().size() + ") ",online);
		} else if (element instanceof UserClientImpl) {
			UserClientImpl user = (UserClientImpl) element;
			text.append(user.getId() + ": " + user.getForename() + ", "
					+ user.getSurname());
		
			cell.setImage(userImage);
			if (user.isOnline()) {
				text.append(" (" + "online" + ") ", online);
				
			}

		}
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}

}
