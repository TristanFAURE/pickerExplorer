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
package org.topcased.pickerexplorer.ui.elements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;
import org.topcased.iterators.configurations.pickers.annotations.JavaQueryUtils;
import org.topcased.iterators.configurations.pickers.annotations.JavaQueryUtils.ArgumentDescriptor;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.util.PickerExplorerUtils;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;

/**
 * This class wraps a picker and its configuration for a given element in the tree of the
 * Picker Explorer view.
 * 
 * @author mgrihang
 * 
 */
public class ConfigurablePicker {

	private AbstractPickerExplorerElement owner;

	private IPicker picker;

	private String name;

	private boolean enabled = true;

	// to allow the user to edit the parameters
	private Map<String, ArgumentDescriptor> arguments = Collections.emptyMap();

	// private Map<String, Object> parameters = Collections.emptyMap();
	private PickerParameter[] paramTable = new PickerParameter[0];

	private PickerExplorerComposite pickerExplorer;

	/**
	 * Creates a new {@link ConfigurablePicker} which picker is a copy of the parent configurable picker.
	 * 
	 * @param parentPicker
	 *        the parent to inherit from
	 * @param owner
	 *        the {@link AbstractPickerExplorerElement} that owns this {@link ConfigurablePicker}
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	ConfigurablePicker(ConfigurablePicker parentPicker, AbstractPickerExplorerElement owner) {
		this.name = parentPicker.name;
		this.enabled = parentPicker.enabled;
		this.owner = owner;

		// try to clone the parent picker
		try {
			this.picker = PickerExplorerUtils.tryToCopyPickerFrom(parentPicker.getPicker());
		}
		catch (Exception e) {
			// just copy the reference
			this.picker = parentPicker.getPicker();
		}
		IJavaQuery javaQuery = PickerExplorerUtils.getJavaQuery(picker);
		// in case it's an IJavaQuery get the parametetrs
		if (javaQuery != null) {
			this.arguments = JavaQueryUtils.getArguments(javaQuery, owner.getDomainObject());
			// inherit parameters from the parent configurable picker
			inheritParametersFrom(parentPicker);
		}

	}

	/**
	 * Creates a new configurable picker from scratch, with default parameters.
	 * 
	 * @param name
	 * @param picker
	 * @param owner
	 */
	ConfigurablePicker(String name, IPicker picker, AbstractPickerExplorerElement owner) {
		this.name = name;
		this.picker = picker;
		this.owner = owner;
		IJavaQuery javaQuery = PickerExplorerUtils.getJavaQuery(picker);
		if(javaQuery != null) {
			this.arguments = JavaQueryUtils.getArguments(javaQuery, owner.getDomainObject());
			// default parameters
			this.paramTable = getParamTable(javaQuery);

		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return this.name;
	}

	//	private Map<String, ArgumentDescriptor> getArguments() {
	//		return arguments;
	//	}

	public AbstractPickerExplorerElement getOwner() {
		return this.owner;
	}

	public PickerParameter[] getParamTable() {
		return this.paramTable;
	}

	public IPicker getPicker() {
		return this.picker;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	private PickerParameter[] getParamTable(IJavaQuery javaQuery) {
		Map<String, Object> parameters = JavaQueryUtils.getValues(javaQuery);
		PickerParameter[] paramTable = new PickerParameter[parameters.size()];
		int i = 0;
		for(String name : parameters.keySet()) {
			paramTable[i] = new PickerParameter(name, this.arguments.get(name), parameters.get(name), this);
			i++;
		}
		return paramTable;
	}

	private void inheritParametersFrom(ConfigurablePicker parentPicker) {
		PickerParameter[] parentParamTable = parentPicker.getParamTable();
		this.paramTable = new PickerParameter[parentParamTable.length];
		for(int i = 0; i < parentParamTable.length; i++) {
			this.paramTable[i] = parentParamTable[i].copyAndSetNewOwner(this);
		}
	}

	void setOwner(AbstractPickerExplorerElement owner) {
		this.owner = owner;
	}

	void updateParameterValues() {
		IJavaQuery javaQuery = PickerExplorerUtils.getJavaQuery(this.picker);
		if(javaQuery != null) {
			Map<String, Object> values = new HashMap<String, Object>();
			for(PickerParameter param : this.paramTable) {
				values.put(param.getName(), param.getValue());
			}
			JavaQueryUtils.setValues(values, javaQuery);
		}
		// update the tree viewer ?
		owner.notifyChanges();
		// TODO update the param list
		pickerExplorer.refreshParamList();
	}

	public Map<String, ArgumentDescriptor> getArguments() {
		return arguments;
	}

	public void setPickerExplorer(PickerExplorerComposite pickerExplorer) {
		this.pickerExplorer = pickerExplorer;
	}

}
