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
package org.topcased.pickerexplorer.ui.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.topcased.iterators.configurations.pickers.PickerWithDescription;
import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;

public class PickerExplorerUtils {

	private static AdapterFactoryLabelProvider defaultLabelProvider = null;

	static {
			defaultLabelProvider = new AdapterFactoryLabelProvider.ColorProvider(new ComposedAdapterFactory(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)),Display.getDefault().getSystemColor(SWT.COLOR_BLACK),Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		}
	
	public static Image getImage(Object o) {
		if(o instanceof ObjectElement) {
			Object obj = ((ObjectElement)o).getDomainObject();
			if(obj instanceof EObject) {
				EObject eo = (EObject)obj;
				return defaultLabelProvider.getImage(eo);
			}
			if(obj instanceof Boolean) {
				return defaultLabelProvider.getImage(obj);
			}
			if(obj instanceof Integer) {
				return defaultLabelProvider.getImage(obj);
			}
			if(obj instanceof Double) {
				return defaultLabelProvider.getImage(obj);
			}
		}
		if(o instanceof PickerElement) {
			IPicker picker = ((PickerElement)o).getConfigurablePicker().getPicker();
			return defaultLabelProvider.getImage(picker);
		}
		if(o instanceof ConfigurablePicker) {
			ConfigurablePicker picker = (ConfigurablePicker)o;
			return defaultLabelProvider.getImage(picker.getPicker());
		}
		if(o instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)o;
			Object obj = param.getValue();
			if(obj instanceof EObject) {
				EObject eo = (EObject)obj;
				return defaultLabelProvider.getImage(eo);
			}
			if(obj instanceof Boolean) {
				return defaultLabelProvider.getImage(obj);
			}
			if(obj instanceof Integer) {
				return defaultLabelProvider.getImage(obj);
			}
			if(obj instanceof Double) {
				return defaultLabelProvider.getImage(obj);
			}
		}

		return null;
	}

	public static String getInfo(Object object) {
		// extract different information depending on the type of object
		StringBuilder builder = new StringBuilder(""); //$NON-NLS-1$

		if(object instanceof ObjectElement) {
			ObjectElement oe = (ObjectElement)object;
			builder.append(defaultLabelProvider.getText(oe.getDomainObject()));
			// if it's an EObject display its resource
			if(oe.getDomainObject() instanceof EObject) {
				EObject eo = (EObject) oe.getDomainObject();
				URI uri = EcoreUtil.getURI(eo);
				String path = uri.trimFragment().path();
				if(!eo.eIsProxy()) {
					builder.append(String.format(Messages.Message_in_resource, path));
				} else {
						builder.append(String.format(Messages.Message_proxy_in_resource, path));
				}
			}
			if(oe.getExecutionTime() != -1) {
				builder.append(printChildrenAndExecTime(oe));
			}
			if(oe.isError()) {
				builder.append(Messages.Message_error_during_execution);
			}
		}

		if(object instanceof PickerElement) {
			PickerElement pe = (PickerElement)object;
			builder.append(pe.getConfigurablePicker().getName());
			if(pe.getExecutionTime() != -1) {
				builder.append(printChildrenAndExecTime(pe));
			}
			if(pe.isError()) {
				builder.append(Messages.Message_error_during_execution);
			}
		}

		if(object instanceof ConfigurablePicker) {
			ConfigurablePicker cPicker = (ConfigurablePicker)object;
			builder.append(cPicker.getName());
			IPicker picker = cPicker.getPicker();
			builder.append(Messages.Message_two_dots+PickerExplorerUtils.getDescription(picker));
		}

		if(object instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)object;
			builder.append(param.getName() + " : " + param.getLabel()); //$NON-NLS-1$
		}

		return builder.toString();
	}

	private static String printChildrenAndExecTime(AbstractPickerExplorerElement element) {
		if(element.getExecutionTime() >= 1000000) {
			return String.format(Messages.Message_elements_fetched_in_millis, element.getNumberOfChildren(), element.getExecutionTime() / 1000000);
		} else {
			return String.format(Messages.Message_elements_fetched_in_nanos, element.getNumberOfChildren(), element.getExecutionTime());
		}
	}
	
	public static String getDescription(IPicker picker) {
		String description = Messages.Message_no_description_available;
		if(picker instanceof PickerWithDescription) {
			PickerWithDescription pickerWithDescription = (PickerWithDescription) picker;
			if (!(pickerWithDescription.getDescription() == null || pickerWithDescription.getDescription().equals(""))) { //$NON-NLS-1$
				description = pickerWithDescription.getDescription();
			}
			else {
				String className = pickerWithDescription.getCorePicker().getClass().getSimpleName();
				if (className == null || className.equals("")) { //$NON-NLS-1$
					className = pickerWithDescription.getClass().getName();
				}
				description = "<"+className+">"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		else {
			String className = picker.getClass().getSimpleName();
			if (className == null || className.equals("")) { //$NON-NLS-1$
				className = picker.getClass().getName();
			}
			description = "<"+className+">"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return description;
	}
	
	public static String getName(IPicker picker) {
		String name = Messages.Message_unnamed_picker;
		if(picker instanceof PickerWithDescription) {
			PickerWithDescription pickerWithDescription = (PickerWithDescription) picker;
			if (!(pickerWithDescription.getName() == null || pickerWithDescription.getName().equals(""))) { //$NON-NLS-1$
				name = pickerWithDescription.getName();
			}
		}
		return name;
	}
	
	/**
	 * Get the {@link EObject} from an instance of {@link Object}, if possible.
	 * @param obj the object to adapt
	 * @return
	 */
	public static EObject resolveSemanticElement(Object obj) {
		return null;
		
	}

	public static AdapterFactoryLabelProvider getDefaultLabelProvider() {
		return defaultLabelProvider;
	}
	
	public static IJavaQuery getJavaQuery(IPicker iPicker) {
		if (iPicker instanceof IJavaQuery) {
			return (IJavaQuery) iPicker;
		}
		else {
			if (iPicker instanceof PickerWithDescription) {
				PickerWithDescription wPicker = (PickerWithDescription)iPicker;
				return (IJavaQuery) wPicker.getAdapter(IJavaQuery.class);
			}
			else {
				return null;
			}
		}
	}
	
	public static IPicker tryToCopyPickerFrom(IPicker iPicker) throws InstantiationException, IllegalAccessException {
		if (iPicker instanceof PickerWithDescription) {
			PickerWithDescription wPicker = (PickerWithDescription)iPicker;
			return wPicker.copy();
		}
		return iPicker;
	}

}
