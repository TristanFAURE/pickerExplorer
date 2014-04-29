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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.ResourceManager;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.util.PickerExplorerUtils;


class PickerListLabelProvider extends LabelProvider implements ITableLabelProvider {

	private Image pickerIcon = ResourceManager.getPluginImageDescriptor("org.topcased.pickerexplorer.ui", "icons/generic_element.gif").createImage(); //$NON-NLS-1$ //$NON-NLS-2$

	@Override
	public void dispose() {
		super.dispose();
		// destroy the image
		this.pickerIcon.dispose();
	}

	public Image getColumnImage(Object element, int columnIndex) {
		return columnIndex == 0 ? this.pickerIcon : null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof ConfigurablePicker) {
			ConfigurablePicker cPicker = (ConfigurablePicker) element;
			switch(columnIndex) {
			case 0:
				String name = cPicker.getName();
				if (name == null || name.equals("")) {
					name = PickerExplorerUtils.getName(cPicker.getPicker());
				}
				return name;
			case 1:
				return PickerExplorerUtils.getDescription(cPicker.getPicker());
			default:
				break;
			}
		}
		return Messages.Message_unknown_type;
	}

}
