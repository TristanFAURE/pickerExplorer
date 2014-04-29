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
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;
import org.topcased.iterators.exceptions.PickerExecutionException;

public class InverseReferencesQuery implements IJavaQuery {

	public Iterable<?> getNexts(Object element) throws PickerExecutionException {

		EObject eo = getEObject(element);
		if (eo == null) {
			return Collections.emptyList();
		}

		return getInverseReferences(eo);
	}


	private Set<EObject> getInverseReferences(EObject eo) throws PickerExecutionException {

		ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(eo);
		// s'il n'en existe pas, on en crée un
		if (crossReferencer == null) {
			crossReferencer = new ECrossReferenceAdapter();
			// et on l'attache au resource set
			eo.eResource().getResourceSet().eAdapters().add(crossReferencer);
		}

		Collection<Setting> inverseReferences = crossReferencer.getNonNavigableInverseReferences(eo, true);
//		Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(eo, true);

		Set<EObject> iRefs = new HashSet<EObject>();
		for (Setting s : inverseReferences) {
			iRefs.add(s.getEObject());
		}

		return iRefs;

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
