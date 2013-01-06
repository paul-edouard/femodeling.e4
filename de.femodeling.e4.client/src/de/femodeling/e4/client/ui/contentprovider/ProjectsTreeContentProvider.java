package de.femodeling.e4.client.ui.contentprovider;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.femodeling.e4.client.model.ProjectClientImpl;
import de.femodeling.e4.client.service.IProjectProvider;
import de.femodeling.e4.model.core.assembly.Assembly;
import de.femodeling.e4.model.core.part.Part;



public class ProjectsTreeContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {
	
	
	//private static Logger logger = Logger.getLogger(ProjectsTreeContentProvider.class);
	
	@Inject
	IProjectProvider ProjectProvider;
	
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
		if(parentElement instanceof ProjectClientImpl){
			ProjectClientImpl p=(ProjectClientImpl) parentElement;
			List<Object> c_l=new LinkedList<Object>();
			c_l.addAll(p.getChilds());
			
			if(ProjectProvider.getNumberOfParts(p, Part.Type.PARTIAL_MODEL)>0){
				ProjectTreeContent ass=new ProjectTreeContent("Assembly",p);
				ProjectTreeContent trans=new ProjectTreeContent("Translation",p);
				c_l.add(ass);
				c_l.add(trans);
			}
			
			if(ProjectProvider.hasAssembly(p, Assembly.Type.FE_MODULE)){
				ProjectTreeContent fe=new ProjectTreeContent("FE-Module",p);
				c_l.add(fe);
			}
			if(ProjectProvider.hasConnections(p)){
				ProjectTreeContent conn=new ProjectTreeContent("Connections",p);
				c_l.add(conn);
			}
			
			//logger.info("Project: "+p.getName()+" Childs: "+c_l.size());
			
			return  c_l.toArray();
		}
		
		return null;
	}
	
	public class ProjectTreeContent{
		
		protected String name;
		protected ProjectClientImpl parent;
		
		public ProjectTreeContent(String name, ProjectClientImpl parent){
			this.name=name;
			this.parent=parent;
		}
		
		public String getName() {
			return name;
		}
		public ProjectClientImpl getParent() {
			return parent;
		}
		
		
	}
	
	
	@Override
	public Object getParent(Object element) {
		if(element instanceof ProjectClientImpl){
			return ((ProjectClientImpl) element).getParent();
		}
		else if(element instanceof ProjectTreeContent){
			return ((ProjectTreeContent) element).getParent();
		}
		else
			return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof ProjectClientImpl){
			ProjectClientImpl p=(ProjectClientImpl) element;
			
			if(!p.getChilds().isEmpty())return true;
			else if(ProjectProvider.hasAssembly(p, Assembly.Type.PDMU))return true;
			else if(ProjectProvider.hasConnections(p))return true;
			else return false;
			
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof ProjectClientImpl){
			return this.getChildren(inputElement);
		}
		return null;
	}

}
