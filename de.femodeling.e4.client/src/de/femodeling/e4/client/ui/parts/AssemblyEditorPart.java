package de.femodeling.e4.client.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AssemblyEditorPart {
	private Text text;

	public AssemblyEditorPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		
		text = new Text(parent, SWT.BORDER);
		
		Composite composite_1 = new Composite(parent, SWT.NONE);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setSize(85, 294);
		scrolledComposite.setContent(composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setSize(74, 298);
		lblNewLabel.setText("New Label");
		
		
		
		Label lblNewLabel_2 = new Label(parent, SWT.NONE);
		lblNewLabel_2.setBounds(0, 0, 55, 15);
		lblNewLabel_2.setText("New Label");
		
		
		
		Label lblNewLabel_1 = new Label(scrolledComposite, SWT.NONE);
		lblNewLabel_1.setBounds(0, 0, 55, 15);
		lblNewLabel_1.setText("New Label");
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}

}
