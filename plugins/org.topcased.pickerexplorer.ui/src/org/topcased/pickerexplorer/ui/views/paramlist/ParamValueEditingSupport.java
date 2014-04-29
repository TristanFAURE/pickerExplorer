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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Table;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;


public class ParamValueEditingSupport extends EditingSupport {
	
	private TextCellEditor textEditor;
	private ComboBoxViewerCellEditor comboBoxEditor;

	public ParamValueEditingSupport(ColumnViewer viewer, Table paramTable) {
		super(viewer);
		textEditor = new TextCellEditor(paramTable);
		comboBoxEditor = new ComboBoxViewerCellEditor(paramTable);
		comboBoxEditor.setContentProvider(new ParameterComboContentProvider());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if(element instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)element;
			if (param.hasChoices()) {
				comboBoxEditor.setInput(param);
				return comboBoxEditor;
			}
		}
		return textEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		if(element instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)element;
			return param.getValue().toString();
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if(element instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)element;
			Class<?> type = param.getType();
			Object parsedValue = parseValue((String)value, type);
			if(parsedValue != null) {
				param.setValue(parsedValue);
				param.updatePicker();
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
