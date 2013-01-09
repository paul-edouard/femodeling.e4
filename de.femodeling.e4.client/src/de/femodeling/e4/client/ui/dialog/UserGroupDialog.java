package de.femodeling.e4.client.ui.dialog;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.femodeling.e4.client.model.UserClientImpl;

public class UserGroupDialog extends TitleAreaDialog {
	private DataBindingContext m_bindingContext;
	
	private Text UserGroupName;
	
	private UserClientImpl group;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public UserGroupDialog(Shell parentShell,UserClientImpl group) {
		super(parentShell);
		this.group=group;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("User Group");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Name:");
		
		UserGroupName = new Text(container, SWT.BORDER);
		UserGroupName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		UserGroupName.setText(this.group.getForename());

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(438, 243);
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextUserGroupNameObserveWidget = WidgetProperties.text(SWT.Modify).observe(UserGroupName);
		IObservableValue forenameGroupObserveValue = BeanProperties.value("forename").observe(group);
		bindingContext.bindValue(observeTextUserGroupNameObserveWidget, forenameGroupObserveValue, null, null);
		//
		return bindingContext;
	}
}
