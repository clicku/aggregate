/*
 * Copyright (C) 2011 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.aggregate.client.widgets;

import java.util.ArrayList;

import org.opendatakit.aggregate.client.AggregateUI;
import org.opendatakit.aggregate.client.FilterSubTab;
import org.opendatakit.aggregate.client.filter.Filter;
import org.opendatakit.aggregate.client.filter.FilterGroup;
import org.opendatakit.aggregate.client.popups.HelpBalloon;
import org.opendatakit.aggregate.constants.common.UIConsts;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class CopyFilterGroupButton extends AbstractButtonBase implements ClickHandler {

	private static final String TOOLTIP_TEXT = "Save as new filter group";

	private static final String HELP_BALLOON_TXT = "This will save the current filters into a new filter" +
			"group";

	private FilterSubTab parentSubTab;

	public CopyFilterGroupButton(FilterSubTab parentSubTab) {
		super("Save As", TOOLTIP_TEXT);
		this.parentSubTab = parentSubTab;
		helpBalloon = new HelpBalloon(this, HELP_BALLOON_TXT);
	}

	@Override
	public void onClick(ClickEvent event) {
		super.onClick(event);

		FilterGroup filterGroup = parentSubTab.getDisplayedFilterGroup();

		ArrayList<Filter> newFilterGroupfilters = new ArrayList<Filter>();
		newFilterGroupfilters.addAll(filterGroup.getFilters());
		FilterGroup newGroup = new FilterGroup(UIConsts.FILTER_NONE, filterGroup.getFormId(), newFilterGroupfilters);

		// set the displaying filters to the newly saved filter group
		parentSubTab.switchFilterGroup(newGroup);
		AggregateUI.getUI().getTimer().refreshNow();
	}
}