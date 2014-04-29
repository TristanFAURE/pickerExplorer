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
package org.topcased.pickerexplorer.ui.views.pickertree;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.ui.progress.PendingUpdateAdapter;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.elements.RootElement;

class ElementFilter extends ViewerFilter {

	private String filterString;

	private Pattern currentPattern;

	private IBaseLabelProvider labelProvider;

	private Map<AbstractPickerExplorerElement, Boolean> flags;

	private PickerTreeComposite tree;

	ElementFilter(PickerTreeComposite tree) {
		this.tree = tree;
		this.labelProvider = tree.getLabelProvider();
	}


	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if(element instanceof PendingUpdateAdapter) {
			return true;
		}

		if(element instanceof AbstractPickerExplorerElement) {
			AbstractPickerExplorerElement candidate = (AbstractPickerExplorerElement)element;

			// if it's in the map go look at it
			if(this.flags.get(candidate) != null) {
				return this.flags.get(candidate);
			}
			// if not found in the map, the map is not up to date
			// so we update it without using cached values (they don't exist yet)
			// and only starting with the current element
			computeFlagsForElement(candidate);
			if(this.flags.get(candidate) != null) {
				return this.flags.get(candidate);
			}
			// if still not found
			// just filter with the label
			return nameMatches(candidate);

		}

		return false;
	}

	private boolean computeFlagsForElement(AbstractPickerExplorerElement element) {

		boolean flag = false;

		// if the name matches
		// flag it
		if(nameMatches(element)) {
			// flag it
			flag = true;
		}

		// it it is the root or if it has children, flag them
		if(element instanceof RootElement || this.tree.isExpanded(element)) {
			// at first we assume that no child is a match
			boolean childrenFlag = false;
			// we iterate over the children
			for(AbstractPickerExplorerElement child : element.getCachedChildren()) {
				if(computeFlagsForElement(child)) {
					childrenFlag = true;
				}
			}
			// if the flag wasn't set to true yet
			// (ie the label didn't match)
			// then we take the children's flag
			if(!flag) {
				flag = childrenFlag;
			}
		}

		this.flags.put(element, flag);
		return flag;
	}

	private boolean nameMatches(AbstractPickerExplorerElement candidate) {
		if (labelProvider instanceof ILabelProvider) {
			ILabelProvider labelP1 = (ILabelProvider) labelProvider;
			return this.currentPattern.matcher(labelP1.getText(candidate)).matches();
		}
		else if(labelProvider instanceof IStyledLabelProvider){
			IStyledLabelProvider labelP1 = (IStyledLabelProvider) labelProvider;
			return this.currentPattern.matcher(labelP1.getStyledText(candidate).getString()).matches();
		}
		return false;
	}

	void computeFlags() {
		AbstractPickerExplorerElement root = this.tree.getRootElement();
		if(root != null) {
			this.flags = new HashMap<AbstractPickerExplorerElement, Boolean>();
			computeFlagsForElement(root);
		}
	}

	void setFilterString(String s) {
		this.filterString = Messages.Message_wildcard + s + Messages.Message_wildcard;
		this.currentPattern = Pattern.compile(this.filterString, Pattern.CASE_INSENSITIVE);
	}

}
