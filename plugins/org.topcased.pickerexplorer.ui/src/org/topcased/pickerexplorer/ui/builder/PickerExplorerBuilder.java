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
package org.topcased.pickerexplorer.ui.builder;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.topcased.iterators.configurations.factories.IterableCreationManager;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.Activator;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.RootElement;
import org.topcased.pickerexplorer.ui.exceptions.NullPickerException;
import org.topcased.pickerexplorer.ui.views.InViewPickerExplorerComposite;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;
import org.topcased.pickerexplorer.ui.views.PickerExplorerView;
import org.topcased.pickerexplorer.ui.views.pickertree.TreeElementLabelProvider;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;

/**
 * The purpose of this class is to provide convenience methods
 * for setting up the configuration of a picker explorer
 * and running it.
 * @author mgrihang
 *
 */
public class PickerExplorerBuilder {

	private static final String REGISTERED_PICKER_ID = Messages.Message_registered_picker;
	private static final String PLUGIN_ID = Messages.Message_plugin_id;
	private PickerExplorerConfiguration config;

	/**
	 * The default constructor, providing default settings for the picker explorer.
	 */
	public PickerExplorerBuilder() {
		this.config = new PickerExplorerConfiguration();
	}

	/**
	 * Use this constructor to set up a builder with an existing {@link PickerExplorerConfiguration}.
	 * For example, after setting up a builder, it is possible to save its configuration,
	 * so the builder can be re-created later with the same configuration.
	 * 
	 * @param config the configuration to provide the builder with
	 */
	public PickerExplorerBuilder(PickerExplorerConfiguration config) {
		this.config = config;
	}

	/**
	 * This method adds an object of any type (e.g. an instance of {@link EObject})
	 * to the configuration. The added object will subsequently be set as a root
	 * of the picker explorer.
	 * @param toAdd the object to add as an input
	 * @return
	 */
	public PickerExplorerBuilder addElement(Object toAdd) {
		// add an element to the roots
		this.config.addElement(toAdd);
		return this;
	}

	/**
	 * This method adds an {@link IPicker} to the list of available pickers
	 * for this configuration. It is designed to add other pickers
	 * than the registered ones.
	 * The registered pickers may be added by calling {@link #setRetrieveRegisteredPickers(boolean)}.
	 * @param picker the picker to be added to the configuration
	 * @return
	 * @throws NullPickerException 
	 */
	public PickerExplorerBuilder addPicker(IPicker picker) throws NullPickerException {
		if (picker == null) {
			throw new NullPickerException();
		}
		// add a picker
		this.config.addPicker(picker);
		return this;
	}

	public PickerExplorerBuilder addPicker(String pickerId) throws NullPickerException {
		try {
			// first, search into the registered pickers
			IterableCreationManager manager;
			manager = IterableCreationManager.getInstance();

			Map<String, IPicker> pickerMap = manager.getAvailablePickers();
			if (pickerMap.containsKey(pickerId)) {
				addPicker(pickerMap.get(pickerId));
				// if found it, do not search further
				return this;
			}

			// retrieve a picker from an extension point and add it
			IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(PLUGIN_ID+Messages.Message_dot+REGISTERED_PICKER_ID);
			IConfigurationElement[] config = extensionPoint.getConfigurationElements();

			IPicker picker = null;

			for (IConfigurationElement configElement : config) {
				String id = configElement.getAttribute(Messages.Message_picker_id);

				if (id.equals(pickerId)) {
					picker = (IPicker) configElement.createExecutableExtension(Messages.Message_picker_class);
				}
			}

			this.config.addPicker(picker);

		} catch (CoreException e) {
			Activator.logException(e);
		}
		return this;
	}

	/**
	 * This method initializes an instance of {@link RootElement} with the parameters
	 * of the builder's configuration. Clients can then call the getChildren() method
	 * on the root and its children to expand the tree. The choice of the algorithm
	 * used to compute the tree (width-wise or depth-wise) is left to the user.
	 * @return
	 */
	public RootElement createRootFromConfig() {
		return new RootElement(config.getMaxDepth(), config.isRetrieveRegisteredPickers(), config.isAllPickersEnabledByDefault(), config.isGroupByPicker(), config.isExploreNonCachedChildren());
	}

	/**
	 * 
	 * @return the current {@link PickerExplorerConfiguration} of this builder
	 */
	public PickerExplorerConfiguration getConfiguration() {
		return config;
	}

	/**
	 * Run this builder. If a parent composite has not been set before
	 * by calling {@link #setParent(Composite)},
	 * the picker explorer will be created within the Picker Explorer view.
	 */
	public void run() {
		try {
			if(this.config.getParent() != null) {
				createInComposite();
			} else {
				createInView();
			}

		} catch (Exception e) {
			Activator.logException(e);
		}

	}

	public void run(IPickerRunnable runnable, Map<String, Object> params) {
		// FIXME what is this method supposed to do ?
		RootElement root = createRootFromConfig();
        try {
			PickerExplorerElementVisitor visitor = new PickerExplorerElementVisitor();
        	addPickersThenElements(root);
			runnable.run(root, visitor, params);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Sets wether the initial pickers are enabled or not
	 * when running the builder. The pickers are all enabled by default.
	 * @param selected
	 * @return
	 */
	public PickerExplorerBuilder setAllPickersEnabledByDefault(boolean selected) {
		// pickers selected by default ?
		this.config.setAllPickersEnabledByDefault(selected);
		return this;
	}

	/**
	 * Sets wether the contextual menu (e.g. right click) is enabled
	 * for the picker explorer tree. The default is <code>true</code>.
	 * @param cMenuEnabled
	 * @return
	 */
	public PickerExplorerBuilder setContextMenuEnabled(boolean cMenuEnabled) {
		// right click context menu enabled
		this.config.setContextMenuEnabled(cMenuEnabled);
		return this;
	}

	/**
	 * Sets wether the children of an element in the tree are grouped
	 * according to which picker created them. This option can be changed later
	 * for a selected element, either in the contextual menu or in the view's toolbar.
	 * The default value is <code>true</code>.
	 * @param groupByPicker
	 * @return
	 */
	public PickerExplorerBuilder setGroupByPicker(boolean groupByPicker) {
		// group by picker ?
		this.config.setGroupByPicker(groupByPicker);
		return this;
	}

	/**
	 * Sets the icon that will be used
	 * for the view or the composite containing the picker explorer.
	 * @param desc
	 * @return
	 */
	public PickerExplorerBuilder setIcon(ImageDescriptor desc) {
		// FIXME this method is not used yet, because I don't know how to set dynamically the view's or the parent composite's icon
		this.config.setIcon(desc);
		return this;
	}

	/**
	 * Sets a custom label provider for displaying
	 * elements in the tree. If a label provider is not set
	 * by calling this method, a default {@link TreeElementLabelProvider}
	 * will be used.
	 * @param labelProvider
	 * @return
	 */
	public PickerExplorerBuilder setLabelProvider(ILabelProvider labelProvider) {
		// custom label provider
		this.config.setLabelprovider(labelProvider);
		return this;
	}

	/**
	 * Sets the maximum depth of the tree.
	 * The default value is <code>-1</code>, allowing an infinite depth.
	 * @param maxDepth
	 * @return
	 */
	public PickerExplorerBuilder setMaxDepth(int maxDepth) {
		// maximum depth level
		this.config.setMaxDepth(maxDepth);
		return this;
	}
	
	/**
	 * Sets wether the visitor should explore on cached children or not.
	 * The default is <code>false</code>.
	 * @param exploreNonCachedChildren
	 * @return
	 */
	public PickerExplorerBuilder setExploreNonCachedChildren(boolean exploreNonCachedChildren) {
		this.config.setExploreNonCachedChildren(exploreNonCachedChildren);
		return this;
	}

	/**
	 * Sets wether the parameter list (allowing to view and edit
	 * the parameters of some pickers) is enabled. The default is <code>true</code>.
	 * @param paramListVisible
	 * @return
	 */
	public PickerExplorerBuilder setParamListVisible(boolean paramListVisible) {
		// param list
		this.config.setParamListVisible(paramListVisible);
		return this;
	}

	/**
	 * Sets the parent in which the picker explorer composite
	 * will be created. If none is set, the picker explorer will be created
	 * in the Picker Explorer view.
	 * @param parent
	 * @return
	 */
	public PickerExplorerBuilder setParent(Composite parent) {
		// parent composite
		this.config.setParent(parent);
		return this;
	}

	/**
	 * Sets wether the picker list (allowing to view, enable and disable
	 * the pickers on an element) is enabled. The default is <code>true</code>.
	 * @param pickerListVisible
	 * @return
	 */
	public PickerExplorerBuilder setPickerListVisible(boolean pickerListVisible) {
		// picker list
		this.config.setPickerListVisible(pickerListVisible);
		return this;
	}

	/**
	 * Sets wether the registered pickers
	 * (found in the workspace or provided by extension points)
	 * have to be retrieved
	 * at the initialization of the picker explorer. The default is <code>true</code>.
	 * @param retrieveRegisteredPickers
	 */
	public PickerExplorerBuilder setRetrieveRegisteredPickers(boolean retrieveRegisteredPickers) {
		// when creating the root, retrieve the registered pickers ?
		this.config.setRetrieveRegisteredPickers(retrieveRegisteredPickers);
		return this;
	}
	
	public PickerExplorerBuilder setTitle (String title){
		this.config.setTitle(title);
		return this;
	}

	/**
	 * This method adds the configuration's pickers
	 * to the root element. Children are then added,
	 * therefore inheriting the previously added pickers.
	 * <br> This method is called only once
	 * to initialize the {@link RootElement}.
	 * @param root
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void addPickersThenElements(RootElement root) throws InstantiationException, IllegalAccessException {
		// take care to add all the pickers to the root element
		root.addAllPickers(this.config.getPickers());
		// before adding the elements
		root.addAll(this.config.getElements().toArray());
	}

	private void createInComposite() throws InstantiationException, IllegalAccessException {
		PickerExplorerComposite pickerExplorer = new PickerExplorerComposite(this.config.getParent(), SWT.NONE, this.config.isPickerListVisible(), this.config.isParamListVisible(), this.config.isContextMenuEnabled(), this.config.getLabelProvider());
		initializeRoot(pickerExplorer);
	}

	private void createInView() throws InstantiationException, IllegalAccessException, PartInitException {
		PickerExplorerView view = (PickerExplorerView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(Messages.Message_picker_explorer_view);
		InViewPickerExplorerComposite pickerExplorer = view.showPickerExplorer(this.config.isPickerListVisible(), this.config.isParamListVisible(), this.config.isContextMenuEnabled(), this.config.getLabelProvider());
		if (this.config.getLabel() != null){
			view.setPartName(this.config.getLabel());
		}
		initializeRoot(pickerExplorer);
	}
	
	private void initializeRoot(PickerExplorerComposite pickerExplorer) throws InstantiationException, IllegalAccessException {
		RootElement root = createRootFromConfig();
		addPickersThenElements(root);
		pickerExplorer.setRootElement(root);
	}

}
