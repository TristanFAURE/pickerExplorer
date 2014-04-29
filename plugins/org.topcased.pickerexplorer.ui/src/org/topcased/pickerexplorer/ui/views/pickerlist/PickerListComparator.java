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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;

class PickerListComparator extends ViewerComparator {

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		String name1;
		String name2;

		if(e1 instanceof ConfigurablePicker && e2 instanceof ConfigurablePicker) {
			name1 = ((ConfigurablePicker)e1).getName();
			name2 = ((ConfigurablePicker)e2).getName();
		} else {
			name1 = e1.toString();
			name2 = e2.toString();
		}

		// use the comparator to compare the strings
		return getComparator().compare(name1, name2);
	}

}
