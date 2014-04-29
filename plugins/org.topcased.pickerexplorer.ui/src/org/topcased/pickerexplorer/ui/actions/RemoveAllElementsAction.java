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
import org.topcased.pickerexplorer.ui.views.pickertree.PickerTreeComposite;


public class RemoveAllElementsAction extends Action {

	PickerTreeComposite pickerExplorerTree;

	public RemoveAllElementsAction(PickerTreeComposite tree) {
		super(Messages.Message_remove_all, Action.AS_PUSH_BUTTON);
		setText(Messages.Message_remove_all);
		setToolTipText(Messages.Message_remove_all_from_tree);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/progress_remall-1.gif"));
		this.pickerExplorerTree = tree;
	}

	/*
	 * @see Action#actionPerformed
	 */
	@Override
	public void run() {
		if(this.pickerExplorerTree != null) {
			this.pickerExplorerTree.removeAllRootElements();
		}
	}

}
