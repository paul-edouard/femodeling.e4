package de.femodeling.e4.client.ui.dialog;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.BackingStoreException;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.ui.IImageKeys;
import de.femodeling.e4.client.ui.preferences.GeneralPreferences;
import de.femodeling.e4.model.core.ConnectionDetails;
import de.femodeling.e4.util.Utils;

/**
 * Login dialog, which prompts for the user's account info, and has Login and
 * Cancel buttons.
 */
public class SecureLoginDialog extends TitleAreaDialog {

	private Text userIdText;

	private Text serverText;

	
	private ConnectionDetails connectionDetails;

	//private HashMap savedDetails = new HashMap();

	private Image[] images;

	private static final String SERVER = "server";

	private boolean showError;
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.AUTO_LOGIN)
	Boolean auto_login;
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = SERVER)
	String server;
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client")
	IEclipsePreferences prefs;
	
	@Inject
	IBundleResourceLoader loader;
	
	
	@Inject
	public SecureLoginDialog(@Optional Shell parentShell) {
		super(parentShell);
		
	}

	public void setShowError(boolean showError) {
		this.showError = showError;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Login");
		//TODO set the shell image
		// load the image from the product definition
		/*
		IProduct product = Platform.getProduct();
		if (product != null) {
			String[] imageURLs = parseCSL(product
					.getProperty(IProductConstants.WINDOW_IMAGES));
			if (imageURLs.length > 0) {
				images = new Image[imageURLs.length];
				for (int i = 0; i < imageURLs.length; i++) {
					String url = imageURLs[i];
					ImageDescriptor descriptor = AbstractUIPlugin
							.imageDescriptorFromPlugin(product
									.getDefiningBundle().getSymbolicName(), url);
					images[i] = descriptor.createImage(true);
				}
				newShell.setImages(images);
			}
		}
		*/
	}

	public static String[] parseCSL(String csl) {
		if (csl == null)
			return null;

		StringTokenizer tokens = new StringTokenizer(csl, ","); //$NON-NLS-1$
		ArrayList array = new ArrayList(10);
		while (tokens.hasMoreTokens())
			array.add(tokens.nextToken().trim());

		return (String[]) array.toArray(new String[array.size()]);
	}

	public boolean close() {
		if (images != null) {
			for (int i = 0; i < images.length; i++)
				images[i].dispose();
		}
		return super.close();
	}

	protected Control createDialogArea(Composite parent) {
		// Composite parent = (Composite) super.createDialogArea(area);
		Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = 300;
		composite.setLayoutData(gridData);

		Label accountLabel = new Label(composite, SWT.NONE);
		accountLabel.setText("Account details:");
		accountLabel.setLayoutData(new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false, 2, 1));

		Label userIdLabel = new Label(composite, SWT.NONE);
		userIdLabel.setText("&User ID:");
		userIdLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));

		
		userIdText = new Text(composite, SWT.BORDER);
		// gridData.widthHint = convertHeightInCharsToPixels(20);
		userIdText.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, false));
		
		userIdText.setEditable(false);

		Label serverLabel = new Label(composite, SWT.NONE);
		serverLabel.setText("&Server:");
		serverLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));

		serverText = new Text(composite, SWT.BORDER);
		serverText.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, false));
		serverText.setText("http://ceplx265:8080/de.femodeling.e4.server");

		Label passwordLabel = new Label(composite, SWT.NONE);
		passwordLabel.setText("&Password:");
		passwordLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		passwordLabel.setVisible(false);


		final Button autoLogin = new Button(composite, SWT.CHECK);
		autoLogin.setText("Login &automatically at startup");
		autoLogin.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true,
				true, 2, 1));
		autoLogin.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				prefs.putBoolean(GeneralPreferences.AUTO_LOGIN,
						autoLogin.getSelection());

				try {
					prefs.flush();
					System.out.println("Auto Login: "
							+ autoLogin.getSelection());
				} catch (BackingStoreException ex) {
					System.out.println(ex);
				}

			}
		});
		if(auto_login!=null)
			autoLogin.setSelection(auto_login);
		if(server!=null && !server.isEmpty())
			serverText.setText(server);
		
		if (showError) {
			setErrorMessage("Authentication exception!\nPlease check the server URI or user"); //$NON-NLS-1$
		} else {
			setMessage("Please enter the server name."
					+ "\nDefault: "
					+ "http://ceplx265:8080/de.femodeling.e4.server"); //$NON-NLS-1$
		}
		setTitle("Porsche FE-Modeling Client Login"); //$NON-NLS-1$
		setHelpAvailable(false);
		
		setTitleImage(loader.loadImage(getClass(), IImageKeys.LOGIN_DIALOG));
		
		
		// Set the user name
		userIdText.setText(Utils.getUserName());
		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, "&Login", true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

	}

	

	protected void okPressed() {
		if (connectionDetails.getUserId().equals("")) {
			MessageDialog.openError(getShell(), "Invalid User ID",
					"User ID field must not be blank.");
			return;
		}
		if (connectionDetails.getServer().equals("")) {
			MessageDialog.openError(getShell(), "Invalid Server",
					"Server field must not be blank.");
			return;
		}
		super.okPressed();
	}

	protected void buttonPressed(int buttonId) {
		String userId = userIdText.getText();
		String server = serverText.getText();
		String password = "";
		connectionDetails = new ConnectionDetails(userId, server, password);
		
		if (buttonId == IDialogConstants.OK_ID
				|| buttonId == IDialogConstants.CANCEL_ID)
			saveDescriptors();
		super.buttonPressed(buttonId);
	}

	public void saveDescriptors() {
		try {
			// save the server
			prefs.put(SERVER,serverText.getText());
			prefs.flush();
		
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	
	

	/**
	 * Returns the connection details entered by the user, or <code>null</code>
	 * if the dialog was canceled.
	 */
	public ConnectionDetails getConnectionDetails() {
		if(connectionDetails==null){
			String userId,localserver;
			
			if(userIdText!=null && serverText!=null){
				userId = userIdText.getText();
				localserver = serverText.getText();
			}
			else{
				userId=Utils.getUserName();
				localserver=server;
			}
			connectionDetails = new ConnectionDetails(userId,localserver, "");
		}
		
		return connectionDetails;
	}
}
