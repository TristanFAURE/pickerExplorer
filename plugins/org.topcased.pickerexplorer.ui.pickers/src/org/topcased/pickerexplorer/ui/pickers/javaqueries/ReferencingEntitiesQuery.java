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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.topcased.iterators.configurations.pickers.annotations.Argument;
import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;
import org.topcased.iterators.exceptions.PickerExecutionException;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.settransform.ISetTransformer;

public class ReferencingEntitiesQuery implements IJavaQuery {

	private static final String SET_TRANSFORMER_EXTENSION_POINT_ID = Messages.Message_set_transformer;
	private static final String PLUGIN_ID = Messages.Message_plugin_id;
	private final static String CONTAINER_TRANSFORMER_ID = Messages.Message_container_set_transformer;
	private static String TRANSFORMER_ID_ATTRIBUTE = Messages.Message_set_transformer_id;
	private static String TRANSFORMER_CLASS_ATTRIBUTE = Messages.Message_set_transformer_class;
	
	@Argument(label = "Id of the transformer to use")
	private String transformerId = CONTAINER_TRANSFORMER_ID;

	public Iterable<?> getNexts(Object element) throws PickerExecutionException {

		EObject eo = getEObject(element);
		if (eo == null) {
			return Collections.emptyList();
		}

		return getReferencingEntities(eo);
	}


	private Set<Object> getReferencingEntities(EObject eo) throws PickerExecutionException {

		ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(eo);
		// s'il n'en existe pas, on en crée un
		if (crossReferencer == null) {
			crossReferencer = new ECrossReferenceAdapter();
			// et on l'attache au resource set
			eo.eResource().getResourceSet().eAdapters().add(crossReferencer);
		}

		Collection<Setting> inverseReferences = crossReferencer.getNonNavigableInverseReferences(eo, true);
		//		Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(eo, true);

		Set<Object> iRefs = new HashSet<Object>();
		for (Setting s : inverseReferences) {
			iRefs.add(s.getEObject());
		}

		// retrieve a set transformer from an extension point and use it
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(PLUGIN_ID+Messages.Message_dot+SET_TRANSFORMER_EXTENSION_POINT_ID);
		IConfigurationElement[] config = extensionPoint.getConfigurationElements();

		ISetTransformer transformer = null;

		for (IConfigurationElement configElement : config) {
			
			String id = configElement.getAttribute(TRANSFORMER_ID_ATTRIBUTE);

			if (transformerId.equals(id)) {
				try {
					transformer = (ISetTransformer) configElement.createExecutableExtension(TRANSFORMER_CLASS_ATTRIBUTE);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (transformer != null) {
			return transformer.transform(iRefs);
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
