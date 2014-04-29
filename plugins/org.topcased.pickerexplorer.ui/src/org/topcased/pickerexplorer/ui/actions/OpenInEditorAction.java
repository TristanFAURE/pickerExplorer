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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wb.swt.ResourceManager;
import org.topcased.pickerexplorer.ui.Activator;
import org.topcased.pickerexplorer.ui.Messages;


public class OpenInEditorAction extends Action {

	private String path;
	private String uri;

	public OpenInEditorAction(String path, String uri) {
		super("Open", Action.AS_PUSH_BUTTON);
		setText("Open");
		setToolTipText("Open in an editor");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Messages.Message_plugin_id, "icons/goto_obj-1.gif"));
		this.path = path;
		this.uri = uri;
	}

	@Override
	public void run() {

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));

		IMarker marker = null;
		try {
			marker = file.createMarker(EValidator.MARKER);
			marker.setAttribute(EValidator.URI_ATTRIBUTE, uri);

			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

			IEditorPart editor = IDE.openEditor(page, file);
			IDE.gotoMarker(editor, marker);

		} catch (CoreException e) {
			Activator.logException(e);
		}
		finally {
			try {
				marker.delete();
			} catch (CoreException e) {
				Activator.logException(e);
			}
		}

	}

}
