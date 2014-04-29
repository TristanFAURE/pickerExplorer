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
package org.topcased.pickerexplorer.ui.visitor;

import org.topcased.pickerexplorer.ui.elements.ObjectElement;
import org.topcased.pickerexplorer.ui.elements.PickerElement;
import org.topcased.pickerexplorer.ui.elements.PickerParameter;
import org.topcased.pickerexplorer.ui.elements.RootElement;
import org.topcased.pickerexplorer.ui.views.pickertree.TreeElementLabelProvider;

public class PickerExplorerElementVisitor 
{
	
	protected String modelTest;
	
	private boolean verbose = false;
	
	public PickerExplorerElementVisitor() {
		modelTest = "";
	}
	
	public boolean visitRootElement(RootElement rootElement, int hierarchicalLevel)
	{
		return true;
	}
	
	public boolean visitPickerElement(PickerElement pickerElement, int hierarchicalLevel)
	{
		TreeElementLabelProvider labelProvider = new TreeElementLabelProvider();
		if(verbose) System.out.print("Processing picker "+labelProvider.getText(pickerElement)+"... ");
		for(int i=0;i<hierarchicalLevel;i++)
		{
			modelTest += "\t";
		}
		modelTest += "* " + labelProvider.getText(pickerElement);
		PickerParameter[] paramTable = pickerElement.getConfigurablePicker().getParamTable();
		modelTest += " - Parameters = [";
		for(int i=0;i<paramTable.length;i++)
		{
			modelTest += "("+paramTable[i].getName()+"="+ paramTable[i].getValue()+")";
			if(i+1<paramTable.length){
				modelTest += ", ";
			}
		}
		modelTest += "]\n";
		if(verbose) System.out.println("Done");
		return true;
	}
	
	public boolean visitObjectElement(ObjectElement objectElement, int hierarchicalLevel)
	{
		TreeElementLabelProvider labelProvider = new TreeElementLabelProvider();
		if(verbose) System.out.print("Processing element "+labelProvider.getText(objectElement)+"... ");
		for(int i=0;i<hierarchicalLevel;i++)
		{
			modelTest += "\t";
		}
		modelTest += labelProvider.getText(objectElement);
		modelTest += "\n";
		if(verbose) System.out.println("Done");
		return true;
	}
	
	public String getModelTest(){
		return modelTest;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
}
