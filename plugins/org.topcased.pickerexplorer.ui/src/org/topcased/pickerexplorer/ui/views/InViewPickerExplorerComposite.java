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
package org.topcased.pickerexplorer.ui.views;

import java.util.Properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.util.EMFProviderUtil;
import org.topcased.pickerexplorer.ui.util.PickerExplorerUtils;
import org.topcased.pickerexplorer.ui.util.UnEditablePropertySource;

/**
 * A subclass of {@link PickerExplorerComposite} that is designed
 * to fit into the {@link PickerExplorerView}. Hence, it provides additional features
 * such as toolbar actions, status bar messages and link
 * with the {@link Properties} view.
 * @author mgrihang
 *
 */
public class InViewPickerExplorerComposite extends PickerExplorerComposite implements IAdaptable {

	// the view
	private PickerExplorerView view;
	private PropertySheetPage myPropertySheetPage;

	InViewPickerExplorerComposite(Composite parent, int style, PickerExplorerView view, boolean pickerListVisible, boolean paramListVisible, boolean contextMenuEnabled, ILabelProvider labelProvider) {
		super(parent, style, pickerListVisible, paramListVisible, contextMenuEnabled, labelProvider);
		this.view = view;
	}

	@Override
	public void reactToSelection(Object obj) {

		super.reactToSelection(obj);

		provideInfoWith(obj);

		if(obj instanceof AbstractPickerExplorerElement) {
			AbstractPickerExplorerElement element = (AbstractPickerExplorerElement)obj;
			this.view.setGroupElementsActionChecked(element.isGroupByPicker());

			// enable actions
			this.view.setGroupElementsActionEnabled(true);
			this.view.setRefreshElementActionEnabled(true);
		} else {
			// disable actions
			this.view.setGroupElementsActionEnabled(false);
			this.view.setRefreshElementActionEnabled(false);
		}
		this.view.setSaveElementsActionEnabled(true);

	}

	@Override
	public void updateRelevantParts(Object obj) {

		super.updateRelevantParts(obj);

		provideInfoWith(obj);

	}

	private void provideInfoWith(Object object) {
		// print info on status bar
		this.view.getViewSite().getActionBars().getStatusLineManager().setMessage(PickerExplorerUtils.getImage(object), PickerExplorerUtils.getInfo(object));
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IPropertySheetPage.class)) {
			return getPropertySheetPage();
		}
		return null;
	}

	/**
	 * This method returns a {@link PropertySheetPage}
	 * allowing to view and edit properties for instances of {@link EObject}
	 * that are encapsuled into {@link ObjectElement}s.
	 * @return
	 */
	public IPropertySheetPage getPropertySheetPage() {
		if (myPropertySheetPage == null) {
			myPropertySheetPage = new PropertySheetPage();
			final AdapterFactoryContentProvider provider = EMFProviderUtil.getAdapterFactoryContentProvider();
			myPropertySheetPage.setPropertySourceProvider(new IPropertySourceProvider() {
				public IPropertySource getPropertySource(Object object) {
					IPropertySource source = null;
					if (object instanceof ObjectElement) {
						source =provider.getPropertySource(((ObjectElement) object).getDomainObject());
					} else {
						source = provider.getPropertySource(object);
					}
					if (source == null) {
						return null;
					}
					// override editing methods in the property source
					return new UnEditablePropertySource(source);
				}
			});
		}

		return myPropertySheetPage;
	}

}
