package de.femodeling.e4.client.ui.labelprovider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.ui.IImageKeys;
import de.femodeling.e4.client.ui.contentprovider.ProjectsTreeContentProvider.ProjectTreeContent;

public class ProjectsTreeLabelProvider implements ILabelProvider {
	
	
	//@Inject
	//IBundleResourceLoader loader;
	
	private Image projectImage;
	private Image projectLockedImage;

	private Image assemblyImage;
	private Image feModuleImage;
	private Image connectionImage;
	private Image translationImage;

	
	public ProjectsTreeLabelProvider(IBundleResourceLoader loader) {
		super();
		
		projectImage=loader.loadImage(getClass(), IImageKeys.PROJECT);
		projectLockedImage=loader.loadImage(getClass(), IImageKeys.PROJECT_LOCK);
		
		assemblyImage=loader.loadImage(getClass(), IImageKeys.PART_GROUP);
		feModuleImage=loader.loadImage(getClass(), IImageKeys.GROUP);
		connectionImage=loader.loadImage(getClass(), IImageKeys.CONNECTION);
		translationImage=loader.loadImage(getClass(), IImageKeys.CONNECTION);
		
		
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof ProjectClientImpl){
			ProjectClientImpl proj=(ProjectClientImpl) element;
		
					if(!proj.islocked()) return projectImage;
					else 				return projectLockedImage;
		
			
		}
		else if(element instanceof ProjectTreeContent){
			ProjectTreeContent p_content=(ProjectTreeContent) element;
			if(p_content.getName().equals("Assembly"))return assemblyImage;
			else if(p_content.getName().equals("Translation"))return translationImage;
			else if(p_content.getName().equals("FE-Module"))return feModuleImage;
			else if(p_content.getName().equals("Connections"))return connectionImage;
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof ProjectClientImpl){
			ProjectClientImpl proj=(ProjectClientImpl) element;
			
				return proj.getName();
			
			
		}
		else if(element instanceof ProjectTreeContent){
			return ((ProjectTreeContent) element).getName();
		}
		return null;
	}
	
}
