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
package org.topcased.pickerexplorer.batch.pickerbatch;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Picker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.topcased.pickerexplorer.batch.pickerbatch.Picker#getPickerId <em>Picker Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage#getPicker()
 * @model
 * @generated
 */
public interface Picker extends EObject {

	/**
	 * Returns the value of the '<em><b>Picker Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Picker Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Picker Id</em>' attribute.
	 * @see #setPickerId(String)
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage#getPicker_PickerId()
	 * @model
	 * @generated
	 */
	String getPickerId();

	/**
	 * Sets the value of the '{@link org.topcased.pickerexplorer.batch.pickerbatch.Picker#getPickerId <em>Picker Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Picker Id</em>' attribute.
	 * @see #getPickerId()
	 * @generated
	 */
	void setPickerId(String value);
} // Picker
