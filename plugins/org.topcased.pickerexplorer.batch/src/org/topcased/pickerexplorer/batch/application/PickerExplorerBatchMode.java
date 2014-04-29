/*****************************************************************************
 * Copyright (c) 2014 Atos.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 *****************************************************************************/
package org.topcased.pickerexplorer.batch.application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.topcased.pickerexplorer.batch.pickerbatch.Mapping;
import org.topcased.pickerexplorer.batch.pickerbatch.Picker;
import org.topcased.pickerexplorer.batch.pickerbatch.PickerBatchMappings;
import org.topcased.pickerexplorer.ui.builder.IPickerRunnable;
import org.topcased.pickerexplorer.ui.builder.PickerExplorerBuilder;
import org.topcased.pickerexplorer.ui.elements.RootElement;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;

@SuppressWarnings("restriction")
public class PickerExplorerBatchMode implements IApplication {

	private static final String OUTPUT = "output";

	private static final String MODEL = "model";

	private static final String MAXDEPTH = "maxDepth";
	
	private static final String VERBOSE = "verbose";



	private ArgOpt modelArg = new ArgOpt(MODEL, ArgOpt.REQUIRED_ARGUMENT, ArgOpt.REQUIRED_ARGUMENT_VALUE, "mdl", "Picker Batch UML model ");

	private ArgOpt outputArg = new ArgOpt(OUTPUT, ArgOpt.REQUIRED_ARGUMENT, ArgOpt.REQUIRED_ARGUMENT_VALUE, "out", "Output file complete path ");

	private ArgOpt maxDepthArg = new ArgOpt(MAXDEPTH, ArgOpt.OPTIONAL_ARGUMENT, ArgOpt.REQUIRED_ARGUMENT_VALUE, "max", "Max depth");
	
	private ArgOpt verboseArg = new ArgOpt(VERBOSE, ArgOpt.OPTIONAL_ARGUMENT, ArgOpt.NO_ARGUMENT_VALUE, "vrb", "Verbose");
	private boolean verbose;

	/**
	 * The extracted parameters
	 */
	private HashMap<String, String> parameters;

	private boolean validArguments = true;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		Display.getDefault().wake();
		setupWorkbench();

		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);

		ArgOpt[] listOpts = {modelArg, outputArg, maxDepthArg, verboseArg};
		// All the parameters are collected
		parameters = checkAndLoadParam(args, listOpts);

		if(validArguments)
		{
			String modelStringUri = parameters.get(MODEL);
			URI modelUri = URI.createURI(modelStringUri);

			// The max depth is collected from the arguments
			int maxDepth = 1;
			String maxDepthString = parameters.get(MAXDEPTH);
			if(maxDepthString != null){
				try
				{
					maxDepth = Integer.parseInt(maxDepthString);
				}
				catch(NumberFormatException e)
				{
				}
			}
			
			// The max depth is collected from the arguments
			verbose = false;
			if(parameters.get(VERBOSE) != null){
				verbose = true;
			}

			PickerExplorerBuilder pickerBuilder = new PickerExplorerBuilder();

			ResourceSet set = new ResourceSetImpl();
			Resource modelResource = set.getResource(modelUri,true);
			if(modelResource.getContents().size() > 0 && modelResource.getContents().get(0) instanceof PickerBatchMappings){
				// All the elements and all the pickers are added to the builder
				PickerBatchMappings pickerBatchMappings = (PickerBatchMappings)modelResource.getContents().get(0);
				for(Mapping mapping:pickerBatchMappings.getMappings()){
					pickerBuilder.addElement(mapping.getEObject());
					if(verbose) System.out.println(mapping.getEObject().toString() + " element added");
					for(Picker picker:mapping.getPickers()){
						pickerBuilder.addPicker(picker.getPickerId());
						if(verbose) System.out.println(picker.getPickerId() + " picker added");
					}
				}
			}

			pickerBuilder.setAllPickersEnabledByDefault(true);
			// Only the pickers from the model should be taken into account 
			pickerBuilder.setRetrieveRegisteredPickers(false);
			pickerBuilder.setMaxDepth(maxDepth+1); // +1 because the rootElement is skipped
			pickerBuilder.setExploreNonCachedChildren(true);
			pickerBuilder.run(new IPickerRunnable() {

				@Override
				public void run(RootElement root,
						PickerExplorerElementVisitor visitor,
						Map<String, Object> params) {
					System.out.println("Generation...");
					visitor.setVerbose(verbose);
					root.accept(visitor, -1);
					PrintWriter file = null;
					try
					{
						file = new PrintWriter(new BufferedWriter
								(new FileWriter(parameters.get(OUTPUT))));
						file.println(visitor.getModelTest());
						System.out.println("Generation complete (see file "+parameters.get(OUTPUT)+")");	
					}
					catch (IOException e)
					{
						System.err.println("Unable to write the output file ("+parameters.get(OUTPUT)+")");
						if(verbose) e.printStackTrace();
					}
					finally
					{
						if(file != null)
						{
							file.close();
						}
					}
					
				}

			}, new HashMap<String, Object>());
		}
		return EXIT_OK;
	}

	@Override
	public void stop() {


	}

	private HashMap<String, String> checkAndLoadParam(String[] pArgs, ArgOpt[] pArgOpts) throws Exception
	{
		HashMap<String, String> params = new HashMap<String, String>();
		GetOpt getOpt = new GetOpt();
		String argFlat = getOpt.getFlatArguments(pArgs);

		if (argFlat.indexOf("--help") >= 0 || argFlat.indexOf("-h") >= 0)
		{
			getOpt.printHelp(pArgOpts);
			validArguments  = false;
		}
		else
		{
			params = getOpt.getArguments(pArgOpts, pArgs);
			if (GetOpt.error == null || GetOpt.error.length() > 0)
			{
				System.out.println(GetOpt.error);
				validArguments = false;
			}
		}

		return params;
	}


	private void setupWorkbench()
	{
		Workbench.createAndRunWorkbench(Display.getDefault(), new WorkbenchAdvisor()
		{
			@Override
			public String getInitialWindowPerspectiveId()
			{
				return "";
			}

			@Override
			public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
			{
				return null;
			}

			@Override
			public IAdaptable getDefaultPageInput()
			{
				return null;
			}

			@Override
			public String getMainPreferencePageId()
			{
				return "";
			}

			@Override
			protected IWorkbenchConfigurer getWorkbenchConfigurer()
			{
				return null;
			}

			@Override
			public synchronized AbstractStatusHandler getWorkbenchErrorHandler()
			{
				return null;
			}

			@Override
			public void initialize(IWorkbenchConfigurer configurer)
			{
			}

			@Override
			public boolean openWindows()
			{
				return false;
			}

			@Override
			public IStatus restoreState(IMemento memento)
			{
				return null;
			}

			@Override
			public IStatus saveState(IMemento memento)
			{
				return null;
			}
		});
	}

}
