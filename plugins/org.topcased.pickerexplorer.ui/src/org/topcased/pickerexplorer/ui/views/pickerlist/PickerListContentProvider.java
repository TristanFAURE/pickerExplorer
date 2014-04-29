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
package org.topcased.pickerexplorer.ui.views.pickerlist;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;


class PickerListContentProvider implements IStructuredContentProvider {

	public void dispose() {
		// TODO nothing to dispose ?
	}

	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof ObjectElement) {
			ObjectElement oe = (ObjectElement)inputElement;
			return oe.getAvailablePickers().toArray();
		}
		if(inputElement instanceof PickerElement) {
			PickerElement pe = (PickerElement)inputElement;
			return new ConfigurablePicker[]{ pe.getConfigurablePicker() };
		}
		return new Object[0];
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO check the boxes for enabled pickers ?
	}

}
