 
package de.femodeling.e4.client.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import de.femodeling.e4.client.model.ProjectClientImpl;

public class ProjectEditorPart {
	private Text textName;
	private Text textCreationUser;
	private Text textCreatedDate;
	private Combo comboType;
	private Combo comboGroup;
	private Combo comboState;
	private List listPermitedUser;
	
	@Inject
	private ProjectClientImpl currentPro;
	
	private ProjectClientImpl copyPro;
	
	
	
	@Inject
	public ProjectEditorPart() {
		

	}
	
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		
		//Create a copy of the project!
		copyPro=currentPro.createCopy();
		
		parent.setLayout(new GridLayout(1, false));
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setImage(ResourceManager.getPluginImage("de.femodeling.e4.client", "icons/monitor.png"));
		tbtmNewItem.setToolTipText("Show the basis parameters of the project");
		tbtmNewItem.setText("Overview");
		
		
		Composite compositeOverview = new Composite(tabFolder, SWT.NONE);
		compositeOverview.setEnabled(false);
		compositeOverview.setBackground(SWTResourceManager.getColor(240, 255, 255));
		tbtmNewItem.setControl(compositeOverview);
		compositeOverview.setLayout(new GridLayout(1, false));
		
		
		Label lblName = new Label(compositeOverview, SWT.NONE);
		lblName.setBackground(SWTResourceManager.getColor(240, 255, 255));
		lblName.setText("Name:");
		
		textName = new Text(compositeOverview, SWT.BORDER);
		GridData gd_textName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textName.widthHint = 236;
		textName.setLayoutData(gd_textName);
		
		Group grpCreation = new Group(compositeOverview, SWT.NONE);
		grpCreation.setBackground(SWTResourceManager.getColor(240, 255, 255));
		grpCreation.setLayout(new GridLayout(2, false));
		grpCreation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpCreation.setText("Creation");
		
		Label lblCreationDate = new Label(grpCreation, SWT.NONE);
		lblCreationDate.setText("Date:");
		
		Label lblUser = new Label(grpCreation, SWT.NONE);
		lblUser.setText("User:");
		
		textCreatedDate = new Text(grpCreation, SWT.BORDER);
		textCreatedDate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		textCreatedDate.setEditable(false);
		
		textCreationUser = new Text(grpCreation, SWT.BORDER);
		textCreationUser.setEditable(false);
		textCreationUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblType = new Label(compositeOverview, SWT.NONE);
		lblType.setText("Type:");
		
		comboType = new Combo(compositeOverview, SWT.NONE);
		comboType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblGroup = new Label(compositeOverview, SWT.NONE);
		lblGroup.setText("Group:");
		
		comboGroup = new Combo(compositeOverview, SWT.NONE);
		comboGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblState = new Label(compositeOverview, SWT.NONE);
		lblState.setText("State");
		
		comboState = new Combo(compositeOverview, SWT.NONE);
		comboState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPermitedUsers = new Label(compositeOverview, SWT.NONE);
		lblPermitedUsers.setImage(ResourceManager.getPluginImage("de.femodeling.e4.client", "icons/group.png"));
		lblPermitedUsers.setText("Permited users:");
		
		ListViewer listViewer = new ListViewer(compositeOverview, SWT.BORDER | SWT.V_SCROLL);
		listPermitedUser = listViewer.getList();
		listPermitedUser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		TabItem tbtmData = new TabItem(tabFolder, SWT.NONE);
		tbtmData.setImage(ResourceManager.getPluginImage("de.femodeling.e4.client", "icons/database_table.png"));
		tbtmData.setText("Data");
		
		TabItem tbtmStatistic = new TabItem(tabFolder, SWT.NONE);
		tbtmStatistic.setImage(ResourceManager.getPluginImage("de.femodeling.e4.client", "icons/chart_curve.png"));
		tbtmStatistic.setText("Statistic");
		//TODO Your code here
	}
	
	
	@PreDestroy
	public void preDestroy() {
		//TODO Your code here
	}
	
	
	@Focus
	public void onFocus() {
		//TODO Your code here
	}
	
	
	@Persist
	public void save() {
		//TODO Your code here
	}
}