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
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.views.pickertree.PickerTreeComposite;


public class RemoveElementsAction extends Action {

	private ObjectElement[] toRemove;

	private PickerTreeComposite pickerExplorerTree;

	public RemoveElementsAction(PickerTreeComposite pickerExplorerTree, ObjectElement[] elements) {
		super(Messages.Message_remove, Action.AS_PUSH_BUTTON);
		setText(Messages.Message_remove);
		setToolTipText(Messages.Message_remove_selected);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/progress_rem-1.gif")); //$NON-NLS-1$ 
		this.pickerExplorerTree = pickerExplorerTree;
		this.toRemove = elements;
	}

	/*
	 * @see Action#actionPerformed
	 */
	@Override
	public void run() {
		this.pickerExplorerTree.removeRootElements(this.toRemove);
	}

}
