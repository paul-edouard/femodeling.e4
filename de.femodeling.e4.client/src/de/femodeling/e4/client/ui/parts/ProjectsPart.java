 
package de.femodeling.e4.client.ui.parts;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.model.listener.ProjectClientListenerIF;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.client.ui.contentprovider.ProjectsTreeContentProvider;
import de.femodeling.e4.client.ui.contentprovider.ProjectsTreeContentProvider.ProjectTreeContent;
import de.femodeling.e4.client.ui.labelprovider.ProjectsTreeLabelProvider;



public class ProjectsPart {
	
	@Inject
	Shell shell;
	
	@Inject
	IProjectProvider provider;
	
	@Inject
	IEclipseContext context;
	
	@Inject
	IBundleResourceLoader loader;
	
	@Inject
	ESelectionService selectionService;
	
	@Inject
	EPartService service;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	private MApplication app;
	
	private TreeViewer treeViewer;
	
	private ProjectClientImpl root;
	
	public static final String PART_EDITOR_ID="de.femodeling.e4.client.partdescriptor.projecteditor";
	
	
	@Inject
	public ProjectsPart() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent, EMenuService menuService) {
		
		root=provider.getRoot();
		
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				
				ISelection selection=event.getSelection();
				selectionService.setSelection(selection);
				
				
			}
		});
		
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				//System.out.println("Double Click!");
				
				ISelection selection =treeViewer.getSelection();
				if (selection != null & selection instanceof IStructuredSelection) {
					IStructuredSelection strucSelection = (IStructuredSelection) selection;
					Object item =strucSelection.getFirstElement();
				
					
					if(item instanceof ProjectTreeContent){
						ProjectTreeContent p_content=(ProjectTreeContent) item;
						ProjectClientImpl pro =p_content.getParent();
						
						System.out.println("Double Click on: "+p_content.getName());
						
						//"Translation"
						if(p_content.getName().equals("Translation")){
							/*
							TranslationEditorInput input=new TranslationEditorInput(pro);
							try {
								page.openEditor(input, TranslationEditor.ID);
							} catch (PartInitException e) {
								MessageDialog.openWarning(shell, "Translation Editor Error", "Cannot open the translation editor");
								e.printStackTrace();
							}
							*/
							
						}
						//"Connections"
						else if(p_content.getName().equals("Connections")){
							/*
							ConnectionEditorInput input=new ConnectionEditorInput(pro);
							try {
								page.openEditor(input, ConnectionEditor.ID);
							} catch (PartInitException e) {
								MessageDialog.openWarning(shell, "Connection Editor Error", "Cannot open the connection editor");
								e.printStackTrace();
							}
							*/
							
						}
						//"FE-Module"
						else if(p_content.getName().equals("FE-Module")){
							/*
							FeModuleEditorInput input=new FeModuleEditorInput(pro);
							try {
								page.openEditor(input, FeModuleEditor.ID);
							} catch (PartInitException e) {
								MessageDialog.openWarning(shell, "FE-Module Editor Error", "Cannot open the fe module editor");
								e.printStackTrace();
							}
							*/
							
						}
						//"Assembly"
						else if(p_content.getName().equals("Assembly")){
							/*
							AssemblyEditorInput input=new AssemblyEditorInput(pro);
							try {
								page.openEditor(input, AssemblyEditor.ID);
							} catch (PartInitException e) {
								MessageDialog.openWarning(shell, "Assembly Editor Error", "Cannot open the assembly editor");
								e.printStackTrace();
							}
							*/
							
						}
						
						
						
					}
					//Opent the project Editor
					else if(item instanceof ProjectClientImpl){
						
						OpenProjectEditor((ProjectClientImpl) item);
						
					}
					
				}
				
			}
		});
		
		treeViewer.setContentProvider(ContextInjectionFactory.make( ProjectsTreeContentProvider.class,context));
		treeViewer.setLabelProvider(new ProjectsTreeLabelProvider(loader));
		treeViewer.setInput(root);
		
		menuService.registerContextMenu(treeViewer.getTree(), "de.femodeling.e4.client.popupmenu.project");
		
		
		//ViewerSupport.
		root.addProjectsListener(new ProjectClientListenerIF() {
			public void projectsChanged(ProjectClientImpl parent,
					ProjectClientImpl entry) {
				
			
				Display display=shell.getDisplay();
				
				display.asyncExec(new Runnable() {
					
					@Override
					public void run() {
						//logger.info("Root Project Changed!!");
						treeViewer.refresh();
					}
				});
				
			
			}
		});
		
	}
	
	
	private void OpenProjectEditor(ProjectClientImpl selectedProject){
		
		MPart part=searchPart(PART_EDITOR_ID,selectedProject.getLockableId());
		if(part!=null){
			service.bringToTop(part);
			return;
		}
		
		
		part = service
				.createPart(PART_EDITOR_ID);
		part.setLabel(selectedProject.getName());
		part.setVisible(true);
		part.getTags().add(selectedProject.getLockableId());
		
		addProjectToPartContext(part,selectedProject);
		
		//Open the part
		service.showPart(part, PartState.ACTIVATE);
		
	}
	
	private MPart searchPart(String partId,String tag){
		
		List<String> tags=new LinkedList<String>();
		tags.add(tag);
		
		List<MPart> parts=modelService.findElements(app,
				partId, MPart.class,tags );
		if(parts.isEmpty())return null;
		return parts.get(0);
	}
	
	private void addProjectToPartContext(MPart part,ProjectClientImpl o){
		
		//Add the project to the part contact
		IEclipseContext myContext=EclipseContextFactory.create();
		myContext.set(ProjectClientImpl.class, o);
		myContext.setParent(part.getContext());
		part.setContext(myContext);
		
	}
	
	
	
	@PreDestroy
	public void preDestroy() {
		//TODO Your code here
	}
	
	
	@Focus
	public void onFocus() {
		treeViewer.getTree().setFocus();
	}
	
	
}