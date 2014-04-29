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

import java.util.Map;

import org.topcased.pickerexplorer.ui.elements.RootElement;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;


public interface IPickerRunnable {
	public void run (RootElement root, PickerExplorerElementVisitor visitor, Map<String, Object> params);
}
