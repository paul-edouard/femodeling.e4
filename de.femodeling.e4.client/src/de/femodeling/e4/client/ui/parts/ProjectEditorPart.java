 
package de.femodeling.e4.client.ui.parts;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.service.IClientService;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.model.core.Project;

public class ProjectEditorPart{
	private DataBindingContext m_bindingContext;
	
	
	/** This class' Logger instance. */
	private static Logger logger = Logger
			.getLogger(ProjectEditorPart.class);
	
	private Text textName;
	private Text textCreationUser;
	private Text textCreatedDate;
	private Combo comboType;
	private Combo comboGroup;
	private Combo comboState;
	private List listPermitedUser;
	
	@Inject
	private ProjectClientImpl currentPro;
	
	@Inject
	private ClientSession session;
	
	@Inject
	private IUserProvider userProvider;
	
	@Inject
	private IProjectProvider projectProvider;
	
	@Inject
	private MDirtyable dirty;
	
	@Inject
	private Shell shell;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	private IClientService clientService;
	
	
	
	private final class ProjectDirtyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			if (dirty != null) {
				dirty.setDirty(true);
			}
		}
	}
	
	private ProjectDirtyListener projectDirtyListener = new ProjectDirtyListener();
	
	
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
		compositeOverview.setBackground(SWTResourceManager.getColor(240, 255, 255));
		tbtmNewItem.setControl(compositeOverview);
		compositeOverview.setLayout(new GridLayout(1, false));
		
		
		Label lblName = new Label(compositeOverview, SWT.NONE);
		lblName.setBackground(SWTResourceManager.getColor(240, 255, 255));
		lblName.setText("Name:");
		
		textName = new Text(compositeOverview, SWT.BORDER);
		textName.setEnabled(true);
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
		textCreatedDate.setText(currentPro.getCreationDate());
		
		textCreationUser = new Text(grpCreation, SWT.BORDER);
		textCreationUser.setEditable(false);
		textCreationUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		UserClientImpl user=userProvider.getData(currentPro.getCreationUser());
		if(user!=null)
		textCreationUser.setText(user.getId()+": \""+user.getSurname()+", "+user.getForename()+"\"");
		
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
		comboState.setItems(Project.STATE_LIST);
		
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
		
		
		setEditable();
		m_bindingContext = initDataBindings();
		
		fillAndSetCombo();
		addDirtyableListener();
		
		dirty.setDirty(false);
	}
	
	
	@Inject
	private void updatePro(@Optional  @UIEventTopic(IBrokerEvents.PROJECT_UPDATE)String lockableId){
		//logger.info("Update project!!");
		if(textName==null || currentPro==null || lockableId==null)return;
		
		if(!lockableId.equals(this.currentPro.getLockableId()))return;
		
		setEditable();
	
	}
	
	
	
	private void setEditable(){
		
		boolean editable=currentPro.islocked() && session.getSessionId().equals(currentPro.getSessionId());
		
		//logger.info("Set editable");
		
		textName.setEditable(editable);
		comboType.setEnabled(false);
		comboGroup.setEnabled(editable);
		comboState.setEnabled(editable);
		listPermitedUser.setEnabled(editable);
		
	}
	
	private void addDirtyableListener(){
		textName.addModifyListener(projectDirtyListener);
		comboType.addModifyListener(projectDirtyListener);
		comboGroup.addModifyListener(projectDirtyListener);
		comboState.addModifyListener(projectDirtyListener);
	}
	
	private void fillAndSetCombo(){
		//Type
		comboType.setItems(Project.TYPE_LIST);
		comboType.setText(this.currentPro.typeToString());
		comboType.remove(Project.TYPE_LIST[0]);
		
		//State
		comboState.setItems(Project.STATE_LIST);
		comboState.setText(this.currentPro.stateToString());
		
		
		//Group
		Set<String> groups=new HashSet<String>();
		groups.addAll(userProvider.getCurrentUser().getGroups());
		if(currentPro.getGroup()!=null && !currentPro.getGroup().isEmpty())
			groups.add(currentPro.getGroup());
		String[] groupItems=new String[groups.size()];
		int i=0;
		for(String g:groups){
			groupItems[i]=g;i++;
		}
		
		comboGroup.setItems(groupItems);
		if(currentPro.getGroup()!=null)
		comboGroup.setText(currentPro.getGroup());
		
	}
	
	
	@PreDestroy
	public void preDestroy() {
		//TODO Your code here
	}
	
	
	@Focus
	public void onFocus() {
		textName.setFocus();
	}
	
	
	@Persist
	public void save() {
		
		//Save All changes
		copyPro.setSessionId(currentPro.getSessionId());
		copyPro.setGroup(comboGroup.getText());
		copyPro.stringToState(comboState.getText());
		copyPro.stringToType(comboType.getText());
		
		if(projectProvider.updateData(copyPro)!=null){
			dirty.setDirty(false);
			//currentPro.setSessionId();
			eventBroker.post(IBrokerEvents.PROJECT_UPDATE,copyPro.getLockableId() );
		}
		else{
			MessageDialog.openError(shell, "Save project Error", "Cannot save the project");
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextNameObserveWidget = WidgetProperties.text(SWT.Modify).observe(textName);
		IObservableValue nameCurrentProObserveValue = BeanProperties.value("name").observe(copyPro);
		bindingContext.bindValue(observeTextTextNameObserveWidget, nameCurrentProObserveValue, null, null);
		//
		return bindingContext;
	}
}