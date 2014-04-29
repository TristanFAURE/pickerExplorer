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
package org.topcased.pickerexplorer.ui.views.pickerlist;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.topcased.iterators.pickers.IPicker;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.util.PickerExplorerUtils;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;

public class PickerListComposite extends Composite implements ICheckStateListener, ISelectionChangedListener {

	private PickerExplorerComposite pickerExplorer;

	private final FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private Table pickerTable;

	private Section pickerListSection;

	private CheckboxTableViewer checkboxTableViewer;

	private PickerListLabelProvider pickerListLabelProvider;

	public PickerListComposite(Composite parent, int style, PickerExplorerComposite pickerExplorer) {
		super(parent, style);
		this.pickerExplorer = pickerExplorer;

		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		setLayout(fillLayout);

		this.pickerListSection = this.toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		this.toolkit.paintBordersFor(this.pickerListSection);
		this.pickerListSection.setText(""); //$NON-NLS-1$
		this.pickerListSection.setExpanded(true);

		Composite composite = new Composite(this.pickerListSection, SWT.NONE);
		this.toolkit.adapt(composite);
		this.toolkit.paintBordersFor(composite);
		this.pickerListSection.setClient(composite);
		TableColumnLayout tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);
		
		this.checkboxTableViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER | SWT.FULL_SELECTION);
		this.pickerTable = this.checkboxTableViewer.getTable();
		this.pickerTable.setHeaderVisible(true);
		this.pickerTable.setLinesVisible(true);
		this.toolkit.paintBordersFor(this.pickerTable);
		this.toolkit.adapt(this.pickerTable);
		
		TableViewerColumn tableViewerColumnName = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
		TableColumn columnName = tableViewerColumnName.getColumn();
		tcl_composite.setColumnData(columnName, new ColumnPixelData(200, true, true));
		columnName.setText(Messages.Message_name);
		
		TableViewerColumn tableViewerColumnDescription = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
		TableColumn columnDescription = tableViewerColumnDescription.getColumn();
		tcl_composite.setColumnData(columnDescription, new ColumnPixelData(300, true, true));
		columnDescription.setText(Messages.Message_description);
		
		//

		this.checkboxTableViewer.setContentProvider(new PickerListContentProvider());
		this.pickerListLabelProvider = new PickerListLabelProvider();
		this.checkboxTableViewer.setLabelProvider(this.pickerListLabelProvider);
		this.checkboxTableViewer.setCheckStateProvider(new PickerListCheckStateProvider());
		this.checkboxTableViewer.setComparator(new PickerListComparator());
		
		this.checkboxTableViewer.addCheckStateListener(this);
		this.checkboxTableViewer.addSelectionChangedListener(this);

		// hide when the view opens
		close();

	}

	public void checkStateChanged(CheckStateChangedEvent event) {
		// updates the input element
		Object stateChanged = event.getElement();
		if(stateChanged instanceof ConfigurablePicker) {

			ConfigurablePicker cPicker = (ConfigurablePicker)stateChanged;
			boolean enabled = event.getChecked();
			cPicker.setEnabled(enabled);

			// refresh the closest parent ObjectElement
			if(cPicker.getOwner() instanceof PickerElement) {
				this.pickerExplorer.refreshPickerExplorer(cPicker.getOwner().getParentElement());
			} else {
				this.pickerExplorer.refreshPickerExplorer(cPicker.getOwner());
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		this.pickerListLabelProvider.dispose();
		this.checkboxTableViewer.removeCheckStateListener(this);
		this.checkboxTableViewer.removePostSelectionChangedListener(this);
		this.toolkit.dispose();
	}

	public AbstractPickerExplorerElement getInputElement() {
		return (AbstractPickerExplorerElement)this.checkboxTableViewer.getInput();
	}

	public void selectionChanged(SelectionChangedEvent event) {
		selectionChanged(event.getSelection());
	}

	private void selectionChanged(ISelection selection) {
		Object firstElement = ((IStructuredSelection) selection).getFirstElement();
		if(firstElement instanceof ConfigurablePicker) {
			ConfigurablePicker cPicker = (ConfigurablePicker)firstElement;
			this.pickerExplorer.reactToSelection(cPicker);
		}
	}

	public void setInput(AbstractPickerExplorerElement element) {

		// if the input is the same, save the selected indexes
		ISelection pickerTableSelection = null;
		if (element.equals(getInputElement())) {
			pickerTableSelection = checkboxTableViewer.getSelection();
		}

		if (element instanceof ObjectElement) {
			this.pickerListSection.setText(Messages.Message_available_pickers_for + pickerExplorer.getLabelFor(element));			
		}
		else if (element instanceof PickerElement) {
			PickerElement pe = (PickerElement)element;
			IPicker picker = pe.getConfigurablePicker().getPicker();
			String desc = PickerExplorerUtils.getDescription(picker);
			this.pickerListSection.setText(desc);
		}
		
		this.checkboxTableViewer.setInput(element);
		this.pickerListSection.setExpanded(true);

		// set the selection if necessary
		if (pickerTableSelection != null) {
			checkboxTableViewer.setSelection(pickerTableSelection);
			// and obviously react to it
			selectionChanged(checkboxTableViewer.getSelection());
		}

		this.pickerListSection.setVisible(true);
	}

	/**
	 * Hides the picker list section
	 */
	public void close() {
		this.pickerListSection.setExpanded(false);
		this.pickerListSection.setVisible(false);
		this.pickerListSection.setText(Messages.Message_no_element_selected);
	}

	public void refresh() {
		this.checkboxTableViewer.refresh();
	}

}
