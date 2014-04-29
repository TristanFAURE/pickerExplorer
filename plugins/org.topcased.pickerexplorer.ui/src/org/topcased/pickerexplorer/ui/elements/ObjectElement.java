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

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;


public class ObjectElement extends AbstractPickerExplorerElement implements IAdaptable {
	
	ObjectElement(Object domainObject, AbstractPickerExplorerElement parentElement) throws InstantiationException, IllegalAccessException {
		this.domainObject = domainObject;
		this.parentElement = parentElement;
		if(parentElement.remainingLevels != -1) {
			this.remainingLevels = parentElement.remainingLevels - 1;
		}

		inheritPickersFrom(parentElement);
		this.groupByPicker = parentElement.groupByPicker;
		this.exploreNonCachedChildren = parentElement.exploreNonCachedChildren;

	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {

		if(EObject.class.isAssignableFrom(adapter) || adapter.equals(EObject.class)) {
			if (domainObject instanceof EObject) {
				return (EObject) domainObject;				
			}
		}

		return null;
	}

	@Override
	public AbstractPickerExplorerElement[] getCachedChildren() {
		return this.cachedChildren;
	}

	@Override
	public String toString() {
		return this.domainObject == null ? Messages.Message_null : this.domainObject.toString();
	}

	/**
	 * This method effectively computes the children of an element.
	 */
	@Override
	protected AbstractPickerExplorerElement[] doGetChildren(IProgressMonitor monitor) {

		try {

			long startExecution = System.nanoTime();

			// if children have to be grouped according to the picker they come from
			if(this.groupByPicker) {
				this.currentlyExecutingConfigurablePicker = null;
				// return as many children as there are enabled pickers
				Set<PickerElement> childrenSet = new HashSet<PickerElement>();
				for(ConfigurablePicker cPicker : this.availablePickers) {
					if(cPicker.isEnabled()) {
						this.currentlyExecutingConfigurablePicker = cPicker;
						childrenSet.add(new PickerElement(this.domainObject, this, cPicker));
					}
				}
				this.executionTime = System.nanoTime() - startExecution;
				this.numberOfChildren = childrenSet.size();
				this.cachedChildren = childrenSet.toArray(new PickerElement[0]);
				this.executionStatusMessage = ""; //$NON-NLS-1$
				this.isError = false;
				return this.cachedChildren;
			}

			// if all children are mixed together
			else {
				Set<Object> unwrappedChildren = new HashSet<Object>();
				Set<ObjectElement> childrenSet = new HashSet<ObjectElement>();
				// iterate over the enabled pickers of this element
				for(ConfigurablePicker configurablePicker : this.availablePickers) {
					if(configurablePicker.isEnabled()) {
						this.currentlyExecutingConfigurablePicker = configurablePicker;
						// fetch children
						IPicker picker = configurablePicker.getPicker();
//						Set<ObjectElement> pickerChildrenSet = new HashSet<ObjectElement>();

						Iterator<?> pickerChildrenIterator = picker.getNexts(this.domainObject).iterator();
						while(pickerChildrenIterator.hasNext()) {
							unwrappedChildren.add(pickerChildrenIterator.next());
						}
					}
				}
				// the children inherits the configuration of the parent
				for (Object unwrappedChild : unwrappedChildren) {
					ObjectElement newChild = new ObjectElement(unwrappedChild, this);
					childrenSet.add(newChild);
				}

				this.executionTime = System.nanoTime() - startExecution;
				this.numberOfChildren = childrenSet.size();
				this.cachedChildren = childrenSet.toArray(new ObjectElement[0]);
				this.executionStatusMessage = ""; //$NON-NLS-1$
				this.isError = false;
				return this.cachedChildren;

			}
		}

		catch (Exception e) {
			return handleErrorDuringExecution(e, this.currentlyExecutingConfigurablePicker);
		}

	}

	public void enableAllPickers() {
		for (ConfigurablePicker cPicker : availablePickers) {
			cPicker.setEnabled(true);
		}
	}

	public void disableAllPickers() {
		for (ConfigurablePicker cPicker : availablePickers) {
			cPicker.setEnabled(false);
		}
	}

	@Override
	public void accept(PickerExplorerElementVisitor visitor, int hierarchicalLevel) {
		if (visitor.visitObjectElement(this, hierarchicalLevel))
		{
			super.accept(visitor,hierarchicalLevel);
		}
	}
	
}
