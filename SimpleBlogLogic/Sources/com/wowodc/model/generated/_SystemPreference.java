// DO NOT EDIT.  Make changes to com.wowodc.model.SystemPreference.java instead.
package com.wowodc.model.generated;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

import er.extensions.eof.*;
import er.extensions.foundation.*;

@SuppressWarnings("all")
public abstract class _SystemPreference extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SystemPreference";

  // Attribute Keys
  public static final ERXKey<String> HELPFUL_DESCRIPTION = new ERXKey<String>("helpfulDescription");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> PREF_VALUE = new ERXKey<String>("prefValue");
  // Relationship Keys

  // Attributes
  public static final String HELPFUL_DESCRIPTION_KEY = HELPFUL_DESCRIPTION.key();
  public static final String NAME_KEY = NAME.key();
  public static final String PREF_VALUE_KEY = PREF_VALUE.key();
  // Relationships

  private static Logger LOG = Logger.getLogger(_SystemPreference.class);

  public com.wowodc.model.SystemPreference localInstanceIn(EOEditingContext editingContext) {
    com.wowodc.model.SystemPreference localInstance = (com.wowodc.model.SystemPreference)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String helpfulDescription() {
    return (String) storedValueForKey(_SystemPreference.HELPFUL_DESCRIPTION_KEY);
  }

  public void setHelpfulDescription(String value) {
    if (_SystemPreference.LOG.isDebugEnabled()) {
    	_SystemPreference.LOG.debug( "updating helpfulDescription from " + helpfulDescription() + " to " + value);
    }
    takeStoredValueForKey(value, _SystemPreference.HELPFUL_DESCRIPTION_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SystemPreference.NAME_KEY);
  }

  public void setName(String value) {
    if (_SystemPreference.LOG.isDebugEnabled()) {
    	_SystemPreference.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SystemPreference.NAME_KEY);
  }

  public String prefValue() {
    return (String) storedValueForKey(_SystemPreference.PREF_VALUE_KEY);
  }

  public void setPrefValue(String value) {
    if (_SystemPreference.LOG.isDebugEnabled()) {
    	_SystemPreference.LOG.debug( "updating prefValue from " + prefValue() + " to " + value);
    }
    takeStoredValueForKey(value, _SystemPreference.PREF_VALUE_KEY);
  }


  public static com.wowodc.model.SystemPreference createSystemPreference(EOEditingContext editingContext, String helpfulDescription
, String name
, String prefValue
) {
    com.wowodc.model.SystemPreference eo = (com.wowodc.model.SystemPreference) EOUtilities.createAndInsertInstance(editingContext, _SystemPreference.ENTITY_NAME);    
		eo.setHelpfulDescription(helpfulDescription);
		eo.setName(name);
		eo.setPrefValue(prefValue);
    return eo;
  }

  public static ERXFetchSpecification<com.wowodc.model.SystemPreference> fetchSpec() {
    return new ERXFetchSpecification<com.wowodc.model.SystemPreference>(_SystemPreference.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.wowodc.model.SystemPreference> fetchAllSystemPreferences(EOEditingContext editingContext) {
    return _SystemPreference.fetchAllSystemPreferences(editingContext, null);
  }

  public static NSArray<com.wowodc.model.SystemPreference> fetchAllSystemPreferences(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SystemPreference.fetchSystemPreferences(editingContext, null, sortOrderings);
  }

  public static NSArray<com.wowodc.model.SystemPreference> fetchSystemPreferences(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.wowodc.model.SystemPreference> fetchSpec = new ERXFetchSpecification<com.wowodc.model.SystemPreference>(_SystemPreference.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.wowodc.model.SystemPreference> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.wowodc.model.SystemPreference fetchSystemPreference(EOEditingContext editingContext, String keyName, Object value) {
    return _SystemPreference.fetchSystemPreference(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.wowodc.model.SystemPreference fetchSystemPreference(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.wowodc.model.SystemPreference> eoObjects = _SystemPreference.fetchSystemPreferences(editingContext, qualifier, null);
    com.wowodc.model.SystemPreference eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SystemPreference that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.wowodc.model.SystemPreference fetchRequiredSystemPreference(EOEditingContext editingContext, String keyName, Object value) {
    return _SystemPreference.fetchRequiredSystemPreference(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.wowodc.model.SystemPreference fetchRequiredSystemPreference(EOEditingContext editingContext, EOQualifier qualifier) {
    com.wowodc.model.SystemPreference eoObject = _SystemPreference.fetchSystemPreference(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SystemPreference that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.wowodc.model.SystemPreference localInstanceIn(EOEditingContext editingContext, com.wowodc.model.SystemPreference eo) {
    com.wowodc.model.SystemPreference localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
