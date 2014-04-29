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

import org.topcased.iterators.configurations.pickers.annotations.JavaQueryUtils.ArgumentDescriptor;

public class PickerParameter {

	private String name;

	private String label;

	private Object value;

	private Class<?> type;
	
	private ConfigurablePicker owner;
	
	private String[] choices = null;

	private PickerParameter(String name, String label, Class<?> type, Object value, ConfigurablePicker owner) {
		this.name = name;
		this.label = label;
		this.type = type;
		this.value = value;
		this.owner = owner;
		this.choices = owner.getArguments().get(name).choices;
	}

	PickerParameter(String name, ArgumentDescriptor desc, Object value, ConfigurablePicker owner) {
		this.name = name;
		this.label = desc.description;
		this.type = desc.type;
		this.value = value;
		this.owner = owner;
		this.choices = desc.choices;
	}

	public String getLabel() {
		return this.label;
	}

	public String getName() {
		return this.name;
	}

	public Class<?> getType() {
		return this.type;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void updatePicker() {
		// the value of a parameter has changed
		// we have to notify our owner so it can update the query
		this.owner.updateParameterValues();
	}

	//	private ConfigurablePicker getOwner() {
	//		return owner;
	//	}

	/**
	 * This method returns a copy or this instance of {@link PickerParameter}.
	 * The value of the copied parameter is only a shallow copy of the original value.
	 * 
	 * @return a copy of this {@link PickerParameter}.
	 */
	PickerParameter copyAndSetNewOwner(ConfigurablePicker newOwner) {
		return new PickerParameter(this.name, this.label, this.type, this.value, newOwner);
	}

	public ConfigurablePicker getOwner() {
		return owner;
	}

	public boolean hasChoices() {
		return choices != null && choices.length > 0;
	}
	
	public String[] getChoices() {
		return this.choices;
	}

}
