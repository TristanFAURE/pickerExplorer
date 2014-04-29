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
package org.topcased.pickerexplorer.ui.settransform;

import java.util.Set;


public interface ISetTransformer {

	/**
	 * This method is designed to transform a set of objects into another
	 * more semantic or more representative set. The original set may be
	 * the result set of a picker's execution. This raw set can be processed
	 * to provide more interesting information.
	 * @param set the original set of objects
	 * @return the processed set
	 */
	public Set<Object> transform(Set<Object> set);
	
}
