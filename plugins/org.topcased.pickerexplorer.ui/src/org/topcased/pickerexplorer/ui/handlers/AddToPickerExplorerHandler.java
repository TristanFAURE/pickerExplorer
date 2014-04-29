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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.topcased.pickerexplorer.ui.Activator;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.builder.PickerExplorerBuilder;
import org.topcased.pickerexplorer.ui.views.PickerExplorerView;


public class AddToPickerExplorerHandler extends AbstractHandler {

	public AddToPickerExplorerHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		// the elements to be added
		Object[] toAdd = new Object[0];

		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection)selection;
			@SuppressWarnings("unchecked")
			List<Object> candidateElements = sSelection.toList();
			List<EObject> newElements = new LinkedList<EObject>();
			for(Object o : candidateElements) {
				EObject eo = getEObject(o);
				if(eo != null) {
					newElements.add(eo);
				}
			}
			toAdd = newElements.toArray();

		}

		// is the view already open ?
		PickerExplorerView view = null;
		IWorkbenchPage activePage = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();
		view = (PickerExplorerView) activePage.findView(Messages.Message_picker_explorer_view);
		if (view == null || view.getPickerExplorer() == null) {
			// create a builder
			PickerExplorerBuilder builder = new PickerExplorerBuilder();
			// customize it
			builder.setAllPickersEnabledByDefault(false);
			builder.setRetrieveRegisteredPickers(true);
			builder.setGroupByPicker(true);
			builder.setParent(null);
			builder.setMaxDepth(-1);
			builder.setContextMenuEnabled(true);
			// add the elements
			for (Object o : toAdd) {
				builder.addElement(o);
			}
			// and run it
			builder.run();
		}
		else {
			try {
				// get the existing view and show it
				view = (PickerExplorerView)HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage().showView(Messages.Message_picker_explorer_view);
			} catch (PartInitException e) {
				Activator.logException(e);
			}
			try {
				// add the elements
				view.getPickerExplorer().addElements(toAdd);
			} catch (InstantiationException e) {
				Activator.logException(e);
			} catch (IllegalAccessException e) {
				Activator.logException(e);
			}
		}

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
