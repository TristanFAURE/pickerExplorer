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
package org.topcased.pickerexplorer.ui.views.pickertree;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.util.PickerExplorerUtils;


public class TreeElementLabelProvider extends LabelProvider implements IColorProvider, IStyledLabelProvider {

	private static AdapterFactoryLabelProvider factory = PickerExplorerUtils.getDefaultLabelProvider();

	private static Image pickerIcon = ResourceManager.getPluginImageDescriptor("org.topcased.pickerexplorer.ui", "icons/generic_element.gif").createImage(); //$NON-NLS-1$ //$NON-NLS-2$
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof ObjectElement) {
			Object obj = ((ObjectElement)element).getDomainObject();
			IStyledLabelProvider ofType2 = getOfType(IStyledLabelProvider.class, obj);
			if (ofType2 != null){
				return ofType2.getImage(obj);
			}
			ILabelProvider ofType = getOfType(ILabelProvider.class, obj);
			if (ofType != null){
				return ofType.getImage(obj);
			}
			return factory.getImage(obj);
		}
		if(element instanceof PickerElement) {
			return pickerIcon;
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {

		if(element instanceof ObjectElement) {
			Object obj = ((ObjectElement)element).getDomainObject();
			IStyledLabelProvider ofType2 = getOfType(IStyledLabelProvider.class, obj);
			if (ofType2 != null){
				return ofType2.getStyledText(obj).getString();
			}
			ILabelProvider ofType = getOfType(ILabelProvider.class, obj);
			if (ofType != null){
				return ofType.getText(obj);
			}
			return factory.getText(obj);
		}
		if(element instanceof PickerElement) {
			ConfigurablePicker cPicker = ((PickerElement)element).getConfigurablePicker();
			return cPicker.getName();
		}
		return element == null ? Messages.Message_null : element.toString();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	public Color getForeground(Object element) {
		if(element instanceof ObjectElement) {
			Object obj = ((ObjectElement)element).getDomainObject();
			IColorProvider ofType = getOfType(IColorProvider.class, obj);
			if (ofType != null){
				return ofType.getForeground(obj);
			}
			return factory.getForeground(obj);
		}
		if(element instanceof PickerElement) {
			ConfigurablePicker cPicker = ((PickerElement)element).getConfigurablePicker();
			return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		}
		return null;
	}

	public Color getBackground(Object element) {
		if(element instanceof ObjectElement) {
			Object obj = ((ObjectElement)element).getDomainObject();
			IColorProvider ofType = getOfType(IColorProvider.class, obj);
			if (ofType != null){
				return ofType.getBackground(obj);
			}
			return factory.getBackground(obj);
		}
		if(element instanceof PickerElement) {
			ConfigurablePicker cPicker = ((PickerElement)element).getConfigurablePicker();
			return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		}
		return null;
	}

	public <T> T getOfType (Class<T> aClass, Object o){
		T result = null; 
		if (aClass.isInstance(o)){
			result = (T)o;
		}
		if (result == null){
			if (o instanceof IAdaptable){
				result = (T) ((IAdaptable)o).getAdapter(aClass);
			}
		}
		if (result == null){
			result = (T) Platform.getAdapterManager().getAdapter(o, aClass);
		}
		return result ;
	}

	public StyledString getStyledText(Object element) {
		if(element instanceof ObjectElement) {
			Object obj = ((ObjectElement)element).getDomainObject();
			IStyledLabelProvider ofType = getOfType(IStyledLabelProvider.class, obj);
			if (ofType != null){
				return ofType.getStyledText(obj);
			}
		}
		StyledString result = new StyledString(getText(element));
		return result ;
	}
	
}
