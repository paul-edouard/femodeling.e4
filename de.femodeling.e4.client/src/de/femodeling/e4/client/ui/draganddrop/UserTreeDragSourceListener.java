package de.femodeling.e4.client.ui.draganddrop;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;

import de.femodeling.e4.client.model.UserClientImpl;

public class UserTreeDragSourceListener implements DragSourceListener {
	
	private final TreeViewer treeViewer;
	
	
	public UserTreeDragSourceListener(TreeViewer treeViewer) {
		super();
		this.treeViewer = treeViewer;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		System.out.println("Start Drag");
	}
	
	
	
	@Override
	public void dragSetData(DragSourceEvent event) {
		// Here you do the convertion to the type which is expected.
	    IStructuredSelection selection = (IStructuredSelection) treeViewer
	    .getSelection();
	    if(selection.getFirstElement() instanceof UserClientImpl){
	    	UserClientImpl firstElement = (UserClientImpl) selection.getFirstElement();
	    
	    	if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
	    		event.data = firstElement.getId(); 
	    	}
	    }

	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		System.out.println("Finshed Drag");
	}

}
