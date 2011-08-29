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

package org.opendatakit.aggregate.client;

import org.opendatakit.aggregate.client.exception.FormNotAvailableException;
import org.opendatakit.aggregate.client.filter.FilterGroup;
import org.opendatakit.aggregate.client.submission.SubmissionUISummary;
import org.opendatakit.aggregate.client.table.SubmissionTable;
import org.opendatakit.aggregate.constants.common.SubTabs;
import org.opendatakit.common.security.common.GrantedAuthorityName;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;

public class SubmissionPanel extends ScrollPanel {

  private SubmissionTable submissionTable;

  public SubmissionPanel() {
    super();
    getElement().setId("submission_container");
  }

  public void update(FilterGroup filterGroup) {
    // Set up the callback object.
    AsyncCallback<SubmissionUISummary> callback = new AsyncCallback<SubmissionUISummary>() {
      public void onFailure(Throwable caught) {
        if(caught instanceof FormNotAvailableException) {
          // the form was not available, force an update of the panel to try to fix things
          SubTabInterface filterTab = AggregateUI.getUI().getSubTab(SubTabs.FILTER);
          if(filterTab != null) {
            filterTab.update();
          }
        } else {
          AggregateUI.getUI().reportError(caught);
        }
      }

      public void onSuccess(SubmissionUISummary summary) {
        AggregateUI.getUI().clearError();        
        boolean addDeleteButton = AggregateUI.getUI().getUserInfo().getGrantedAuthorities()
        .contains(GrantedAuthorityName.ROLE_DATA_OWNER);
        submissionTable = new SubmissionTable(summary, addDeleteButton);
        setWidget(submissionTable);
        AggregateUI.resize();
      }
    };

    if(filterGroup.getFormId() != null) {
    	SecureGWT.getSubmissionService().getSubmissions(filterGroup, callback);
    }
  }
  
  public SubmissionTable getSubmissionTable() {
    return submissionTable;
  }
}