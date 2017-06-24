package com.kxy.general.common.storage.cache.local;

import com.kxy.general.common.storage.DataMissCallback;
import com.kxy.general.common.storage.KvStorage;
import com.kxy.general.common.storage.cache.CacheReloadAdapter;
import com.kxy.general.common.storage.cache.ReadonlyKvCache;
import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.Version;
import com.kxy.general.common.versioncontrol.VersionQueryService;
import com.kxy.general.common.versioncontrol.changelog.rdb.RowChangeLog;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * caching table of database in local memory.
 *
 * - how data is synced
 *     there are three types of sync data:
 *     1. full table sync
 *         it is a timer, you can set interval by set parameter
 *         {@code reloadAllInterval}
 *     2. when sql: insert, update, delete is executing, we save what
 *        changed in change-log and cache will update these changes.
 *        there are two way to make change-log applied:
 *        1. you can set option {@code checkChangeBeforeReadRdbCache}, so cache
 *           will always check change-log before read
 *        2. a backyard timer thread is checking change-log for cache. you can
 *           set interval by {@code reloadInterval}
 *     3. you can call open api {@code ReloadRdbCache} to full sync cache
 *
 * - when to use this cache
 *     1. a table with a lost of read and less modify
 *     2. frequently full table read
 * - limitations
 *     1. tables with frequently changes may not use this cache.
 *     2. storage consistent required business should not use this cache.
 *
 * Created by xiangyun.kong on 6/13/17.
 */
public class LocalReadonlyKvMemoryCacheForDS implements ReadonlyKvCache {
    /**
     * data adapter between database and cache.
     * there is a general adapter implemented for most cases
     * {@code CacheReloadAutoAdapterForRdb}, you can use it for simplifying code
     */
    @Setter
    private CacheReloadAdapter storageForConsistent;

    /**
     * version service for querying the newest versions.
     */
    @Setter
    private VersionQueryService versionQueryService;

    /**
     * option for whether check change-log before read or not.
     * default is 'not'
     */
    @Setter
    private Boolean checkChangeBeforeReadRdbCache = false;

    /**
     * namespace used by the cache. a instance of cache should have a unique
     * namespace
     */
    @Setter
    @Getter
    private String versionNamespace;

    /**
     * the real storage used by cache.
     * here is {@code KvMemoryCache}
     */
    private KvStorage storage = new KvMemoryCache();

    /**
     * current version of maintained data in cache.
     * null is the smallest version
     */
    private Version currentVersion = null;

    /**
     * local cache for distributed storage is inconsistent. for I don't know
     * when it is updated by remote. and I didn't create a mechanism to make
     * it known and sync. actually I made it known but not sync
     */
    @Override
    public Boolean consistency() {
        return false;
    }

    /**
     * @see ReadonlyKvCache#getVersion()
     */
    @Override
    public Version getVersion() {
        return currentVersion;
    }

    /**
     * @see ReadonlyKvCache#updateChanges()
     */
    @Override
    public void updateChanges() {
        List<ChangeLog> changes = versionQueryService.queryChangeLogSince(
                versionNamespace, currentVersion);
        if (changes != null && changes.size() > 0) {
            Map<String, Serializable> newValues;

            invalidCache(changes);

            newValues = storageForConsistent.query(changes);
            storage.write(newValues);

            currentVersion = getMaxVersion(changes);
        }
    }

    /**
     * @see ReadonlyKvCache#query(String)
     */
    @Override
    public <T extends Serializable> T query(String key) {
        tryUpdateData();
        return storage.query(key);
    }

    /**
     * @see ReadonlyKvCache#query(String, Serializable)
     */
    @Override
    public <T extends Serializable> T query(String key, T miss) {
        tryUpdateData();
        return storage.query(key, miss);
    }

    /**
     * @see ReadonlyKvCache#query(List)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys) {
        tryUpdateData();
        return storage.query(keys);
    }

    /**
     * @see ReadonlyKvCache#query(List, DataMissCallback)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys,
            DataMissCallback<T> callback) {
        tryUpdateData();
        return storage.query(keys, callback);
    }

    /**
     * @see ReadonlyKvCache#queryAll()
     */
    @Override
    public <T extends Serializable> Map<String, T> queryAll() {
        tryUpdateData();
        return storage.queryAll();
    }

    /**
     * try to reload data. there're some conditions:
     * 1. a user controlled option {@code checkChangeBeforeReadRdbCache}
     * 2. changes already made
     */
    private void tryUpdateData() {
        if (checkChangeBeforeReadRdbCache) {
            Version version = versionQueryService.queryCurrentVersion(
                    versionNamespace);
            if (currentVersion == null
                    || currentVersion.compareTo(version) != 0) {
                this.updateChanges();
            }
        }
    }

    /**
     * every change-log contains a version.
     * try to find the max version.
     *
     * @param changeLogs change-logs
     * @return max version in the list
     */
    private Version getMaxVersion(List<ChangeLog> changeLogs) {
        Version maxVersion = null;
        for (ChangeLog changeLog : changeLogs) {
            if (changeLog.getVersion().compareTo(maxVersion) > 0) {
                maxVersion = changeLog.getVersion();
            }
        }
        return maxVersion;
    }

    /**
     * remove changed data.
     * @param changeLogs change-logs that newer then this.currentVersion()
     */
    private void invalidCache(List<ChangeLog> changeLogs) {
        if (isChangeAll(changeLogs)) {
            storage.removeAll();
        } else {
            List<String> keys = new ArrayList<>();
            for (ChangeLog changeLog : changeLogs) {
                if (changeLog instanceof RowChangeLog) {
                    keys.addAll(changeLog.getChangedObjectIds());
                }
            }
            storage.remove(keys);
        }
    }

    /**
     * check if there's a change-log marks 'all data is change'(all need
     * reload).
     *
     * @param changeLogs change-logs
     * @return true if has
     */
    private Boolean isChangeAll(List<ChangeLog> changeLogs) {
        for (ChangeLog changeLog : changeLogs) {
            if (changeLog.isAllChanged()) {
                return true;
            }
        }
        return false;
    }

}
