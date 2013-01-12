package de.femodeling.e4.ui.dataprovider.processors;
 

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import de.femodeling.e4.ui.dataprovider.registery.IRegistery;

public class DataProviderProcessor {
	@Execute
	public void execute(IRegistery registery,IEclipseContext parent) {
		System.out.println("Starting Processor: "+this.getClass());
		registery.initialize(parent);
	}
		
}