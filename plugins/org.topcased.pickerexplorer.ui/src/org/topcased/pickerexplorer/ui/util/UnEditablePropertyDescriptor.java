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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;


public class UnEditablePropertyDescriptor implements IPropertyDescriptor {
	
	private IPropertyDescriptor descriptor;
	
	public UnEditablePropertyDescriptor(IPropertyDescriptor desc) {
		descriptor = desc;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		return null;
	}

	public String getCategory() {
		return descriptor.getCategory();
	}

	public String getDescription() {
		return descriptor.getDescription();
	}

	public String getDisplayName() {
		return descriptor.getDisplayName();
	}

	public String[] getFilterFlags() {
		return descriptor.getFilterFlags();
	}

	public Object getHelpContextIds() {
		return descriptor.getHelpContextIds();
	}

	public Object getId() {
		return descriptor.getId();
	}

	public ILabelProvider getLabelProvider() {
		return descriptor.getLabelProvider();
	}

	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
		return descriptor.isCompatibleWith(anotherProperty);
	}

}
