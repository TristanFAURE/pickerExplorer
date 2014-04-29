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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getEObject <em>EObject</em>}</li>
 *   <li>{@link org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getPickers <em>Pickers</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage#getMapping()
 * @model
 * @generated
 */
public interface Mapping extends EObject {
	/**
	 * Returns the value of the '<em><b>EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EObject</em>' reference.
	 * @see #setEObject(EObject)
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage#getMapping_EObject()
	 * @model
	 * @generated
	 */
	EObject getEObject();

	/**
	 * Sets the value of the '{@link org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getEObject <em>EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EObject</em>' reference.
	 * @see #getEObject()
	 * @generated
	 */
	void setEObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Pickers</b></em>' containment reference list.
	 * The list contents are of type {@link org.topcased.pickerexplorer.batch.pickerbatch.Picker}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pickers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pickers</em>' containment reference list.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage#getMapping_Pickers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Picker> getPickers();

} // Mapping
