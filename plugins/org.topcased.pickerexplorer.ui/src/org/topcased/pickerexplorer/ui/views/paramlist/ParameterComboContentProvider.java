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
import org.topcased.pickerexplorer.ui.elements.PickerParameter;


public class ParameterComboContentProvider implements IStructuredContentProvider {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)inputElement;
			return param.getChoices();
		}
		return new Object[0];
	}

}
