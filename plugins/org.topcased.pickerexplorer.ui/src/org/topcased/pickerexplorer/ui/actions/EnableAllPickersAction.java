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
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;


public class EnableAllPickersAction extends Action {
	
	private PickerExplorerComposite pickerExplorer;
	private ObjectElement objectElement;

	public EnableAllPickersAction(PickerExplorerComposite pickerExplorer, ObjectElement objectElement) {
		super("Enable all pickers for this element", Action.AS_PUSH_BUTTON);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/generic_elements.gif"));
		this.pickerExplorer = pickerExplorer;
		this.objectElement = objectElement;
		setText("Enable all pickers");
	}

	@Override
	public void run() {
		objectElement.enableAllPickers();
		pickerExplorer.refreshPickerExplorer(objectElement);
		pickerExplorer.refreshPickerTable();
	}

}
