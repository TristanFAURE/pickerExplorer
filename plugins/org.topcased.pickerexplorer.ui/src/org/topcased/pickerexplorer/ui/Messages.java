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
package org.topcased.pickerexplorer.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.topcased.pickerexplorer.ui.messages"; //$NON-NLS-1$

	public static String Message_name;

	public static String Message_null;

	public static String Message_picker_explorer_view;

	public static String Message_plugin_id;

	public static String Message_refresh_element;

	public static String Message_refresh_selected_element;

	public static String Message_remove;

	public static String Message_remove_all;

	public static String Message_remove_all_from_tree;

	public static String Message_remove_selected;

	public static String Message_available_pickers_for;

	public static String Message_container_set_transformer;

	public static String Message_description_label;

	public static String Message_editable_parameters_of;

	public static String Message_group_by_picker;
	
	public static String Message_save_elements;

	public static String Message_no_description_available;

	public static String Message_no_element_selected;

	public static String Message_no_picker_selected;

	public static String Message_parameter_label;

	public static String Message_unknown_type;

	public static String Message_unnamed_picker;

	public static String Message_unreachable_object;

	public static String Message_value_label;

	public static String Message_elements_fetched_in_millis;

	public static String Message_elements_fetched_in_nanos;

	public static String Message_error_during_execution;

	public static String Message_in_resource;

	public static String Message_proxy_in_resource;

	public static String Message_param_id;

	public static String Message_value_id;

	public static String Message_description;

	public static String Message_description_id;
	
	public static String Message_dot;

	public static String Message_picker_class;

	public static String Message_picker_id;

	public static String Message_registered_picker;

	public static String Message_set_transformer;

	public static String Message_set_transformer_class;

	public static String Message_set_transformer_id;

	public static String Message_two_dots;

	public static String Message_wildcard;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
