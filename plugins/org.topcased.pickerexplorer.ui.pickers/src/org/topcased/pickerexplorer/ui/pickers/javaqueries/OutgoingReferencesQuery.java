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
package org.topcased.pickerexplorer.ui.pickers.javaqueries;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.topcased.iterators.configurations.pickers.annotations.Argument;
import org.topcased.iterators.configurations.pickers.annotations.Choices;
import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;

public class OutgoingReferencesQuery implements IJavaQuery {

	@Argument(label = "The name of the reference to be searched. Blank allows all.")
	private String name = "";

	@Choices(argumentName = "name")
	public String[] getChoices(Object element) {
		EObject eo = getEObject(element);
		if (eo == null) {
			return null;
		}
		EList<EReference> refs = eo.eClass().getEAllReferences();
		List<String> refNameList = new LinkedList<String>();
		refNameList.add("");
		for (EReference ref : refs) {
			refNameList.add(ref.getName());
		}
		return refNameList.toArray(new String[0]);
	}
	
	public Iterable<?> getNexts(Object element) {
		if (element instanceof EObject) {
			return getOutgoingReferences((EObject) element, name);
		}
		return Collections.emptyList();
	}

	private Iterable<?> getOutgoingReferences(EObject eo, String refName) {
		List<EObject> result = new LinkedList<EObject>();

		for (EReference ref : eo.eClass().getEAllReferences()) {
			if (!ref.isContainment()) {
				if (refName.length() == 0
						|| refName.equalsIgnoreCase(ref.getName())) {
					if (ref.isMany()) {
						result.addAll((Collection) eo.eGet(ref));
					} else {
						result.add((EObject) eo.eGet(ref));
					}
				}
			}
		}

		return result;
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
