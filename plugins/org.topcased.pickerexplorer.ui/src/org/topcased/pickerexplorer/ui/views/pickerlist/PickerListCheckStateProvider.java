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

import org.eclipse.jface.viewers.ICheckStateProvider;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;


class PickerListCheckStateProvider implements ICheckStateProvider {

	public boolean isChecked(Object element) {
		if(element instanceof ConfigurablePicker) {
			return ((ConfigurablePicker)element).isEnabled();
		}
		return false;
	}

	public boolean isGrayed(Object element) {
		// TODO maybe set this to true if the picker is not applicable to the element ?
		return false;
	}

}
