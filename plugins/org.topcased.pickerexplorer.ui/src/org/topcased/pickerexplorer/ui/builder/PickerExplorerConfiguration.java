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
package org.topcased.pickerexplorer.ui.builder;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.ResourceManager;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.views.pickertree.TreeElementLabelProvider;

class PickerExplorerConfiguration {

	private List<Object> elements = new LinkedList<Object>();

	private List<IPicker> pickers = new LinkedList<IPicker>();

	private boolean contextMenuEnabled = true;

	private boolean paramListVisible = true;

	private boolean pickerListVisible = true;

	private boolean groupByPicker = true;

	private boolean allPickersEnabledByDefault = true;

	private boolean retrieveRegisteredPickers = true;

	private boolean exploreNonCachedChildren = false;
	
	private Composite parent;

	private int maxDepth = -1;

	private ImageDescriptor icon = ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/problem_category.gif"); //$NON-NLS-1$

	private ILabelProvider labelProvider = new TreeElementLabelProvider();

	private String title = null;

	public ImageDescriptor getIcon() {
		return this.icon;
	}

	public void setLabelProvider(ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	void addElement(Object o) {
		this.elements.add(o);
	}

	void addPicker(IPicker picker) {
		this.pickers.add(picker);
	}

	List<Object> getElements() {
		return this.elements;
	}

	ILabelProvider getLabelProvider() {
		return this.labelProvider;
	}

	int getMaxDepth() {
		return this.maxDepth;
	}

	Composite getParent() {
		return this.parent;
	}

	List<IPicker> getPickers() {
		return this.pickers;
	}

	boolean isAllPickersEnabledByDefault() {
		return this.allPickersEnabledByDefault;
	}

	boolean isContextMenuEnabled() {
		return this.contextMenuEnabled;
	}

	boolean isGroupByPicker() {
		return this.groupByPicker;
	}

	boolean isParamListVisible() {
		return this.paramListVisible;
	}

	//	private void setElements(List<Object> elements) {
	//		this.elements = elements;
	//	}

	boolean isPickerListVisible() {
		return this.pickerListVisible;
	}

	boolean isRetrieveRegisteredPickers() {
		return this.retrieveRegisteredPickers;
	}

	void setAllPickersEnabledByDefault(boolean selected) {
		this.allPickersEnabledByDefault = selected;
	}

	void setContextMenuEnabled(boolean cMenuEnabled) {
		this.contextMenuEnabled = cMenuEnabled;
	}

	void setGroupByPicker(boolean groupByPicker) {
		this.groupByPicker = groupByPicker;
	}

	void setIcon(ImageDescriptor desc) {
		this.icon = desc;
	}

	void setLabelprovider(ILabelProvider lProvider) {
		this.labelProvider = lProvider;
	}

	void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	//	private void setPickers(List<IPicker> pickers) {
	//		this.pickers = pickers;
	//	}

	void setParamListVisible(boolean paramListVisible) {
		this.paramListVisible = paramListVisible;
	}

	void setParent(Composite parent) {
		this.parent = parent;
	}

	void setPickerListVisible(boolean pickerListVisible) {
		this.pickerListVisible = pickerListVisible;
	}

	void setRetrieveRegisteredPickers(boolean b) {
		this.retrieveRegisteredPickers = b;
	}

	public boolean isExploreNonCachedChildren() {
		return exploreNonCachedChildren;
	}

	void setExploreNonCachedChildren(boolean exploreNonCachedChildren) {
		this.exploreNonCachedChildren = exploreNonCachedChildren;
	}

	void setTitle(String title) {
		this.title = title ;
	}
	
	public String getLabel(){
		return title ;
	}



}
