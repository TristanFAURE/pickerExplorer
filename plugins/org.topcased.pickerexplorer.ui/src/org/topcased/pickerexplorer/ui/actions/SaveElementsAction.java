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
package org.topcased.pickerexplorer.ui.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.topcased.pickerexplorer.ui.Messages;
import org.topcased.pickerexplorer.ui.views.PickerExplorerComposite;
import org.topcased.pickerexplorer.ui.views.PickerExplorerView;
import org.topcased.pickerexplorer.ui.visitor.PickerExplorerElementVisitor;


public class SaveElementsAction extends Action {

	private PickerExplorerComposite pickerExplorer;

	private PickerExplorerView view;

	public SaveElementsAction(PickerExplorerComposite pickerExplorer) {
		this();
		this.pickerExplorer = pickerExplorer;
	}

	public SaveElementsAction(PickerExplorerView view) {
		this();
		this.view = view;
	}

	private SaveElementsAction() {
		super(Messages.Message_save_elements, Action.AS_PUSH_BUTTON);
		setText(Messages.Message_save_elements);
		setImageDescriptor(ImageDescriptor.createFromImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_SAVE_EDIT)));
		setEnabled(false);
	}

	/*
	 * @see Action#actionPerformed
	 */
	@Override
	public void run() {
		if(this.pickerExplorer != null) {
			
			PickerExplorerElementVisitor visitor = new PickerExplorerElementVisitor();
			this.pickerExplorer.getRootElement().accept(visitor, -1);
			IFile iFile = WorkspaceResourceDialog.openNewFile(Display.getDefault().getActiveShell(), "Save as", "Enter a file name", null, null);
			if(iFile != null){
				InputStream is = new ByteArrayInputStream(visitor.getModelTest().getBytes());
				try {
					if(iFile.isAccessible())
					{
						iFile.delete(true, new NullProgressMonitor());
					}
					iFile.create(is, true, new NullProgressMonitor());
					System.out.println(visitor.getModelTest());
				} catch (CoreException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	public void updatePickerExplorer() {
		if(this.view != null) {
			this.pickerExplorer = this.view.getPickerExplorer();
		}
	}

}
