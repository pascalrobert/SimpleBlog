package com.wowodc.model.migration;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.wowodc.model.SystemPreference;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;
import er.extensions.migration.IERXPostMigration;

public class SimpleBlog6 extends ERXMigrationDatabase.Migration implements IERXPostMigration {
  
  @Override
  public NSArray<ERXModelVersion> modelDependencies() {
    return null;
  }
  
  @Override
  public void downgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
    // DO NOTHING
  }

  @Override
  public void upgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
    ERXMigrationTable systemPreferenceTable = database.newTableNamed("SystemPreference");
    systemPreferenceTable.newStringColumn("helpfulDescription", 1000, false);
    systemPreferenceTable.newIntegerColumn("id", false);
    systemPreferenceTable.newStringColumn("name", 100, false);
    systemPreferenceTable.newStringColumn("prefValue", 255, false);
    systemPreferenceTable.create();
    systemPreferenceTable.setPrimaryKey("id");
    
    systemPreferenceTable.addUniqueIndex("name");
  }

  /*
  Image sizes : Max Width  Max Height (non-Javadoc)
   */
  
  public void postUpgrade(EOEditingContext editingContext, EOModel model) throws Throwable {
    SystemPreference.createSystemPreference(editingContext, "Timezone for posts", "timezone", "GMT");
    SystemPreference.createSystemPreference(editingContext, "Date format", "dateFormat", "%Y/%m/%d %H:%M");
    SystemPreference.createSystemPreference(editingContext, "Title for this site", "siteTitle", "My blog");
    SystemPreference.createSystemPreference(editingContext, "Sub title for this site", "siteSubtitle", "My blog");
    SystemPreference.createSystemPreference(editingContext, "Allow remote editing (REST services)", "allowRESTPublishing", "yes");
    SystemPreference.createSystemPreference(editingContext, "Update Services. When you publish a new post, the system will automatically notifies the following site update services.", "updateServicesList", "");
    SystemPreference.createSystemPreference(editingContext, "Maximum number of posts in the RSS feed", "maxItemsInRss", "20");
    SystemPreference.createSystemPreference(editingContext, "Post listings show how many posts per page", "postsListingBatchSize", "10");
    SystemPreference.createSystemPreference(editingContext, "Allow people to post comments on new articles", "allowComments", "yes");
    SystemPreference.createSystemPreference(editingContext, "Enable threaded (nested) comments XX levels deep", "numberOfMaxCommentsThread", "3");
    SystemPreference.createSystemPreference(editingContext, "Number of comments per page", "postCommentsBatchSize", "20");
    //SystemPreference.createSystemPreference(editingContext, "Show more recent or older comment first", "postCommentsBatchSize", "20");
    SystemPreference.createSystemPreference(editingContext, "Show more recent or older comment first", "onlyUsersCanComment", "20");
    SystemPreference.createSystemPreference(editingContext, "Comments must be moderated", "mustModerateComments", "yes");
    //SystemPreference.createSystemPreference(editingContext, "Use trackbacks and pingbacks", "usePingbacks", "yes");
    SystemPreference.createSystemPreference(editingContext, "Email administrators when a new comment is posted", "emailAdminForComments", "yes");

    editingContext.saveChanges();
  }
}