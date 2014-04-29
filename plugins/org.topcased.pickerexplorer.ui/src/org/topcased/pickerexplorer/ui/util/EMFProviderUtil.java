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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

/**
 * This class provides an {@link AdapterFactory} for the purposes
 * of the picker explorer.
 * @author mgrihang
 */
public class EMFProviderUtil {

	private static AdapterFactory adapterFactory; 
	static {
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new EcoreItemProviderAdapterFactory());
		factories.add(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		factories.add(new ReflectiveItemProviderAdapterFactory());
		adapterFactory = new ComposedAdapterFactory(factories);
	}
	
	private static AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
	
	private static AdapterFactoryContentProvider contentProvider = new AdapterFactoryContentProvider(adapterFactory);

	public static AdapterFactoryContentProvider getAdapterFactoryContentProvider() {
		return contentProvider;
	}
	public static AdapterFactoryLabelProvider getAdapterFactoryLabelProvider() {
		return labelProvider;
	}

}
