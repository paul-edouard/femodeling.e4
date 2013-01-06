
package de.femodeling.e4.client.ui.preferences;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.osgi.service.prefs.BackingStoreException;




public class GeneralPreferencePage extends FieldEditorPreferencePage  {
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.AUTO_LOGIN)
	Boolean auto_login;
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client", value = GeneralPreferences.WORKING_DIR)
	String workDir;
	
	@Inject
	@Preference(nodePath = "de.femodeling.e4.client")
	IEclipsePreferences prefs;
	
	
	private IPreferenceStore preferences=new PreferenceStore("temp");
	
	private DirectoryFieldEditor dirEditor;
	
	@Inject
	public GeneralPreferencePage() {
		super(GRID);
		
		this.setDescription("General properties:\n");
		
		
	}

	
	
	public void init(IWorkbench workbench) {
	}

	protected void createFieldEditors() {
		
		if(auto_login!=null)
			preferences.setValue(GeneralPreferences.AUTO_LOGIN, auto_login);
		if(workDir!=null)
			preferences.setValue(GeneralPreferences.WORKING_DIR, workDir);
		
		
		preferences.setDefault(GeneralPreferences.AUTO_LOGIN, false);
		preferences.setDefault(GeneralPreferences.WORKING_DIR,GeneralPreferences.INSTANCE.getCreateDefaultDirname() );
		
		setPreferenceStore(preferences);
		
		BooleanFieldEditor boolEditor = new BooleanFieldEditor(GeneralPreferences.AUTO_LOGIN,
				"Login automatically at startup", getFieldEditorParent());
		addField(boolEditor);
		
		dirEditor=new DirectoryFieldEditor(GeneralPreferences.WORKING_DIR, "Workspace", getFieldEditorParent());
		/*
		dirEditor.getTextControl(null).addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				System.out.println(dirEditor.getStringValue());
			}
		});
		*/
		addField(dirEditor);
		
		
	}
	
	
	@Override
	public boolean isValid() {
		
		//System.out.println(dirEditor.getStringValue());
		
		return super.isValid();
	}
	
	



	@Override
	protected void checkState() {
		System.out.println(dirEditor.getStringValue());
		
		super.checkState();
	}



	public boolean performOk() {
		
		boolean value=super.performOk();
		
		try {
			
			
			prefs.putBoolean(GeneralPreferences.AUTO_LOGIN,
					preferences.getBoolean(GeneralPreferences.AUTO_LOGIN));
			
			prefs.put(GeneralPreferences.WORKING_DIR,
					preferences.getString(GeneralPreferences.WORKING_DIR));
			
			
			prefs.flush();
			
		} catch (BackingStoreException ex) {
			ex.printStackTrace();
			return false;
		}
		
	
		
		return value;
		
		
		
	}
	
}
