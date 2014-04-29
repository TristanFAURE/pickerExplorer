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
package org.topcased.pickerexplorer.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.wb.swt.ResourceManager;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;
import org.topcased.pickerexplorer.ui.views.PickerExplorerView;


public class ToggleGroupElementsAction extends Action {

	private PickerExplorerComposite pickerExplorer;

	private PickerExplorerView view;

	public ToggleGroupElementsAction(PickerExplorerComposite pickerExplorer) {
		this();
		this.pickerExplorer = pickerExplorer;
	}

	public ToggleGroupElementsAction(PickerExplorerView view) {
		this();
		this.view = view;
	}

	private ToggleGroupElementsAction() {
		super(Messages.Message_group_by_picker, Action.AS_CHECK_BOX);
		setText(Messages.Message_group_by_picker);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/problem_category.gif")); //$NON-NLS-1$ 
		setEnabled(false);
	}

	/*
	 * @see Action#actionPerformed
	 */
	@Override
	public void run() {
		if(this.pickerExplorer != null) {
			this.pickerExplorer.updateGroupModeOfElement(isChecked()); // will toggle the checked state						
		}
	}

	public void updatePickerExplorer() {
		if(this.view != null) {
			this.pickerExplorer = this.view.getPickerExplorer();
		}
	}

}
