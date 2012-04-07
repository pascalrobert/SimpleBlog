package com.wowodc.ui.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.wowodc.model.BlogComment;
import com.wowodc.model.BlogEntry;
import com.wowodc.model.SyncInfo;
import com.wowodc.model.SystemPreference;
import com.wowodc.model.enums.BlogCommentStatus;
import com.wowodc.rest.controllers.BlogEntryController;

import er.rest.routes.ERXRouteParameter;

public class BlogEntryDetailPage extends RestComponent {

  private BlogEntry entryItem;
  private SyncInfo syncDetails = null;
  public BlogComment commentItem;
  public BlogComment newComment;
  
  public BlogEntryDetailPage(WOContext context) {
    super(context);
  }

  public BlogEntry blogEntry() {
    return entryItem;
  }

  @ERXRouteParameter
  public void setBlogEntry(BlogEntry _entryItem) {
    this.entryItem = _entryItem;
  }
  
  public SyncInfo syncDetails() {
    return syncDetails;
  }
  
  public void setSyncDetails(SyncInfo syncDetails) {
    this.syncDetails = syncDetails;
  }
  
  public WOActionResults addComment() {
    SystemPreference shouldModeratePref = SystemPreference.fetchSystemPreference(syncDetails.editingContext(), SystemPreference.NAME.eq("mustModerateComments"));
    boolean shouldModerate = new Boolean(shouldModeratePref.prefValue());
    if (shouldModerate) {
      newComment.setStatus(BlogCommentStatus.AWAITING_ACTION);
    } else {
      newComment.setStatus(BlogCommentStatus.MODERATED);      
    }
    syncDetails.editingContext().saveChanges();
    return null;
  }
  
  @Override
  public void appendToResponse(WOResponse response, WOContext context) {
    if (syncDetails != null) {
      response.setHeader(BlogEntryController.formatter.format(syncDetails.lastModified()), "Last-Modified");
      response.setHeader(syncDetails.etag(), "Etag");
    }

    super.appendToResponse(response, context);
  }
}