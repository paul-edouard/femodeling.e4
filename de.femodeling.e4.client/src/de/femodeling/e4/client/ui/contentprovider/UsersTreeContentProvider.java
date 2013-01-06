package de.femodeling.e4.client.ui.contentprovider;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.client.model.core.UserClientGroup;


public class UsersTreeContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {
	
	private static Logger logger = Logger.getLogger(ProjectsTreeContentProvider.class);
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof UserClientImpl){
			return null;
		}
		else if(parentElement instanceof  UserClientGroup){
			UserClientGroup group =(UserClientGroup) parentElement;
			return group.getContentList().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof UserClientImpl){
			UserClientImpl user=(UserClientImpl) element;
			return user.getParent();
		}
		else if(element instanceof  UserClientGroup){
			UserClientGroup group =(UserClientGroup) element;
			return group.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof UserClientImpl){
			return false;
		}
		else if(element instanceof  UserClientGroup){
			UserClientGroup group =(UserClientGroup) element;
			return !group.getContentList().isEmpty();
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof  UserClientGroup){
			return this.getChildren(inputElement);
		}
		else if(inputElement instanceof  UserClientImpl){
			return this.getChildren(inputElement);
		}
		return null;
	}

}
