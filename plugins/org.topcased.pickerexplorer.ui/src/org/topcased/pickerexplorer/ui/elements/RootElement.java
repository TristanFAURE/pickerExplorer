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
package org.topcased.pickerexplorer.ui.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.topcased.iterators.configurations.exceptions.ConfigurationLoadingException;
import org.topcased.iterators.configurations.factories.IterableCreationManager;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.Activator;
import org.topcased.pickerexplorer.ui.exceptions.NullPickerException;
import org.topcased.pickerexplorer.ui.util.PickerExplorerUtils;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;

public class RootElement extends AbstractPickerExplorerElement {

	private List<ObjectElement> lRoots = new LinkedList<ObjectElement>();

	private boolean pickersEnabledByDefault = true;

	private PickerExplorerComposite pickerExplorer;

	/**
	 * Default constructor, adding the registered pickers to the root.
	 * 
	 * @param pickerExplorer
	 *        the picker explorer of which this is the root
	 */
	public RootElement() {
		this(-1, true, true, false, false);
	}

	public RootElement(int numberOfExpandableLevels, boolean withRegisteredPickers, boolean pickersEnabled, boolean groupByPicker, boolean exploreNonCachedChildren) {
		this.remainingLevels = numberOfExpandableLevels;
		this.pickersEnabledByDefault = pickersEnabled;
		this.groupByPicker = groupByPicker;
		this.exploreNonCachedChildren = exploreNonCachedChildren;
		if(withRegisteredPickers) {
			retrieveRegisteredPickers();
		}
	}

	public ObjectElement[] addAll(Object[] toAdd) throws InstantiationException, IllegalAccessException {
		ObjectElement[] wrapped = wrap(toAdd);
		this.lRoots.addAll(Arrays.asList(wrapped));
		return wrapped;
	}

	public void addAllPickers(List<IPicker> pickers) {
		try {
			this.availablePickers.addAll(Arrays.asList(wrapPickers(pickers.toArray(new IPicker[0]))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void empty() {
		this.lRoots = new LinkedList<ObjectElement>();
	}

	@Override
	public AbstractPickerExplorerElement[] getCachedChildren() {
		return getChildren(new NullProgressMonitor());
	}

	@Override
	public AbstractPickerExplorerElement[] doGetChildren(IProgressMonitor monitor) {
		return this.lRoots.toArray(new AbstractPickerExplorerElement[0]);
	}

	public void removeAll(ObjectElement[] toRemove) {
		for(ObjectElement oe : toRemove) {
			this.lRoots.remove(oe);
		}
	}

	public void setRemainingLevels(int maxDepth) {
		this.remainingLevels = maxDepth;
	}

	/**
	 * This method returns a set of all the pickers
	 * that have been found into the workspace
	 * or provided by extensions.
	 * 
	 * @return
	 */
	private Set<ConfigurablePicker> getAllRegisteredPickers() {
		IterableCreationManager manager;
		try {
			manager = IterableCreationManager.getInstance();
			Map<String, IPicker> pickerMap = manager.getAvailablePickers();

			Set<ConfigurablePicker> allAvailablePickers = new HashSet<ConfigurablePicker>();
			for(String name : pickerMap.keySet()) {
				ConfigurablePicker newConfigurablePicker = new ConfigurablePicker(name, pickerMap.get(name), this);
				newConfigurablePicker.setEnabled(this.pickersEnabledByDefault);
				allAvailablePickers.add(newConfigurablePicker);
			}
			return allAvailablePickers;
		} catch (ConfigurationLoadingException e) {
			// FIXME handle exception properly
			Activator.getDefault().getLog().log(e.getStatus());
		}
		return Collections.emptySet();
	}

	private void retrieveRegisteredPickers() {
		this.availablePickers.addAll(getAllRegisteredPickers());
	}

	private ObjectElement wrap(Object domainObject) throws InstantiationException, IllegalAccessException {
		ObjectElement oe = new ObjectElement(domainObject, this);
		oe.setParentElement(this);
		return oe;
	}

	private ObjectElement[] wrap(Object[] unwrapped) throws InstantiationException, IllegalAccessException {
		ObjectElement[] wrapped = new ObjectElement[unwrapped.length];
		for(int i = 0; i < unwrapped.length; i++) {
			wrapped[i] = wrap(unwrapped[i]);
		}
		return wrapped;
	}

	private ConfigurablePicker wrapPicker(IPicker picker) throws InstantiationException, IllegalAccessException, NullPickerException {
		if (picker == null) {
			throw new NullPickerException();
		}
		String pickerName = PickerExplorerUtils.getName(picker);
		ConfigurablePicker cPicker = new ConfigurablePicker(pickerName , picker, this);
		cPicker.setEnabled(this.pickersEnabledByDefault);
		return cPicker;
	}

	private ConfigurablePicker[] wrapPickers(IPicker[] unwrapped) throws InstantiationException, IllegalAccessException, NullPickerException {
		ConfigurablePicker[] wrapped = new ConfigurablePicker[unwrapped.length];
		for(int i = 0; i < unwrapped.length; i++) {
			wrapped[i] = wrapPicker(unwrapped[i]);
		}
		return wrapped;
	}

	public PickerExplorerComposite getPickerExplorer() {
			return pickerExplorer;
	}

	public void setPickerExplorer(PickerExplorerComposite pickerExplorer) {
		this.pickerExplorer = pickerExplorer;
	}
	
	@Override
	public void accept(PickerExplorerElementVisitor visitor, int hierarchicalLevel) {
		if (visitor.visitRootElement(this, hierarchicalLevel))
		{
			super.accept(visitor,hierarchicalLevel);
		}
	}

}
