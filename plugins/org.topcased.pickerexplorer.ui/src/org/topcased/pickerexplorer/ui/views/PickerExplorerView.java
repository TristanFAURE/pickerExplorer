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
package org.topcased.pickerexplorer.ui.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.topcased.pickerexplorer.ui.actions.RefreshElementAction;
import org.topcased.pickerexplorer.ui.actions.SaveElementsAction;
import org.topcased.pickerexplorer.ui.actions.ToggleGroupElementsAction;

public class PickerExplorerView extends ViewPart {

	private ToggleGroupElementsAction toggleGroupElementsAction;

	private RefreshElementAction refreshElementAction;
	
	private SaveElementsAction saveElementsAction;

	public static final String ID = "pickerexplorer.ui.views.PickerExplorerView"; //$NON-NLS-1$

	private PageBook pageBook;

	private InViewPickerExplorerComposite pickerExplorer;

	public PickerExplorerView() {
	}

	
	
	@Override
	public void setPartName(String partName) {
		// TODO Auto-generated method stub
		super.setPartName(partName);
	}



	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		this.pageBook = new PageBook(parent, SWT.NONE);

		// toolbars and menus
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public PickerExplorerComposite getPickerExplorer() {
		return this.pickerExplorer;
	}

	@Override
	public void setFocus() {
		this.pageBook.setFocus();
	}


	public InViewPickerExplorerComposite showPickerExplorer() {
		return showPickerExplorer(true, true, true, null);
	}


	public InViewPickerExplorerComposite showPickerExplorer(boolean pickerListVisible, boolean paramListVisible, boolean contextMenuEnabled, ILabelProvider labelProvider) {
		if(this.pickerExplorer == null) {
			this.pickerExplorer = new InViewPickerExplorerComposite(this.pageBook, SWT.NONE, this, pickerListVisible, paramListVisible, contextMenuEnabled, labelProvider);
			initializeActions();
		}
		this.pageBook.showPage(this.pickerExplorer);
		getSite().setSelectionProvider(pickerExplorer.pickerExplorerTree.getTreeViewer());
		return this.pickerExplorer;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		{
			this.toggleGroupElementsAction = new ToggleGroupElementsAction(this);
			this.refreshElementAction = new RefreshElementAction(this);
			this.saveElementsAction = new SaveElementsAction(this);
		}
	}

	private void initializeActions() {
		this.toggleGroupElementsAction.updatePickerExplorer();
		this.refreshElementAction.updatePickerExplorer();
		this.saveElementsAction.updatePickerExplorer();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();

		manager.add(this.toggleGroupElementsAction);
		manager.add(this.refreshElementAction);
		manager.add(this.saveElementsAction);

	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {

		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(this.toggleGroupElementsAction);
		toolBarManager.add(this.refreshElementAction);
		toolBarManager.add(this.saveElementsAction);

	}

	void setGroupElementsActionChecked(boolean groupByPicker) {
		this.toggleGroupElementsAction.setChecked(groupByPicker);
	}

	void setGroupElementsActionEnabled(boolean b) {
		this.toggleGroupElementsAction.setEnabled(b);
	}

	void setRefreshElementActionEnabled(boolean b) {
		this.refreshElementAction.setEnabled(b);
	}
	
	void setSaveElementsActionEnabled(boolean b) {
		this.saveElementsAction.setEnabled(b);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		Object adaptedObject = pickerExplorer != null ? pickerExplorer.getAdapter(adapter) : null;
		if(adaptedObject == null) {
			adaptedObject = super.getAdapter(adapter);
		}

		return adaptedObject;
	}

}
