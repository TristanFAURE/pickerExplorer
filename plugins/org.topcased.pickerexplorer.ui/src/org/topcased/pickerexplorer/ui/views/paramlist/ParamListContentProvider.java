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
package org.topcased.pickerexplorer.ui.views.paramlist;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;


class ParamListContentProvider implements IStructuredContentProvider {
	
	private PickerExplorerComposite pickerExplorer;

	public ParamListContentProvider(PickerExplorerComposite pickerExplorer) {
		this.pickerExplorer = pickerExplorer;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof ConfigurablePicker) {
			ConfigurablePicker cPicker = (ConfigurablePicker)inputElement;
			return cPicker.getParamTable();
		}
		return null;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof ConfigurablePicker) {
			ConfigurablePicker cPicker = (ConfigurablePicker)newInput;
			cPicker.setPickerExplorer(pickerExplorer);
		}
	}

}
