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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.topcased.pickerexplorer.batch.pickerbatch.Picker;
import org.topcased.pickerexplorer.batch.pickerbatch.PickerbatchPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Picker</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.topcased.pickerexplorer.batch.pickerbatch.impl.PickerImpl#getPickerId <em>Picker Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PickerImpl extends EObjectImpl implements Picker {
	/**
	 * The default value of the '{@link #getPickerId() <em>Picker Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPickerId()
	 * @generated
	 * @ordered
	 */
	protected static final String PICKER_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPickerId() <em>Picker Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPickerId()
	 * @generated
	 * @ordered
	 */
	protected String pickerId = PICKER_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PickerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PickerbatchPackage.Literals.PICKER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPickerId() {
		return pickerId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPickerId(String newPickerId) {
		String oldPickerId = pickerId;
		pickerId = newPickerId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PickerbatchPackage.PICKER__PICKER_ID, oldPickerId, pickerId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PickerbatchPackage.PICKER__PICKER_ID:
				return getPickerId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PickerbatchPackage.PICKER__PICKER_ID:
				setPickerId((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PickerbatchPackage.PICKER__PICKER_ID:
				setPickerId(PICKER_ID_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PickerbatchPackage.PICKER__PICKER_ID:
				return PICKER_ID_EDEFAULT == null ? pickerId != null : !PICKER_ID_EDEFAULT.equals(pickerId);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (pickerId: ");
		result.append(pickerId);
		result.append(')');
		return result.toString();
	}

} //PickerImpl
