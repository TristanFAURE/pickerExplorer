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
package org.topcased.pickerexplorer.ui.settransform;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;


public class ContainerTransformer implements ISetTransformer {

	public Set<Object> transform(Set<Object> set) {

		Set<Object> toAdd = new HashSet<Object>();
		Set<Object> toRemove = new HashSet<Object>();
		for (Object source : set) {
			
			EObject sourceEObject = getEObject(source);
			if (sourceEObject != null) {
				EObject container = sourceEObject.eContainer();
				if (container != null) {
					toRemove.add(source);
					toAdd.add(container);
				}
			}

		}
		set.removeAll(toRemove);
		set.addAll(toAdd);

		return set;
	}
	
	private EObject getEObject(Object element) {
		EObject eo = null;
		if (element instanceof EObject)
		{
			eo = (EObject) element;
		}
		if (eo == null)
		{
			if (element instanceof IAdaptable)
			{
				IAdaptable adaptable = (IAdaptable) element;
				eo = (EObject) adaptable.getAdapter(EObject.class);
			}
			if (eo == null)
			{
				Platform.getAdapterManager().getAdapter(element, EObject.class);
			}
		}

		return eo;
	}
	
}
