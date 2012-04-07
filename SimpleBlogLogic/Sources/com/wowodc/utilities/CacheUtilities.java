package com.wowodc.utilities;

import java.lang.reflect.Method;

import com.webobjects.eoaccess.EODatabase;
import com.webobjects.eoaccess.EODatabaseContext;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOObjectStoreCoordinator;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEnterpriseObjectCache;
import er.extensions.eof.ERXGenericRecord;

public class CacheUtilities {

  /**
   * Creates a standard EO cache object for the passed entity name and keypath for all  objects of
   * the entity that match restrictingQualifier.  If restrictingQualifier is null, all objects of this entity are
   * cached.
   *
   * @param entityName name of the EOEntity for the instances that will be in the cache
   * @param keyPath key path of unique value in EOs
   * @param restrictingQualifier EOQualifier restricting the set of objects in the cache
   * @param shouldFetchInitialValues true if the cache should be fully populated on first access
   * @return a standard EO cache object for the passed entity name and keypath
   * @throws Exception 
   */
  public static ERXEnterpriseObjectCache<? extends EOEnterpriseObject> createCache(String entityName, String keyPath, EOQualifier restrictingQualifier, boolean shouldFetchInitialValues) throws Exception    {
      /** require [valid_entityName] entityName != null;
                  [valid_keyPath] keyPath != null;
       **/

      EOEditingContext ec = new EOEditingContext();
      ec.lock();
      try
      {
          EOObjectStoreCoordinator osc = (EOObjectStoreCoordinator) ec.rootObjectStore();
          osc.lock();

          try
          {
              long timeout = 100; // TODO get this from a property
              boolean shouldRetainObjects = true;
              boolean shouldReturnUnsavedObjects = true;  // Needed for imports where new objects reference other new objects
              ERXEnterpriseObjectCache<? extends EOEnterpriseObject> cache = new ERXEnterpriseObjectCache(entityName, keyPath, restrictingQualifier, timeout,
                                                                            shouldRetainObjects, shouldFetchInitialValues, shouldReturnUnsavedObjects);

              /**
               * OK, things get nasty here.  You HAVE been warned!
               *
               * The cache has an interaction with the EOEditingContext fetchTimestamp()/defaultFetchTimestampLag().  After
               * the objects have been fetched into the cache, if the defaultFetchTimestampLag() passes before they are
               * re-fetched, when they are faulted into a new editing context (localInstanceOfObject), the snapshot will be discarded
               * and the objects re-fetched, one by one.  This rather eliminates the value of the cache.
               *
               * There are a few options to fix this:
               * - use a large defaultFetchTimestampLag() and ensure that all the places that need fresh data use
               *   a fetch specification that refreshes re-fetched objects.  This also means that you must pre-fetch all
               *   the objects that need fresh data.  This makes the defaultFetchTimestampLag() rather useless to control
               *   data freshness.
               *
               * - use a large defaultFetchTimestampLag() and implement ERChangeNotification to keep all the EOF stacks
               *   in sync.  This ensures current data without needing to use defaultFetchTimestampLag().
               *
               * - use a custom EODatabaseContext.delegate and implement the delegate method
               *   databaseContextShouldFetchObjectFault(EODatabaseContext, Object) to use the existing snapshot regardless of age.
               *   To implement this, the delegate will need to know that the entity is being cached.  This can be done by setting
               *   a flag in EOEntity.userInfo in this method.
               *
               * - make the objects as "Cache in Memory" in the EOModel.  The large drawback of this is that the objects will never
               *   be refreshed.  Refreshing fetch specifications do not affect entities cached in memory.
               *
               * - mark the snapshots of the cached objects as expiring at some time in the distant future.  As they expire from the
               *   cache they will be re-fetched and refreshed from the database.  This option was chosen as it more closely matches
               *   what should happen.  It does require access to a protected method in EODatabase.  It is possible that future
               *   versions of WebObjects will break this implementation, but there should be some way of achieving the result.
               */
              EOEntity entity = EOUtilities.entityNamed(ec, entityName);
              EODatabaseContext dbContext = EOUtilities.databaseContextForModelNamed(ec, entity.model().name());
              EODatabase database = dbContext.database();
              NSArray objectsInCache = cache.allObjects(ec);
              for (int i = 0; i < objectsInCache.count(); i++)
              {
                  EOEnterpriseObject eo = (EOEnterpriseObject) objectsInCache.objectAtIndex(i);
                  // Sets the expiration timestamp for the snapshot to NSTimestamp.DistantFuture.getTime()
                  _setTimestampForCachedGlobalID().invoke(database, ec.globalIDForObject(eo));
              }

              return cache;
          }
          catch (Exception e)
          {
              throw new Exception(e);
          }
          finally
          {
              osc.unlock();
          }
      }
      finally
      {
          ec.unlock();
          ec.dispose();
      }

      /** ensure [valid_result] Result != null;  **/
  }


  private static Method _setTimestampForCachedGlobalID = null;
  /**
   * Hack to access protected method in EODatabase.
   * @return Method for protected void _setTimestampForCachedGlobalID(EOGlobalID gid)
   * @throws Exception 
   */
  private static Method _setTimestampForCachedGlobalID() throws Exception
  {
      synchronized (ERXGenericRecord.class)
      {
          if (_setTimestampForCachedGlobalID == null)
          {
              try
              {
                  _setTimestampForCachedGlobalID = EODatabase.class.getDeclaredMethod("_setTimestampForCachedGlobalID",
                                                                                      new Class[] {EOGlobalID.class});
                  _setTimestampForCachedGlobalID.setAccessible(true);
              }
              catch (Exception e)
              {
                  throw new Exception(e);
              }
          }
      }

      return _setTimestampForCachedGlobalID;
  }
  
}
