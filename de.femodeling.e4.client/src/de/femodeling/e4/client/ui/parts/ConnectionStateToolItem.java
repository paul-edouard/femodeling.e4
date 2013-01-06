 
package de.femodeling.e4.client.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;

import de.femodeling.e4.bundleresourceloader.IBundleResourceLoader;
import de.femodeling.e4.client.jobs.KeepAliveJob;
import de.femodeling.e4.client.jobs.KeepAliveJob.State;
import de.femodeling.e4.client.model.ClientSession;
import de.femodeling.e4.client.ui.IImageKeys;

public class ConnectionStateToolItem {
	

	@Inject
	IBundleResourceLoader loader;
	
	KeepAliveJob keepAliveJob;
	
	private State lastState;
	private ToolItem tltmDropdownItem;
	
	@Inject
	public ConnectionStateToolItem() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parent,IEclipseContext context) {
		System.out.println("Image fg null?: "+loader.loadImage(getClass(), IImageKeys.ONLINE)==null);
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		
		tltmDropdownItem = new ToolItem(toolBar, SWT.NONE);
		tltmDropdownItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		tltmDropdownItem.setHotImage(ResourceManager.getPluginImage("de.femodeling.e4.client", "icons/online.gif"));
		tltmDropdownItem.setImage(ResourceManager.getPluginImage("de.femodeling.e4.client", "icons/online.gif"));
		tltmDropdownItem.setText("Online");

		//start the keep alive job
		keepAliveJob=ContextInjectionFactory.make(KeepAliveJob.class, context);
	}
	
	
	@PreDestroy
	public void preDestroy(){
		if(keepAliveJob!=null)
		keepAliveJob.cancel();
	}
	
	@Inject
	private void setConnectionState(@Optional  @UIEventTopic(KeepAliveJob.ONLINE_STATE)State state, ClientSession session){
		if(tltmDropdownItem==null)return;
		
		if(lastState==null || lastState!=state ){
		
		switch (state) {
		case ONLINE:
			tltmDropdownItem.setText("Online");
			tltmDropdownItem.setToolTipText("Session: "+session.getSessionId()+"\nSever:"+session.getServer());
			break;
		case OFFLINE:
			tltmDropdownItem.setText("Offline");
			tltmDropdownItem.setToolTipText("");
			break;
			}
		
		}
		
		lastState=state;
		
	}
	
	
	
	@Focus
	public void onFocus() {
		tltmDropdownItem.getParent().setFocus();
	}
}