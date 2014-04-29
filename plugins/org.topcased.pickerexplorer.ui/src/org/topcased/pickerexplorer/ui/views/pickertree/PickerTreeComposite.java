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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.topcased.pickerexplorer.ui.actions.DisableAllPickersAction;
import org.topcased.pickerexplorer.ui.actions.DisablePickerElementAction;
import org.topcased.pickerexplorer.ui.actions.EnableAllPickersAction;
import org.topcased.pickerexplorer.ui.actions.OpenInEditorAction;
import org.topcased.pickerexplorer.ui.actions.RefreshElementAction;
import org.topcased.pickerexplorer.ui.actions.RemoveAllElementsAction;
import org.topcased.pickerexplorer.ui.actions.RemoveElementsAction;
import org.topcased.pickerexplorer.ui.actions.ToggleGroupElementsAction;
import org.topcased.pickerexplorer.ui.elements.AbstractPickerExplorerElement;
import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.elements.RootElement;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;

public class PickerTreeComposite extends Composite implements ISelectionChangedListener, KeyListener, ITreeViewerListener {

	private PickerExplorerComposite pickerExplorer;

	private TreeViewer treeViewer;

	private ElementFilter filter;

	private TreeElementContentProvider treeElementContentProvider;

	private Text filterText;

	private Menu contextMenu = null;

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	public PickerTreeComposite(Composite parent, int style, PickerExplorerComposite pickerExplorer, boolean contextMenuEnabled, ILabelProvider labelProvider) {
		super(parent, style);
		setLayout(new GridLayout(1, true));
		this.pickerExplorer = pickerExplorer;

		// the graphic part
		this.treeViewer = new TreeViewer(this, SWT.MULTI | SWT.BORDER);
		Tree tree = this.treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setBounds(0, 0, 87, 87);

		this.filterText = new Text(this, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
		this.filterText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		this.filterText.setBounds(0, 0, 76, 23);

		//

		this.treeElementContentProvider = new TreeElementContentProvider(pickerExplorer);
		this.treeViewer.setContentProvider(this.treeElementContentProvider);
		if (labelProvider != null) {
			if (labelProvider instanceof IStyledLabelProvider){
				this.treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider((IStyledLabelProvider) labelProvider));
			}
			else {
				this.treeViewer.setLabelProvider(labelProvider);			
			}
		}
		else {
			this.treeViewer.setLabelProvider(new TreeElementLabelProvider());
		}
		this.treeViewer.setSorter(new ViewerSorter());
		this.treeViewer.setUseHashlookup(true);
		this.treeViewer.addSelectionChangedListener(this);
		this.treeViewer.addTreeListener(this);

		this.filterText.addKeyListener(this);

		this.filter = new ElementFilter(this);

		if(contextMenuEnabled) {
			createContextMenu();
		}

	}

	public void addRootElementsToTree(ObjectElement[] toAdd) {
		// do not add them to the root element, they already have been added
		this.treeViewer.add(getRootElement(), toAdd);
	}

	@Override
	public void dispose() {
		super.dispose();
		this.treeViewer.removePostSelectionChangedListener(this);
		//		filterText.removeKeyListener(this);
	}

	public RootElement getRootElement() {
		return (RootElement) this.treeViewer.getInput();
	}

	public void keyPressed(KeyEvent e) {
		// do nothing
	}

	public void keyReleased(KeyEvent e) {
		if(e.keyCode == SWT.KEYPAD_CR || SWT.CR == e.keyCode) {
			// disable the pending update adapter
			this.treeElementContentProvider.setUseCachedChildren(true);

			// filter
			Object[] expandedElements = this.treeViewer.getExpandedElements();

			this.filter.setFilterString(this.filterText.getText());
			this.filter.computeFlags();
			applyFilter(this.filter);

			this.treeViewer.setExpandedElements(expandedElements);

			// enable it
			this.treeElementContentProvider.setUseCachedChildren(false);
		}
	}

	/**
	 * Refreshes a given element of the tree.
	 * 
	 * @param element
	 *        the element to be refreshed ; if it is <code>null</code> then
	 *        the entire tree is refreshed
	 */
	public void refreshElement(Object element) {
		if(element == null) {
			this.treeViewer.refresh();
		} else {
			this.treeViewer.refresh(element);
		}
	}

	public void refreshSelectedElement() {
		AbstractPickerExplorerElement toRefresh = this.pickerExplorer.getSelectedElement();
		refreshElement(toRefresh);
	}

	public void removeAllRootElements() {
		// remove them in the RootElement
		getRootElement().empty();
		removeAllRootElementsFromTree();
	}

	private void removeAllRootElementsFromTree() {
		// in the viewer
		this.treeViewer.getTree().removeAll();
		// close picker list and param list
		pickerExplorer.closeAll();
	}

	public void removeRootElements(AbstractPickerExplorerElement[] toRemove) {
		// remove them in the RootElement
		getRootElement().removeAll((ObjectElement[]) toRemove);
		removeRootElementsFromTree(toRemove);
	}

	private void removeRootElementsFromTree(AbstractPickerExplorerElement[] toRemove) {
		// in the viewer
		this.treeViewer.remove(getRootElement(), toRemove);
		// close picker list and param list
		pickerExplorer.closeAll();
	}

	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Object firstElement = selection.getFirstElement();

		if(firstElement instanceof AbstractPickerExplorerElement) {
			AbstractPickerExplorerElement element = (AbstractPickerExplorerElement)firstElement;
			this.pickerExplorer.reactToSelection(element);
		}

	}

	@Override
	public boolean setFocus() {
		return this.treeViewer.getControl().setFocus();
	}

	public void setLabelprovider(ILabelProvider labelProvider) {
		this.treeViewer.setLabelProvider(labelProvider);
	}

	public void setViewerInput(RootElement root) {
		this.treeViewer.setInput(root);
		root.setPickerExplorer(pickerExplorer);
	}

	public void treeCollapsed(TreeExpansionEvent event) {
		changeSelectionTo(event.getElement());
	}

	public void treeExpanded(TreeExpansionEvent event) {
		changeSelectionTo(event.getElement());
	}

	private void applyFilter(ViewerFilter filter) {
		this.treeViewer.resetFilters();
		this.treeViewer.addFilter(filter);
	}

	private void changeSelectionTo(Object element) {
		TreeItem selectedItem = (TreeItem)this.treeViewer.testFindItem(element);
		this.treeViewer.getTree().setSelection(selectedItem);
		// update the view
		this.pickerExplorer.reactToSelection(element);
	}

	private void createContextMenu() {

		// the contextual menu
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}

		});

		this.contextMenu = menuManager.createContextMenu(this.treeViewer.getControl());
		this.treeViewer.getControl().setMenu(this.contextMenu);

	}

	private void fillContextMenu(IMenuManager menuManager) {
		// add actions depending on the selection
		IStructuredSelection sel = (IStructuredSelection)this.treeViewer.getSelection();

		//***************************************
		// if we don't care about the selection

		// remove all
		RemoveAllElementsAction removeAllElementsAction = new RemoveAllElementsAction(this);
		menuManager.add(removeAllElementsAction);

		//*************************************
		// selection of at least one element
		if(sel.size() >= 1) {

			// remove one or several elements (remove)
			Set<AbstractPickerExplorerElement> roots = new HashSet<AbstractPickerExplorerElement>();
			for(Object o : sel.toList()) {
				AbstractPickerExplorerElement child = (AbstractPickerExplorerElement)o;
				roots.add(child.getRootAncestor());
			}
			RemoveElementsAction removeElementsAction = new RemoveElementsAction(this, roots.toArray(new ObjectElement[0]));
			menuManager.add(removeElementsAction);

		}

		//*****************************************
		// selection of only one element
		if(sel.size() == 1) {

			AbstractPickerExplorerElement selected = (AbstractPickerExplorerElement)sel.getFirstElement();


			// refresh
			RefreshElementAction refreshElementAction = new RefreshElementAction(this.pickerExplorer) {

				@Override
				public void run() {
					refreshSelectedElement();
				}
			};
			refreshElementAction.setEnabled(true);
			menuManager.add(refreshElementAction);

			// toggle group by picker
			ToggleGroupElementsAction toggleGroupElementsAction = new ToggleGroupElementsAction(this.pickerExplorer);
			toggleGroupElementsAction.setChecked(selected.isGroupByPicker());
			toggleGroupElementsAction.setEnabled(true);
			menuManager.add(toggleGroupElementsAction);

			// disable PickerElement
			if (selected instanceof PickerElement) {
				PickerElement pickerElement = (PickerElement) selected;
				DisablePickerElementAction togglePickerElementAction = new DisablePickerElementAction(this.pickerExplorer, pickerElement);
				togglePickerElementAction.setChecked(pickerElement.getConfigurablePicker().isEnabled());
				menuManager.add(togglePickerElementAction);
			}

			// enable all the pickers for this element
			if(selected instanceof ObjectElement) {
				ObjectElement objectElement = (ObjectElement) selected;
				EnableAllPickersAction enableAllPickersAction = new EnableAllPickersAction(this.pickerExplorer, objectElement);
				menuManager.add(enableAllPickersAction);
			}

			// disable all the pickers for this element
			if(selected instanceof ObjectElement) {
				ObjectElement objectElement = (ObjectElement) selected;
				DisableAllPickersAction disableAllPickersAction = new DisableAllPickersAction(this.pickerExplorer, objectElement);
				menuManager.add(disableAllPickersAction);
			}
			
			// open in an editor
			if (selected instanceof ObjectElement) {
				ObjectElement objectElement = (ObjectElement) selected;
				EObject eo = (EObject)objectElement.getAdapter(EObject.class);
				if (eo != null) {
					String uri = EcoreUtil.getURI(eo).toString();
					String path = EcoreUtil.getURI(eo).trimFragment().toPlatformString(true);
					OpenInEditorAction openInEditorAction = new OpenInEditorAction(path, uri);
					menuManager.add(openInEditorAction);
				}
			}
		}

	}

	IBaseLabelProvider getLabelProvider() {
		return (IBaseLabelProvider)this.treeViewer.getLabelProvider();
	}

	boolean isExpanded(AbstractPickerExplorerElement element) {
		return this.treeViewer.getExpandedState(element);
	}

}
