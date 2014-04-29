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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchFactory
 * @model kind="package"
 * @generated
 */
public interface PickerbatchPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pickerbatch";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.topcased.org/pickerbatch/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pickerbatch";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PickerbatchPackage eINSTANCE = org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerBatchMappingsImpl <em>Picker Batch Mappings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerBatchMappingsImpl
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl#getPickerBatchMappings()
	 * @generated
	 */
	int PICKER_BATCH_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICKER_BATCH_MAPPINGS__MAPPINGS = 0;

	/**
	 * The number of structural features of the '<em>Picker Batch Mappings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICKER_BATCH_MAPPINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.MappingImpl
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 1;

	/**
	 * The feature id for the '<em><b>EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__EOBJECT = 0;

	/**
	 * The feature id for the '<em><b>Pickers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__PICKERS = 1;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerImpl <em>Picker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerImpl
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl#getPicker()
	 * @generated
	 */
	int PICKER = 2;

	/**
	 * The feature id for the '<em><b>Picker Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICKER__PICKER_ID = 0;

	/**
	 * The number of structural features of the '<em>Picker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICKER_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link org.topcased.pickerexplorer.batch.pickerbatch.PickerBatchMappings <em>Picker Batch Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Picker Batch Mappings</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerBatchMappings
	 * @generated
	 */
	EClass getPickerBatchMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.topcased.pickerexplorer.batch.pickerbatch.PickerBatchMappings#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.PickerBatchMappings#getMappings()
	 * @see #getPickerBatchMappings()
	 * @generated
	 */
	EReference getPickerBatchMappings_Mappings();

	/**
	 * Returns the meta object for class '{@link org.topcased.pickerexplorer.batch.pickerbatch.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference '{@link org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getEObject <em>EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EObject</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getEObject()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_EObject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getPickers <em>Pickers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pickers</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.Mapping#getPickers()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Pickers();

	/**
	 * Returns the meta object for class '{@link org.topcased.pickerexplorer.batch.pickerbatch.Picker <em>Picker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Picker</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.Picker
	 * @generated
	 */
	EClass getPicker();

	/**
	 * Returns the meta object for the attribute '{@link org.topcased.pickerexplorer.batch.pickerbatch.Picker#getPickerId <em>Picker Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Picker Id</em>'.
	 * @see org.topcased.pickerexplorer.batch.pickerbatch.Picker#getPickerId()
	 * @see #getPicker()
	 * @generated
	 */
	EAttribute getPicker_PickerId();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PickerbatchFactory getPickerbatchFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerBatchMappingsImpl <em>Picker Batch Mappings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerBatchMappingsImpl
		 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl#getPickerBatchMappings()
		 * @generated
		 */
		EClass PICKER_BATCH_MAPPINGS = eINSTANCE.getPickerBatchMappings();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PICKER_BATCH_MAPPINGS__MAPPINGS = eINSTANCE.getPickerBatchMappings_Mappings();

		/**
		 * The meta object literal for the '{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.MappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.MappingImpl
		 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>EObject</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__EOBJECT = eINSTANCE.getMapping_EObject();

		/**
		 * The meta object literal for the '<em><b>Pickers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__PICKERS = eINSTANCE.getMapping_Pickers();

		/**
		 * The meta object literal for the '{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerImpl <em>Picker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerImpl
		 * @see org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerbatchPackageImpl#getPicker()
		 * @generated
		 */
		EClass PICKER = eINSTANCE.getPicker();

		/**
		 * The meta object literal for the '<em><b>Picker Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PICKER__PICKER_ID = eINSTANCE.getPicker_PickerId();

	}

} //PickerbatchPackage
