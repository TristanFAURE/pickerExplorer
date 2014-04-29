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
package org.topcased.pickerexplorer.ui.util;

import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;

public class AdaptableToEObjectPropertyTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		return isEObject((IStructuredSelection)receiver);
	}

	@SuppressWarnings("unchecked")
	private <T> T getAdapter(Object o, Class<? extends T> toAdapt) {
		T result = null;
		if(o instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable)o;
			result = (T)adaptable.getAdapter(toAdapt);
		}
		if(result == null) {
			result = (T)Platform.getAdapterManager().getAdapter(o, toAdapt);
		}
		return result;
	}

	/**
	 * Tests if all elements in the selection are instances of {@link EObject}
	 * 
	 * @param selection
	 * @return
	 */
	private boolean isEObject(IStructuredSelection selection) {
		if(!selection.isEmpty()) {
			Iterator<?> iter = selection.iterator();
			while(iter.hasNext()) {
				Object current = iter.next();
				if(!(current instanceof EObject)) {
					if(getAdapter(current, EObject.class) == null) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

}
