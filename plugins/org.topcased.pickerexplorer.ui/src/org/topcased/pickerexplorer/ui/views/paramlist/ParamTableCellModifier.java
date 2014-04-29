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

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.Item;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;

class ParamTableCellModifier implements ICellModifier {

	private PickerExplorerComposite pickerExplorer;

	ParamTableCellModifier(PickerExplorerComposite pickerExplorer) {
		this.pickerExplorer = pickerExplorer;
	}

	public boolean canModify(Object element, String property) {
		return Messages.Message_value_id.equals(property);
	}

	public Object getValue(Object element, String property) {
		if(element instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)element;
			return param.getValue().toString();
		}
		return null;
	}

	public void modify(Object element, String property, Object value) {
		if(!Messages.Message_value_id.equals(property)) {
			return;
		}
		if(element instanceof Item) {
			PickerParameter param = (PickerParameter)((Item)element).getData();
			Class<?> type = param.getType();
			Object parsedValue = parseValue((String)value, type);
			if(parsedValue != null) {
				param.setValue(parsedValue);
				param.updatePicker();
				// notify the view
				this.pickerExplorer.refreshPickerExplorerSelectedElement();
				this.pickerExplorer.refreshParamListSelectedParam();
			}
		}
	}

	private Object parseValue(String value, Class<?> type) {
		try {
			if(type == String.class) {
				return value;
			}
			if(type == int.class || type.isAssignableFrom(Integer.class)) {
				return Integer.parseInt(value);
			}
			if(type == double.class || type.isAssignableFrom(Double.class)) {
				return Double.parseDouble(value);
			}
			if(type == boolean.class || type.isAssignableFrom(Boolean.class)) {
				return Boolean.parseBoolean(value);
			}
		} catch (Exception e) {
			// parsing exception
			return null;
		}
		return null;
	}

}
