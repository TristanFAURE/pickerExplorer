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

public class RefreshElementAction extends Action {

	private PickerExplorerComposite pickerExplorer;

	private PickerExplorerView view;

	public RefreshElementAction(PickerExplorerComposite pickerExplorer) {
		this();
		this.pickerExplorer = pickerExplorer;
	}

	public RefreshElementAction(PickerExplorerView view) {
		this();
		this.view = view;
	}

	private RefreshElementAction() {
		super(Messages.Message_refresh_element, Action.AS_PUSH_BUTTON);
		setText(Messages.Message_refresh_element);
		setToolTipText(Messages.Message_refresh_selected_element);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/nav_refresh-1.gif"));
		setEnabled(false);
	}

	/*
	 * @see Action#actionPerformed
	 */
	@Override
	public void run() {
		if(this.pickerExplorer != null) {
			this.pickerExplorer.refreshPickerExplorerSelectedElement();
		}
	}

	public void updatePickerExplorer() {
		if(this.view != null) {
			this.pickerExplorer = this.view.getPickerExplorer();
		}
	}

}
