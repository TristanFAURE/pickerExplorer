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
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;


public class DisablePickerElementAction extends Action {

	private PickerExplorerComposite pickerExplorer;
	private PickerElement pickerElement;

	public DisablePickerElementAction(PickerExplorerComposite pickerExplorer, PickerElement pickerElement) {
		super("Disable picker", Action.AS_PUSH_BUTTON);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/generic_element.gif"));
		this.pickerExplorer = pickerExplorer;
		this.pickerElement = pickerElement;
		setText("Disable picker");
	}

	@Override
	public void run() {
		pickerElement.getConfigurablePicker().setEnabled(false);
		pickerExplorer.refreshPickerExplorer(pickerElement.getParentElement());
		pickerExplorer.refreshPickerTable();
	}

}
