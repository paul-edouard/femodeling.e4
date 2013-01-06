package de.femodeling.e4.client.ui.dialog;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.model.core.User;



public class UserDialog extends Dialog {
	private DataBindingContext m_bindingContext;
	private Text text_user_id;
	private Text text_forename;
	private Text text_surname;
	private Text text_password;
	private Text text_phonenumber;
	private Text text_location;
	private Text text_groups;

	private UserClientImpl user;
	private Button btnAdmin;
	private Button btnDataManager;
	private Button btnCustomer;
	private Button btnSupplier;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public UserDialog(Shell parentShell, UserClientImpl user) {
		super(parentShell);
		setShellStyle(SWT.SHELL_TRIM);
		this.user = user;
	}

	@Override
	public int open() {

		return super.open();
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(1, false);
		container.setLayout(gl_container);

		Label lblNewLabel = new Label(container, SWT.SHADOW_IN);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		lblNewLabel.setText("User Id:");

		text_user_id = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_text_user_id = new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1);
		gd_text_user_id.widthHint = 201;
		text_user_id.setLayoutData(gd_text_user_id);
		if (this.user.getId().isEmpty())
			text_user_id.setEditable(true);

		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setText("Password");
		lblPassword.setVisible(false);

		text_password = new Text(container, SWT.BORDER | SWT.PASSWORD);
		text_password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		text_password.setVisible(false);

		Label lblForename = new Label(container, SWT.NONE);
		lblForename.setText("Forename:");

		text_forename = new Text(container, SWT.BORDER);
		text_forename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblSurname = new Label(container, SWT.NONE);
		lblSurname.setText("Surname:");

		text_surname = new Text(container, SWT.BORDER);
		text_surname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblPhonenumber = new Label(container, SWT.NONE);
		lblPhonenumber.setText("Phonenumber:");

		text_phonenumber = new Text(container, SWT.BORDER);
		text_phonenumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblLocation = new Label(container, SWT.NONE);
		lblLocation.setText("Location:");

		text_location = new Text(container, SWT.BORDER);
		text_location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblGroups = new Label(container, SWT.NONE);
		lblGroups.setText("Groups:");

		text_groups = new Text(container, SWT.BORDER);
		text_groups.setEditable(false);
		text_groups.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		String groups = "";
		for (String group : this.user.getGroups()) {
			groups += group + ", ";
		}
		if (groups.length() > 2)
			groups = groups.substring(0, groups.length() - 2);
		text_groups.setText(groups);

		Label lblRoles = new Label(container, SWT.NONE);
		lblRoles.setText("Roles:");

		btnAdmin = new Button(container, SWT.CHECK);
		btnAdmin.setImage(ResourceManager
				.getPluginImage("de.porsche.femodeling.client",
						"icons/famfamfam/user_suit.png"));
		btnAdmin.setSelection(this.user.hasRole(User.ADMIN));
		btnAdmin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnAdmin.getSelection()) {
					user.addRole(User.ADMIN);
				} else
					user.removeRole(User.ADMIN);

			}
		});
		btnAdmin.setText("Admin");

		btnDataManager = new Button(container, SWT.CHECK);
		btnDataManager.setImage(ResourceManager.getPluginImage(
				"de.porsche.femodeling.client",
				"icons/famfamfam/database_link.png"));
		btnDataManager.setSelection(this.user.hasRole(User.DATA_MANAGER));
		btnDataManager.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnDataManager.getSelection()) {
					user.addRole(User.DATA_MANAGER);
				} else
					user.removeRole(User.DATA_MANAGER);
			}
		});
		btnDataManager.setText("Data Manager");

		btnCustomer = new Button(container, SWT.CHECK);
		btnCustomer.setImage(ResourceManager.getPluginImage(
				"de.porsche.femodeling.client",
				"icons/famfamfam/database_go.png"));
		btnCustomer.setSelection(this.user.hasRole(User.CUSTOMER));
		btnCustomer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnCustomer.getSelection()) {
					user.addRole(User.CUSTOMER);
				} else
					user.removeRole(User.CUSTOMER);
			}
		});
		btnCustomer.setText("Customer");

		btnSupplier = new Button(container, SWT.CHECK);
		btnSupplier.setImage(ResourceManager.getPluginImage(
				"de.porsche.femodeling.client",
				"icons/famfamfam/database_key.png"));
		btnSupplier.setSelection(this.user.hasRole(User.SUPPLIER));
		btnSupplier.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSupplier.getSelection()) {
					user.addRole(User.SUPPLIER);
				} else
					user.removeRole(User.SUPPLIER);
			}
		});
		btnSupplier.setText("Supplier");

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button_ok = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		button_ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		Button button_cancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(503, 546);
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue text_user_idObserveTextObserveWidget = SWTObservables
				.observeText(text_user_id, SWT.Modify);
		IObservableValue userIdObserveValue = BeansObservables.observeValue(
				user, "id");
		bindingContext.bindValue(text_user_idObserveTextObserveWidget,
				userIdObserveValue, null, null);
		//
		IObservableValue text_passwordObserveTextObserveWidget = SWTObservables
				.observeText(text_password, SWT.Modify);
		IObservableValue userPasswordObserveValue = BeansObservables
				.observeValue(user, "password");
		bindingContext.bindValue(text_passwordObserveTextObserveWidget,
				userPasswordObserveValue, null, null);
		//
		IObservableValue text_forenameObserveTextObserveWidget = SWTObservables
				.observeText(text_forename, SWT.Modify);
		IObservableValue userForenameObserveValue = BeansObservables
				.observeValue(user, "forename");
		bindingContext.bindValue(text_forenameObserveTextObserveWidget,
				userForenameObserveValue, null, null);
		//
		IObservableValue text_surnameObserveTextObserveWidget = SWTObservables
				.observeText(text_surname, SWT.Modify);
		IObservableValue userSurnameObserveValue = BeansObservables
				.observeValue(user, "surname");
		bindingContext.bindValue(text_surnameObserveTextObserveWidget,
				userSurnameObserveValue, null, null);
		//
		IObservableValue text_phonenumberObserveTextObserveWidget = SWTObservables
				.observeText(text_phonenumber, SWT.Modify);
		IObservableValue userPhonenumberObserveValue = BeansObservables
				.observeValue(user, "phonenumber");
		bindingContext.bindValue(text_phonenumberObserveTextObserveWidget,
				userPhonenumberObserveValue, null, null);
		//
		IObservableValue text_locationObserveTextObserveWidget = SWTObservables
				.observeText(text_location, SWT.Modify);
		IObservableValue userLocationObserveValue = BeansObservables
				.observeValue(user, "location");
		bindingContext.bindValue(text_locationObserveTextObserveWidget,
				userLocationObserveValue, null, null);
		//
		return bindingContext;
	}
}
