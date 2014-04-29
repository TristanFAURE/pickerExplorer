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
package org.topcased.pickerexplorer.ui.pickers.javaqueries;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.topcased.iterators.configurations.pickers.annotations.Argument;
import org.topcased.iterators.configurations.pickers.annotations.IJavaQuery;
import org.topcased.iterators.exceptions.PickerExecutionException;
import org.topcased.ocl.evaluation.exceptions.EvaluationException;
import org.topcased.ocl.evaluation.managers.OCLEvaluator;

public class OCLQuery implements IJavaQuery {

	private static OCLEvaluator evaluator;

	static {
		if (evaluator == null) {
			evaluator = new OCLEvaluator("http://www.topcased.org/iterators/1.0.0");
		}
	}

	@Argument(label = "The body of the OCL query")
	private String body = "self.eContents()";

	public Iterable<?> getNexts(Object element) throws PickerExecutionException {
		Iterable<?> result = Collections.EMPTY_LIST;

		if (element instanceof EObject) {
			// The query is evaluated on this eObject.
			EObject context = (EObject) element;
			// Parsing the body of the query.
			Object evalResult;
			try {
				evalResult = evaluator.evaluateQuery(body, context);
				if (evalResult != null) {
					if (evalResult instanceof Iterable<?>) {
						result = (Iterable<?>) evalResult;
					} else {
						result = Collections.singleton(evalResult);
					}
				}
			}
			/*
				 */catch (EvaluationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	};

}
