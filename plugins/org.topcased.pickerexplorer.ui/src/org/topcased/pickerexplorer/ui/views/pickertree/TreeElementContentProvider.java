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

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.progress.DeferredTreeContentManager;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;

class TreeElementContentProvider implements ITreeContentProvider, IJobChangeListener {

	PickerExplorerComposite pickerExplorer;

	private boolean useCachedChildren = false;

	// a DeferredTreeContentManager to handle the pending
	private DeferredTreeContentManager deferredTreeContentManager;

	TreeElementContentProvider(PickerExplorerComposite pickerExplorer) {
		this.pickerExplorer = pickerExplorer;
	}

	public void aboutToRun(IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public void awake(IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// Nothing to dispose
	}

	public void done(IJobChangeEvent event) {
		// notify the view that a pending is over
		// so it can update its viewers and other parts (status bar, error message...)
		this.pickerExplorer.updateRelevantParts(this.pickerExplorer.getSelectedElement());
	}

	public Object[] getChildren(Object parentElement) {
		if(this.useCachedChildren) {
				return ((AbstractPickerExplorerElement) parentElement).getCachedChildren();
		}
		return this.deferredTreeContentManager.getChildren(parentElement);
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	//	private boolean isUseCachedChildren() {
	//		return useCachedChildren;
	//	}

	public Object getParent(Object element) {
		if(element instanceof AbstractPickerExplorerElement) {
			AbstractPickerExplorerElement child = (AbstractPickerExplorerElement)element;
			return child.getParentElement();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return this.deferredTreeContentManager.mayHaveChildren(element);
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if(viewer instanceof AbstractTreeViewer) {
			this.deferredTreeContentManager = new DeferredTreeContentManager((AbstractTreeViewer)viewer);
			this.deferredTreeContentManager.addUpdateCompleteListener(this);
		}
		// TODO cancel children calculation if input is changed
	}

	public void running(IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public void scheduled(IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public void sleeping(IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	void setUseCachedChildren(boolean useDeferredContentProvider) {
		this.useCachedChildren = useDeferredContentProvider;
	}

}
