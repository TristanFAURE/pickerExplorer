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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;


public class UnEditablePropertySource implements IPropertySource {
	
	private IPropertySource source;

	public UnEditablePropertySource(IPropertySource source) {
		this.source = source;
	}

	public Object getEditableValue() {
		return source.getEditableValue();
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descs = source.getPropertyDescriptors();
		if (descs == null) {
			return null;
		}

		List<IPropertyDescriptor> newDescs = new ArrayList<IPropertyDescriptor>();
		for (int i = 0; i < descs.length; i++) {
			IPropertyDescriptor desc = descs[i];
			newDescs.add(new UnEditablePropertyDescriptor(desc));
		}

		return newDescs.toArray(new IPropertyDescriptor[newDescs.size()]);
	}

	public Object getPropertyValue(Object id) {
		return source.getPropertyValue(id);
	}

	public boolean isPropertySet(Object id) {
		return source.isPropertySet(id);
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
	}

}
