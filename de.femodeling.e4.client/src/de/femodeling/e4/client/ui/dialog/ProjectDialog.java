package de.femodeling.e4.client.ui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.femodeling.e4.client.model.ProjectClientImpl;


public class ProjectDialog extends Dialog {
	
	
	public enum Type { CREATE, RENAME }
	private Type type;
	//private Shell shell;
	
	private String projectDirName;
	
	private ProjectClientImpl pro;
	ProjectClientImpl parent;
	
	//private Text text_Group;
	private Text text_Project_Name;
	//private Text text_HDBT_Name;
	//private Text text_Directory;
	private Button buttonOK;
	
	
	
	
	public String getProjectDirName() {
		return projectDirName;
	}

	public ProjectClientImpl getPro() {
		return pro;
	}
	
	public ProjectClientImpl getParentPro() {
		return parent;
	}

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ProjectDialog(Shell parentShell,ProjectClientImpl pro, Type type) {
		super(parentShell);
		
		this.type=type;
		
		if(this.type==Type.CREATE){
			this.pro=new ProjectClientImpl();
			this.parent=pro;
		}
		else if(this.type==Type.RENAME){
			this.pro=pro;
		}
		
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		
		/*
		Label lblGroup = new Label(container, SWT.NONE);
		lblGroup.setText("Group");
		new Label(container, SWT.NONE);
		
		text_Group = new Text(container, SWT.BORDER);
		text_Group.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				pro.setGroupName(text_Group.getText());
				if(buttonOK!=null)buttonOK.setEnabled(projectCanbeSaved());
				//System.out.println(text_Group.getText());
			}
		});
		text_Group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		*/
		
		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setText("Project Name");
		new Label(container, SWT.NONE);
		
		text_Project_Name = new Text(container, SWT.BORDER);
		text_Project_Name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				pro.setName(text_Project_Name.getText(),false);
				if(buttonOK!=null)buttonOK.setEnabled(projectCanbeSaved());
				//System.out.println(text_Project_Name.getText());
			}
		});
		text_Project_Name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		
		
		if(this.type==Type.RENAME){
			if(pro!=null)text_Project_Name.setText(pro.getName());
		}
		
		
		/*
		Label lblHdbt = new Label(container, SWT.NONE);
		lblHdbt.setText("HDBT Name");
		new Label(container, SWT.NONE);
		
		text_HDBT_Name = new Text(container, SWT.BORDER);
		text_HDBT_Name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				pro.setMaturityLevel(text_HDBT_Name.getText());
				if(buttonOK!=null)buttonOK.setEnabled(projectCanbeSaved());
				//System.out.println(text_HDBT_Name.getText());
			}
		});
		text_HDBT_Name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		
		Label lblDirectory = new Label(container, SWT.NONE);
		lblDirectory.setText("Directory");
		new Label(container, SWT.NONE);
		
		
		text_Directory = new Text(container, SWT.BORDER);
		text_Directory.setEditable(false);
		text_Directory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//FileDialog fDialog=new FileDialog(shell);
				//fDialog.setText("Please select the directory to save the project data");
				
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);
        
        		directoryDialog.setFilterPath(projectDirName);
        		directoryDialog.setMessage("Please select a directory and click OK");
        
        		String dir = directoryDialog.open();
        		if(dir != null) {
        			text_Directory.setText(dir);
        			projectDirName = dir;
        			if(buttonOK!=null)buttonOK.setEnabled(projectCanbeSaved());
        		}
				
				
			}
		});
		
		btnNewButton.setToolTipText("select the project directory");
		btnNewButton.setText("Browse");
		*/
		
		
/*
		if(parent.getParent()==null || parent.getParent().getName().equals("RootGroup")){
			text_Group.setText(parent.getName());
			System.out.println("Parent null");
		}
		else{
			ProjectsGroup parentGroup=parent.getParent();
			text_Group.setText(parentGroup.getName());
			text_Project_Name.setText(parent.getName());
		}
*/		
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		buttonOK = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		buttonOK.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(300, 150);
	}
	
	
	private boolean projectCanbeSaved(){
		if(pro!=null /* && pro.isValidToSave()*/){
			return true;
			/*
			if(projectDirName!=null && !projectDirName.isEmpty()){
				File f =new File(projectDirName);
				return f.isDirectory();
			}
			*/
			
		}
		return false;
	}
	
}
