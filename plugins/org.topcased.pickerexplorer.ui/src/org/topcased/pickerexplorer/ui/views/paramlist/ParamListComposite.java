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
package org.topcased.pickerexplorer.ui.views.paramlist;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.elements.ConfigurablePicker;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;

public class ParamListComposite extends Composite implements ISelectionChangedListener {

	private PickerExplorerComposite pickerExplorer;

	private Section paramListSection;

	private PickerParameter lastSelectedParameter;

	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Table paramTable;

	private TableViewer tableViewer;

	public ParamListComposite(Composite parent, int style, PickerExplorerComposite pickerExplorer) {
		super(parent, style);
		this.pickerExplorer = pickerExplorer;
		
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		setLayout(fillLayout);

		this.paramListSection = this.formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		this.formToolkit.paintBordersFor(this.paramListSection);
		this.paramListSection.setText(""); //$NON-NLS-1$
		this.paramListSection.setExpanded(true);

		Composite composite = new Composite(this.paramListSection, SWT.NONE);
		this.formToolkit.adapt(composite);
		this.formToolkit.paintBordersFor(composite);
		this.paramListSection.setClient(composite);
		TableColumnLayout tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);

		this.tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		this.paramTable = this.tableViewer.getTable();
		this.paramTable.setHeaderVisible(true);
		this.paramTable.setLinesVisible(true);
		this.formToolkit.paintBordersFor(this.paramTable);
		this.formToolkit.adapt(this.paramTable);

		TableViewerColumn paramColumn = new TableViewerColumn(this.tableViewer, SWT.NONE);
		TableColumn tblclmnParameter = paramColumn.getColumn();
		tcl_composite.setColumnData(tblclmnParameter, new ColumnPixelData(150, true, true));
		tblclmnParameter.setText(Messages.Message_parameter_label);

		TableViewerColumn valueColumn = new TableViewerColumn(this.tableViewer, SWT.NONE);
		TableColumn tblclmnValue = valueColumn.getColumn();
		tcl_composite.setColumnData(tblclmnValue, new ColumnPixelData(150, true, true));
		tblclmnValue.setText(Messages.Message_value_label);

		TableViewerColumn descriptionColumn = new TableViewerColumn(this.tableViewer, SWT.NONE);
		TableColumn tblclmnDescription = descriptionColumn.getColumn();
		tcl_composite.setColumnData(tblclmnDescription, new ColumnPixelData(150, true, true));
		tblclmnDescription.setText(Messages.Message_description_label);

		// label and content providers for the param table
		this.tableViewer.setLabelProvider(new ParamTableLabelProvider());
		this.tableViewer.setContentProvider(new ParamListContentProvider(pickerExplorer));
		this.tableViewer.addSelectionChangedListener(this);
		this.tableViewer.setColumnProperties(new String[]{ Messages.Message_param_id, Messages.Message_value_id, Messages.Message_description_id });
		
		// TODO editing support
		EditingSupport paramValueEditingSupport = new ParamValueEditingSupport(valueColumn.getViewer(), paramTable);
		valueColumn.setEditingSupport(paramValueEditingSupport);
				
		// hide when the view is created
		close();

	}

	/**
	 * Hides the parameter table section
	 */
	public void close() {
		this.paramListSection.setExpanded(false);
		this.paramListSection.setVisible(false);
		this.paramListSection.setText(Messages.Message_no_picker_selected);
	}

	@Override
	public void dispose() {
		// TODO remove the listeners ?
		super.dispose();
		this.formToolkit.dispose();
	}

	public void refreshSelectedParameter() {
		this.tableViewer.refresh(this.lastSelectedParameter);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof PickerParameter) {
			PickerParameter param = (PickerParameter)firstElement;
			this.lastSelectedParameter = param;
			this.pickerExplorer.reactToSelection(param);
		}
	}

	public void setInput(ConfigurablePicker configurablePicker) {
		// do it only if there are parameters to display
		if(configurablePicker.getParamTable().length > 0) {
			this.paramListSection.setText(Messages.Message_editable_parameters_of + configurablePicker.getName());
			this.tableViewer.setInput(configurablePicker);
			this.paramListSection.setExpanded(true);
			this.paramListSection.setVisible(true);
		}
		// else hide
		else {
			close();
		}
	}

	public void refresh() {
		tableViewer.refresh();
	}

	//	private ConfigurablePicker getInputElement() {
	//		return (ConfigurablePicker) tableViewer.getInput();
	//	}

}
