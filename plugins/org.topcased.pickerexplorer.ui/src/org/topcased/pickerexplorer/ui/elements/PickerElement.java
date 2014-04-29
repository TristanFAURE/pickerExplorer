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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;


public class PickerElement extends AbstractPickerExplorerElement {

	private ConfigurablePicker configurablePicker;
	
	PickerElement(Object domainObject, ObjectElement parentElement, ConfigurablePicker picker) {
		this.domainObject = domainObject;
		this.parentElement = parentElement;
		this.groupByPicker = parentElement.groupByPicker;
		// this picker element doesn't inherit copies of its parent's pickers
		// he simply copies their references
		this.availablePickers = parentElement.availablePickers;
		this.configurablePicker = picker;
		this.configurablePicker.setOwner(this);
		this.remainingLevels = parentElement.remainingLevels;
		this.exploreNonCachedChildren = parentElement.exploreNonCachedChildren;
	}

	@Override
	public AbstractPickerExplorerElement[] getCachedChildren() {
		return this.cachedChildren;
	}

	public ConfigurablePicker getConfigurablePicker() {
		return this.configurablePicker;
	}

	/**
	 * This method calculates the children of the specific picker of this category.
	 */
	@Override
	public AbstractPickerExplorerElement[] doGetChildren(IProgressMonitor monitor) {

		try {

			// calculate the children of this specific picker (if enabled)
			Set<ObjectElement> childrenSet = new HashSet<ObjectElement>();

			long startExecution = System.nanoTime();
			if(this.configurablePicker.isEnabled()) {
				this.currentlyExecutingConfigurablePicker = this.configurablePicker;
				IPicker picker = this.configurablePicker.getPicker();
				Set<Object> rawChildren = new HashSet<Object>();
				Iterator<?> childrenIterator = picker.getNexts(this.domainObject).iterator();
				while(childrenIterator.hasNext()) {
					rawChildren.add(childrenIterator.next());
				}
				for (Object o : rawChildren) {
					childrenSet.add(new ObjectElement(o, this));
				}
			}

			this.executionTime = System.nanoTime() - startExecution;
			this.numberOfChildren = childrenSet.size();
			this.cachedChildren = childrenSet.toArray(new ObjectElement[0]);
			this.executionStatusMessage = "";
			this.isError = false;
			return this.cachedChildren;

		} catch (Exception e) {
			return handleErrorDuringExecution(e, currentlyExecutingConfigurablePicker);
		}

	}
	
	@Override
	public void accept(PickerExplorerElementVisitor visitor, int hierarchicalLevel) {
		if (visitor.visitPickerElement(this, hierarchicalLevel))
		{
			super.accept(visitor,hierarchicalLevel);
		}
	}
}
