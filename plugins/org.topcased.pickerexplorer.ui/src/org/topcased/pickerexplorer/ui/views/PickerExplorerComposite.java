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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;
import org.topcased.pickerexplorer.ui.elements.RootElement;
import org.topcased.pickerexplorer.ui.views.paramlist.ParamListComposite;
import org.topcased.pickerexplorer.ui.views.pickerlist.PickerListComposite;
import org.topcased.pickerexplorer.ui.views.pickertree.PickerTreeComposite;

/**
 * Instances of this class represent a picker explorer control,
 * containing a tree and two tables,
 * each displaying the tree structure of the elements,
 * the list of available pickers,
 * and the editable parameters of a picker.
 * <br>Depending on some options, the picker and parameter lists
 * may not be visible.
 * @author mgrihang
 *
 */
public class PickerExplorerComposite extends Composite {

	protected ParamListComposite paramList;

	protected PickerTreeComposite pickerExplorerTree;

	protected PickerListComposite pickerList;

	protected Form rightSideForm;

	protected FormToolkit toolkit;

	protected AbstractPickerExplorerElement lastSelectedElement;

	/**
	 * A custom constructor for this composite.
	 * @param parent
	 * @param style
	 * @param pickerListVisible
	 * @param paramListVisible
	 * @param contextMenuEnabled
	 * @param labelProvider
	 */
	public PickerExplorerComposite(Composite parent, int style, boolean pickerListVisible, boolean paramListVisible, boolean contextMenuEnabled, ILabelProvider labelProvider) {
		super(parent, style);
		this.toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new FillLayout());

		// if only the tree is visible
		if(!pickerListVisible && !paramListVisible) {
			createPickerTreeIn(this, contextMenuEnabled, labelProvider);
		} else {

			// create a sashForm to contain a right part and a left part
			SashForm sashForm = new SashForm(this, SWT.NONE);
			this.toolkit.adapt(sashForm);
			this.toolkit.paintBordersFor(sashForm);

			// left part
			createPickerTreeIn(sashForm, contextMenuEnabled, labelProvider);

			// right part
			this.rightSideForm = this.toolkit.createForm(sashForm);
			this.toolkit.adapt(this.rightSideForm);
			this.toolkit.paintBordersFor(this.rightSideForm);

			this.rightSideForm.getBody().setLayout(new GridLayout(1, false));

			if(pickerListVisible) {
				createPickerListIn(this.rightSideForm.getBody());
			}

			if(paramListVisible) {
				createParamListIn(this.rightSideForm.getBody());
			}

			sashForm.setWeights(new int[]{ 1, 1 });

		}
	}

	public void addChildrenToRootElementInTree(ObjectElement[] toAdd) {
		this.pickerExplorerTree.addRootElementsToTree(toAdd);
	}

	@Override
	public void dispose() {
		this.toolkit.dispose();
		super.dispose();
	}

	public RootElement getRootElement() {
		RootElement root = this.pickerExplorerTree.getRootElement();
		if(root == null) {
			// if not create a new one
			root = new RootElement();
			// set it as the viewer input
			pickerExplorerTree.setViewerInput(root);
		}
		return root;
	}

	public void setRootElement(RootElement root) {
		// input
		pickerExplorerTree.setViewerInput(root);
	}

	public AbstractPickerExplorerElement getSelectedElement() {
		//		if(this.pickerListTable != null) {
		//			return this.pickerListTable.getInputElement();
		//		}
		return this.lastSelectedElement;
	}

	/**
	 * Allows the picker explorer to react to the selection of an object,
	 * which can be an instance of {@link ObjectElement}, {@link PickerElement},
	 * {@link ConfigurablePicker} or {@link PickerParameter}.
	 * @param obj the selected object
	 */
	public void reactToSelection(Object obj) {

		if(obj instanceof AbstractPickerExplorerElement) {
			AbstractPickerExplorerElement element = (AbstractPickerExplorerElement)obj;

			// update last selected element
			this.lastSelectedElement = element;

			// picker list
			providePickerListWith(element);

			// handle possible exceptions during execution
			updateRightFormMessage(element);

			if(obj instanceof ObjectElement) {
				ObjectElement oe = (ObjectElement)element;
				providePickerListWith(oe);
				closeParamList();
			}

			if(obj instanceof PickerElement) {
				PickerElement pe = (PickerElement)element;
				provideParamTableWith(pe.getConfigurablePicker());
			}

		}

		if(obj instanceof ConfigurablePicker) {
			provideParamTableWith((ConfigurablePicker)obj);
		}

	}

	public void refreshParamListSelectedParam() {
		if(this.paramList != null) {
			this.paramList.refreshSelectedParameter();
		}
	}

	public void refreshPickerExplorer(AbstractPickerExplorerElement element) {
		if(this.pickerExplorerTree != null) {
			this.pickerExplorerTree.refreshElement(element);
		}
	}

	public void refreshPickerExplorerSelectedElement() {
		refreshPickerExplorer(getSelectedElement());
	}

	public void setTreeLabelProvider(ILabelProvider labelProvider) {
		if(this.pickerExplorerTree != null) {
			this.pickerExplorerTree.setLabelprovider(labelProvider);
		}
	}

	public void updateGroupModeOfElement(boolean groupByPicker) {
		getSelectedElement().setGroupByPicker(groupByPicker);
		refreshPickerExplorerSelectedElement();
	}

	public void updateRelevantParts(Object obj) {

		if(obj instanceof AbstractPickerExplorerElement) {
			AbstractPickerExplorerElement element = (AbstractPickerExplorerElement)obj;
			updateRightFormMessage(element);
		}

	}

	private void closeParamList() {
		if(this.paramList != null) {
			this.paramList.close();
		}
	}

	private void createParamListIn(Composite parent) {
		// create the param table
		this.paramList = new ParamListComposite(parent, SWT.None, this);
		this.paramList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.toolkit.adapt(this.paramList);
		this.toolkit.paintBordersFor(this.paramList);
	}

	private void createPickerListIn(Composite parent) {
		// create the picker list viewer
		this.pickerList = new PickerListComposite(parent, SWT.NONE, this);
		this.pickerList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.toolkit.adapt(this.pickerList);
		this.toolkit.paintBordersFor(this.pickerList);
	}

	private void createPickerTreeIn(Composite parent, boolean contextMenuEnabled, ILabelProvider labelProvider) {
		// create the tree viewer
		this.pickerExplorerTree = new PickerTreeComposite(parent, SWT.NONE, this, contextMenuEnabled, labelProvider);
		this.toolkit.adapt(this.pickerExplorerTree);
		this.toolkit.paintBordersFor(this.pickerExplorerTree);
	}

	private void provideParamTableWith(ConfigurablePicker cPicker) {
		if(this.paramList != null) {
			this.paramList.setInput(cPicker);
		}
	}

	private void providePickerListWith(AbstractPickerExplorerElement selectedElement) {
		if(this.pickerList != null) {
			this.pickerList.setInput(selectedElement);
		}
	}

	private void updateRightFormMessage(AbstractPickerExplorerElement element) {
		if(this.rightSideForm != null) {
			if(element.isError()) {
				this.rightSideForm.setMessage(element.getExecutionStatusMessage(), 3);
			} else {
				this.rightSideForm.setMessage(null);
			}
		}
	}

	public void refreshPickerTable() {
		this.pickerList.refresh();
	}

	/**
	 * This method adds an array of objects as new roots of the tree.
	 * It starts by adding them to the {@link RootElement},
	 * in order for them to be wrapped into {@link ObjectElement}s
	 * and inherit all the desired properties.
	 * Then, it adds them to the graphic part (the tree viewer).
	 * @param toAdd
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void addElements(Object[] toAdd) throws InstantiationException, IllegalAccessException {
		// give these objects to the RootElement
		ObjectElement[] wrappedToAdd = getRootElement().addAll(toAdd);
		// it will store them
		// and return wrapped objects to add to the tree viewer
		addChildrenToRootElementInTree(wrappedToAdd);

	}

	public String getLabelFor(AbstractPickerExplorerElement element) {
		return ((ILabelProvider) pickerExplorerTree.getTreeViewer().getLabelProvider()).getText(element);
	}

	public void refreshParamList() {
		paramList.refresh();
	}

	public void closeAll() {
		pickerList.close();
		paramList.close();
	}

}
