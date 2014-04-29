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
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;
import org.eclipse.ui.progress.IElementCollector;
import org.topcased.pickerexplorer.ui.Activator;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;
import org.topcased.pickerexplorer.ui.visitor.IPickerExplorerElement;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;


/**
 * This abstract class represents elements in the tree. They can be either
 * instances of {@link ObjectElement} or instances of {@link PickerElement}.
 * This class is not intended to be subclassed by clients.
 * 
 * @author mgrihang
 * 
 */
public abstract class AbstractPickerExplorerElement implements IDeferredWorkbenchAdapter, IPickerExplorerElement {

	/**
	 * Wether the children of an element are grouped by pickers.
	 */
	protected boolean groupByPicker = false;

	protected Object domainObject = null;

	protected long executionTime = -1;

	protected int numberOfChildren = -1;

	protected boolean isError = false;
	
	protected boolean exploreNonCachedChildren = false;

	/**
	 * The children of this element that have already been computed.
	 */
	protected AbstractPickerExplorerElement[] cachedChildren = null;

	protected String executionStatusMessage = ""; //$NON-NLS-1$

	/**
	 * The picker whose children are currently being computed.
	 */
	protected ConfigurablePicker currentlyExecutingConfigurablePicker = null;

	/**
	 * The remaining amount of levels that the user can expand.
	 * The default value <code>-1</code> allows infinite expansion.
	 */
	protected int remainingLevels = -1;

	/**
	 * A set of the pickers that are available
	 * for this particular element of the tree.
	 */
	protected Set<ConfigurablePicker> availablePickers = new HashSet<ConfigurablePicker>();

	protected AbstractPickerExplorerElement parentElement = null;
	
	public void fetchDeferredChildren(Object object, IElementCollector collector, IProgressMonitor monitor) {
		collector.add(getChildren(monitor), monitor);
	}

	public Set<ConfigurablePicker> getAvailablePickers() {
		return this.availablePickers;
	}

	public Object[] getChildren(Object o) {
		return null;
	}

	public Object getDomainObject() {
		return this.domainObject;
	}

	public String getExecutionStatusMessage() {
		return this.executionStatusMessage;
	}

	public long getExecutionTime() {
		return this.executionTime;
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	public String getLabel(Object o) {
		return null;
	}

	public int getNumberOfChildren() {
		return this.numberOfChildren;
	}

	public Object getParent(Object o) {
		return null;
	}

	public AbstractPickerExplorerElement getParentElement() {
		return this.parentElement;
	}

	/**
	 * This method returns the root ancestor of this element.
	 * 
	 * @return
	 */
	public AbstractPickerExplorerElement getRootAncestor() {
		if(this.parentElement instanceof RootElement) {
			return this;
		} else {
			return this.parentElement.getRootAncestor();
		}
	}

	public ISchedulingRule getRule(Object object) {
		return null;
	}

	public boolean isContainer() {
		// null objects may not have any children
		// nor elements without remaining levels
		return !(this.domainObject == null || this.remainingLevels == 0);
	}

	public boolean isError() {
		return this.isError;
	}

	public boolean isGroupByPicker() {
		return this.groupByPicker;
	}

	public void setGroupByPicker(boolean groupByPicker) {
		this.groupByPicker = groupByPicker;
	}

	public abstract AbstractPickerExplorerElement[] getCachedChildren();

	/**
	 * This method has to be implemented by subclasses
	 * to calculate the children of an element.
	 * @param monitor 
	 * 
	 * @return the children of an element in the tree
	 */
	protected abstract AbstractPickerExplorerElement[] doGetChildren(IProgressMonitor monitor);
	
	public AbstractPickerExplorerElement[] getChildren(IProgressMonitor monitor) {
		if (remainingLevels == 0) {
			return new AbstractPickerExplorerElement[0];
		}
		return doGetChildren(monitor);
	}

	protected AbstractPickerExplorerElement[] handleErrorDuringExecution(Exception e, ConfigurablePicker picker) {
		this.executionTime = -1;
		StringBuilder builder = new StringBuilder();
		// if the picker causing the error is known
		if (picker != null) {
			builder.append(picker.getName()+Messages.Message_two_dots);
		}
		// if there is an explicite message
		if (e.getMessage() != null) {
			builder.append(e.getMessage());
		}
		// if there is no explicite message, log the exception
		else {
			builder.append(Messages.Message_error_during_execution);
			Activator.logException(e);
		}
		this.executionStatusMessage = builder.toString();
		this.isError = true;
		this.cachedChildren = new AbstractPickerExplorerElement[0];
		return this.cachedChildren;
	}

	/**
	 * Inherits the configurable pickers from a parent.
	 * 
	 * @param parent
	 *        the parent to inherit from
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected void inheritPickersFrom(AbstractPickerExplorerElement parent) throws InstantiationException, IllegalAccessException {
		Set<ConfigurablePicker> childSet = new HashSet<ConfigurablePicker>();
		for(ConfigurablePicker parentPicker : parent.getAvailablePickers()) {
			ConfigurablePicker childPicker = new ConfigurablePicker(parentPicker, this);
			childSet.add(childPicker);
		}
		this.availablePickers = childSet;
	}

	protected void setParentElement(AbstractPickerExplorerElement parentElement) {
		this.parentElement = parentElement;
	}

	public void notifyChanges() {
		PickerExplorerComposite pickerExplorer = getRootElement().getPickerExplorer();
		if (pickerExplorer != null) {
			pickerExplorer.refreshPickerExplorer(this);
		}
	}

	private RootElement getRootElement() {
		if (this instanceof RootElement) {
			return (RootElement)this;
		}
		else return getParentElement().getRootElement();
	}

	public void accept(PickerExplorerElementVisitor visitor,
			int hierarchicalLevel) {
		AbstractPickerExplorerElement[] cached;
		if(this.exploreNonCachedChildren){
			cached = getChildren(new NullProgressMonitor());
		} else {
			cached = getCachedChildren();
		}
		if(cached != null){
			for (AbstractPickerExplorerElement a : cached)
			{
				a.accept(visitor, hierarchicalLevel+1);
			}
		}
	}
	
}
