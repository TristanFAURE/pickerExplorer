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
package org.topcased.pickerexplorer.ui.javaqueries;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;


public class ContentsQuery implements IJavaQuery {
	
	
	public Iterable<?> getNexts(Object element) {
		
		EObject eo = null;
		if (element instanceof EObject) {
			eo = (EObject) element;
		}
		if (eo == null) {
			if (element instanceof IAdaptable) {
				eo = (EObject) ((IAdaptable) element).getAdapter(EObject.class);
			}
			if (eo == null) {
				eo = (EObject) Platform.getAdapterManager().getAdapter(element, EObject.class);
			}
		}
		
		if (eo == null) {
			return new ArrayList<EObject>();
		}
		
		EList<EObject> cReferences = eo.eContents();
		
		List<EObject> results = new ArrayList<EObject>();
		for (EObject candidate : cReferences) {
			results.add(candidate);
			// TODO filter results depending on the resource
		}
		
		return results;
	}

}
