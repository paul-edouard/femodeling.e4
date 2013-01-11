 
package de.femodeling.e4.client.ui.parts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.broker.IBrokerEvents;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.service.IUserProvider;
import de.femodeling.e4.client.ui.contentprovider.UsersTreeContentProvider;
import de.femodeling.e4.client.ui.dialog.UserDialog;
import de.femodeling.e4.client.ui.draganddrop.UserTreeDragSourceListener;
import de.femodeling.e4.client.ui.draganddrop.UserTreeViewerDropAdapter;
import de.femodeling.e4.client.ui.labelprovider.UsersTreeLabelProvider;
import de.femodeling.e4.client.ui.sorter.UsersTreeSorter;
import de.femodeling.e4.model.core.User;


public class UsersPart {
	
	private static Logger logger = Logger.getLogger(UsersPart.class);
	
	private UserClientGroup rootGroup;
	private TreeViewer treeViewer;
	
	public static final String ROOT_GROUP="ROOT";
	public static final String ALL_USERS_GROUP="All";
	

	@Inject
	IEclipseContext context;
	
	@Inject
	IBundleResourceLoader loader;
	
	@Inject
	IUserProvider provider;
	
	@Inject
	Shell shell;
	
	@Inject
	ESelectionService selectionService;
	
	@Inject
	IEventBroker broker;
	
	
	@Inject
	public UsersPart() {
		rootGroup = UserClientGroup.createGroup(ROOT_GROUP);
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {

		refreshUsers();
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		treeViewer = new TreeViewer(container, SWT.BORDER);
		
		//Add Drag Support
		int operations = DND.DROP_COPY| DND.DROP_MOVE;
	    Transfer[] transferTypes = new Transfer[]{TextTransfer.getInstance()};
	    treeViewer.addDragSupport(operations, transferTypes , new UserTreeDragSourceListener(treeViewer));

		//Add Drop Support
	    treeViewer.addDropSupport(operations, transferTypes, new UserTreeViewerDropAdapter(treeViewer,provider,broker));
	    
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				
				ISelection selection=event.getSelection();
				selectionService.setSelection(selection);
				
			}
		});
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection != null
						& selection instanceof IStructuredSelection) {
					IStructuredSelection strucSelection = (IStructuredSelection) selection;
					Object item = strucSelection.getFirstElement();

					if (item instanceof UserClientImpl) {
						UserClientImpl selectedUser = (UserClientImpl) item;
						
						if(provider.getCurrentUser().hasRole(User.ADMIN) || 
								provider.getCurrentUser().getId().equals(selectedUser.getId())){
						
						
						UserClientImpl copy = selectedUser.createCopy();

						UserDialog d = new UserDialog(shell, selectedUser);

						int code = d.open();

						if (code == Window.OK) {
							provider.putData(selectedUser);
							refreshUsers();
							treeViewer.refresh();
							treeViewer.expandAll();
						} else {
							selectedUser.copyData(copy);
							treeViewer.refresh();
						}
						}

					}

				}
			}
		});
		
		
		//Tree tree = treeViewer.getTree();
		
		treeViewer.setLabelProvider(new UsersTreeLabelProvider(loader,provider));
		treeViewer.setContentProvider(new UsersTreeContentProvider());
		treeViewer.setSorter(new UsersTreeSorter());
		treeViewer.setInput(rootGroup);
		
		
		/*
		createActions();
		initializeToolBar();
		initializeMenu();
		*/
	}
	
	@Inject
	private void addUser(@Optional  @UIEventTopic(IBrokerEvents.USER_ADD)UserClientImpl u){
		
		if(treeViewer!=null){
			refreshUsers();
			treeViewer.refresh();
			treeViewer.expandAll();
		}
	}
	
	@Inject
	private void removeUser(@Optional  @UIEventTopic(IBrokerEvents.USER_REMOVE)UserClientImpl u){
		if(treeViewer!=null){
			refreshUsers();
			treeViewer.refresh();
			treeViewer.expandAll();
		}
	}
	
	@Inject
	private void updateUser(@Optional  @UIEventTopic(IBrokerEvents.USER_UPDATE)UserClientImpl u){
		if(treeViewer!=null){
			refreshUsers();
			treeViewer.refresh();
			treeViewer.expandAll();
		}
	}
	
	
	@Inject
	private void addGroup(@Optional  @UIEventTopic(IBrokerEvents.USER_GROUP_ADD)UserClientImpl u){
		
		if(treeViewer!=null){
			refreshUsers();
			treeViewer.refresh();
			treeViewer.expandAll();
		}
	}
	
	@Inject
	private void updateGroup(@Optional  @UIEventTopic(IBrokerEvents.USER_GROUP_UPDATE)UserClientImpl u){
		
		if(treeViewer!=null){
			refreshUsers();
			treeViewer.refresh();
			treeViewer.expandAll();
		}
	}
	
	
	@PreDestroy
	public void preDestroy() {
		//TODO Your code here
	}
	
	
	@Focus
	public void onFocus() {
		treeViewer.getTree().setFocus();
	}
	
	
	@Persist
	public void save() {
		//TODO Your code here
	}
	
	
	private void refreshUsers() {
		
		rootGroup.clearList();
		
		
		//USERS
		
		Collection<UserClientImpl> users = provider.getAllUsers();
		UserClientGroup allUsers = UserClientGroup.createGroup(ALL_USERS_GROUP);
		for (UserClientImpl user : users) {
			allUsers.addUser(user);
		}
		
		Set<String> onlineUserIds = provider.getOnlineUserIds();
		for (UserClientImpl user : users) {
			if (onlineUserIds.contains(user.getId())){
				user.setOnline(true);
			}
		}
		
		rootGroup.addGroup(allUsers);

		
		//GROUPS
		
		Collection<UserClientImpl> groups = provider.getAllGroups();
		//logger.debug("Number of groups: "+groups.size());
		Collection<UserClientGroup> allGroups=new HashSet<UserClientGroup>();
		for(UserClientImpl u:groups){
			UserClientGroup g=new UserClientGroup(u);
			allGroups.add(g);
		}
		
		for(UserClientGroup group:allGroups){
			group.initUserChilds(users);
			group.initGroupChilds(allGroups);
		}
		
		for(UserClientGroup group:allGroups){
			if(group.getParent()==null)
				rootGroup.addGroup(group);
		}
		
		
		
	}
	
	
	
	
	
}