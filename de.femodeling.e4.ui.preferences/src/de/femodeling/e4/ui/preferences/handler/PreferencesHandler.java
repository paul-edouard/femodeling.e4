package de.femodeling.e4.ui.preferences.handler;
 

import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.ui.preferences.dialog.MyPreferenceDialog;




public class PreferencesHandler {
	
	
	private static final String PREFERENCESPAGES_ID="de.femodeling.e4.ui.preferences.preferencePages";
	
	private PreferenceManager manager;
	
	@Inject
	IBundleResourceLoader loader;
	
	@Inject
	IEclipseContext parent;
	
	
	@Execute
	public void execute(Shell shell, IExtensionRegistry registery) {
		
		
		manager=new PreferenceManager();
		
		evaluate(registery);
		
		MyPreferenceDialog dialog=new MyPreferenceDialog(shell,manager);
		
		dialog.open();
		
		
	}
	
	
	@CanExecute
	public boolean canExecute() {
		//TODO Your code goes here
		return true;
	}
	
	
	private void evaluate(IExtensionRegistry registery){
		IConfigurationElement[] elements=registery.
				getConfigurationElementsFor(PREFERENCESPAGES_ID);
		
		
		for(IConfigurationElement e:elements){
			System.out.println("Evaluating extension");
			
			try {
				final Object o =e.createExecutableExtension("class");
				
				Object page =ContextInjectionFactory.make(o.getClass(), parent);
				
				
				String id=e.getAttribute("id");
				String imagePath=e.getAttribute("image");
				String tree_iconPath=e.getAttribute("tree_icon");
				String name=e.getAttribute("name");
				
				if(page instanceof PreferencePage){
					System.out.println("Add a preference page:" +page.getClass());
					addPreferencePage((PreferencePage)page,id,imagePath,name,tree_iconPath);
				}
				
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	
	private void addPreferencePage(PreferencePage page,String id,String imagePath, String name,String tree_iconPath){
		
		ImageDescriptor image=loader.loadImageDescriptor(page.getClass(), imagePath);
		ImageDescriptor tree_icon=loader.loadImageDescriptor(page.getClass(), tree_iconPath);
		
		
		//page.setDescription(name);
		if(image!=null)
		page.setImageDescriptor(image);
		page.setTitle(name);
		
		PreferenceNode node=new PreferenceNode(id, name,tree_icon,null);
		node.setPage(page);
		
		manager.addToRoot(node);
		
		
	}
	
		
}