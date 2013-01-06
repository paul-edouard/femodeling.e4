 
package de.femodeling.e4.client.ui.parts;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.core.UserClientGroup;
import de.femodeling.e4.client.ui.contentprovider.UsersTreeContentProvider;
import de.femodeling.e4.client.ui.dialog.UserDialog;
import de.femodeling.e4.client.ui.labelprovider.UsersTreeLabelProvider;
import de.femodeling.e4.client.ui.sorter.UsersTreeSorter;


public class UsersPart {
	
	
	private UserClientGroup rootGroup;
	private TreeViewer treeViewer;
	private DataBindingContext m_bindingContext;
	

	
	
	@Inject
	public UsersPart() {
		rootGroup = new UserClientGroup("root");
	}
	
	@PostConstruct
	public void postConstruct(Composite parent,Shell shell) {

		refreshUsers();

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		treeViewer = new TreeViewer(container, SWT.BORDER);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection != null
						& selection instanceof IStructuredSelection) {
					IStructuredSelection strucSelection = (IStructuredSelection) selection;
					Object item = strucSelection.getFirstElement();
					if (strucSelection.size() == 1
							&& item instanceof UserClientImpl) {
						//TODO Set enable if user is admin
						//DeleteUser.setEnabled(true);
						return;
					}
				}
				//DeleteUser.setEnabled(false);
			}
		});
		treeViewer.setSorter(new UsersTreeSorter());
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection != null
						& selection instanceof IStructuredSelection) {
					IStructuredSelection strucSelection = (IStructuredSelection) selection;
					Object item = strucSelection.getFirstElement();

					if (item instanceof UserClientImpl) {
						UserClientImpl selectedUser = (UserClientImpl) item;
						UserClientImpl copy = selectedUser.createCopy();

						UserDialog d = new UserDialog(shell, selectedUser);

						int code = d.open();

						if (code == Window.OK) {
							RegisterClientService.INSTANCE
									.getUserClientService().saveUser(
											selectedUser);
							treeViewer.refresh();
						} else {
							selectedUser.copyData(copy);
							treeViewer.refresh();
						}

					}

				}
			}
		});
		Tree tree = treeViewer.getTree();
		
		treeViewer.setLabelProvider(new UsersTreeLabelProvider());
		treeViewer.setContentProvider(new UsersTreeContentProvider());
		treeViewer.setInput(rootGroup);

		createActions();
		initializeToolBar();
		initializeMenu();
		m_bindingContext = initDataBindings();
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
	
	
	private void refreshUsers() {

		rootGroup.clearList();

		Set<UserClientImpl> users = RegisterClientService.INSTANCE
				.getUserClientService().getAllUsers();
		UserClientGroup allUsers = new UserClientGroup("All");
		for (UserClientImpl user : users) {
			allUsers.addUser(user);
		}

		Set<String> onlineUserIds = RegisterClientService.INSTANCE
				.getUserClientService().getOnlineUserIds();
		for (UserClientImpl user : users) {
			if (onlineUserIds.contains(user.getId()))
				user.setOnline(true);
		}

		rootGroup.addGroup(allUsers);

	}
	
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		return bindingContext;
	}
	
	
	
	
}