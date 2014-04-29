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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage
 * @generated
 */
public interface PickerbatchFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PickerbatchFactory eINSTANCE = org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Picker Batch Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Picker Batch Mappings</em>'.
	 * @generated
	 */
	PickerBatchMappings createPickerBatchMappings();

	/**
	 * Returns a new object of class '<em>Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping</em>'.
	 * @generated
	 */
	Mapping createMapping();

	/**
	 * Returns a new object of class '<em>Picker</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Picker</em>'.
	 * @generated
	 */
	Picker createPicker();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PickerbatchPackage getPickerbatchPackage();

} //PickerbatchFactory
