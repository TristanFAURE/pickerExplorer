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
package org.topcased.pickerexplorer.batch.pickerbatch.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.topcased.pickerexplorer.batch.pickerbatch.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PickerbatchFactoryImpl extends EFactoryImpl implements PickerbatchFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PickerbatchFactory init() {
		try {
			PickerbatchFactory thePickerbatchFactory = (PickerbatchFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.topcased.org/pickerbatch/1.0"); 
			if (thePickerbatchFactory != null) {
				return thePickerbatchFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PickerbatchFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PickerbatchFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PickerbatchPackage.PICKER_BATCH_MAPPINGS: return createPickerBatchMappings();
			case PickerbatchPackage.MAPPING: return createMapping();
			case PickerbatchPackage.PICKER: return createPicker();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PickerBatchMappings createPickerBatchMappings() {
		PickerBatchMappingsImpl pickerBatchMappings = new PickerBatchMappingsImpl();
		return pickerBatchMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Picker createPicker() {
		PickerImpl picker = new PickerImpl();
		return picker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PickerbatchPackage getPickerbatchPackage() {
		return (PickerbatchPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PickerbatchPackage getPackage() {
		return PickerbatchPackage.eINSTANCE;
	}

} //PickerbatchFactoryImpl
