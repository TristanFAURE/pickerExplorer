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
package org.topcased.pickerexplorer.ui.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.topcased.pickerexplorer.ui.Activator;
import org.topcased.pickerexplorer.ui.builder.PickerExplorerBuilder;
import org.topcased.pickerexplorer.ui.exceptions.NullPickerException;


public class ShowAllReferencesHandler extends AbstractHandler {

	public ShowAllReferencesHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
				
		PickerExplorerBuilder builder = new PickerExplorerBuilder();
		
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection)selection;
			@SuppressWarnings("unchecked")
			List<Object> candidateElements = sSelection.toList();
			for(Object o : candidateElements) {
				EObject eo = getEObject(o);
				if(eo != null) {
					
					builder.addElement(eo);
					
				}
			}
		}
		
		// add the cross referencer picker
		// id : inverseReferencesQuery
		try {
			builder.addPicker("Inverse References");
		} catch (NullPickerException e) {
			Activator.logException(e);
		}
		
//		// set the options
		builder.setRetrieveRegisteredPickers(false)
			.setParamListVisible(false)
			.setGroupByPicker(false)
			.setContextMenuEnabled(false)
			.setPickerListVisible(false)
			.setAllPickersEnabledByDefault(true)
			.setParent(null)
			.setTitle("References");

		
		builder.run();
		
		return null;
		
	}

	private EObject getEObject(Object o) {
		EObject eo = null;
		if(o instanceof EObject) {
			eo = (EObject)o;
		}
		if(eo == null) {
			if(o instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable)o;
				eo = (EObject)adaptable.getAdapter(EObject.class);
			}
			if(eo == null) {
				eo = (EObject)Platform.getAdapterManager().getAdapter(o, EObject.class);
			}
		}
		return eo;
	}

}
