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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;


class ParamTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		String text = "";
		if(element instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)element;
			switch(columnIndex) {
			case 0:
				text = param.getName();
				break;
			case 1:
				text = param.getValue().toString();
				break;
			case 2:
				text = param.getLabel();
				break;
			default:
				break;
			}
		}
		return text;
	}

}
