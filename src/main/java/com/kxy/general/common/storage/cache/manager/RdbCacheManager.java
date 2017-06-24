package com.kxy.general.common.storage.cache.manager;

import com.kxy.general.common.storage.KvStorage;
import com.kxy.general.common.storage.RdbStorage;
import com.kxy.general.common.storage.adapter.CacheReloadAutoAdapterForRdb;
import com.kxy.general.common.storage.bridge.ReadonlyKvCacheAgent;
import com.kxy.general.common.storage.cache.ReadonlyKvCache;
import com.kxy.general.common.storage.cache.local.LocalReadonlyKvMemoryCacheForDS;
import com.kxy.general.common.versioncontrol.VersionQueryService;
import com.kxy.general.common.versioncontrol.changelog.rdb.changegetter.RdbChangeHook;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * cache manager for {@link LocalReadonlyKvMemoryCacheForDS}.
 *
 * 1. it manage(config, read) caches' configs
 * 2. it manage caches' reload tasks
 *
 * Created by xiangyun.kong on 6/15/17.
 */
public class RdbCacheManager implements InitializingBean {
    /**
     * main cache config info. configured by spring most time.
     * per-map is a config for per-cache. there're list of caches.
     */
    @Setter
    private List<Map<String, String>> cacheInfoList;

    /**
     * version service used by cache.
     */
    @Setter
    private VersionQueryService versionQueryService;

    /**
     * change-log maker. it should not missed.
     */
    @Setter
    private RdbChangeHook rdbChangeHook;

    /**
     * a general database interface(implemented sqlmap of ibatis) used by
     * cache reload.
     */
    @Setter
    private RdbStorage ibatisRdbStorage;

    /**
     * config center. used for dynamic cache config:
     *      1. checkChangeBeforeReadRdbCache
     *      2. enableRdbCache
     */
    @Setter
    private KvStorage kvConfigStorage;

    /**
     * marks cache is enabled or disabled.
     */
    private Boolean enableCache = true;

    /**
     * loaded cache objects. indexed by table name
     */
    private Map<String, ReadonlyKvCache> caches =
            new HashMap<>();

    /**
     * loaded cache configs. indexed by table name
     */
    private Map<String, Map<String, String>> cachedTables =
            new HashMap<>();

    /**
     * get the key column by loaded table name from config.
     *
     * @param tableName table name. the table's cache should be correctly loaded
     * @return column name
     */
    public String getKeyColumnByTable(String tableName) {
        Map<String, String> config = cachedTables.get(tableName.trim());
        if (config != null) {
            return config.get("columnName");
        }
        return null;
    }

    /**
     * get the namespace by loaded table name from config.
     *
     * @param tableName table name. the table's cache should be correctly loaded
     * @return namespace
     */
    public String getNamespaceByTable(String tableName) {
        Map<String, String> config = cachedTables.get(tableName.trim());
        if (config != null) {
            return config.get("namespace");
        }
        return null;
    }

    /**
     * get cache object by table name.
     *
     * @param tableName table name. the table's cache should be correctly loaded
     * @return cache object
     */
    public ReadonlyKvCache getCacheByTable(String tableName) {
        return caches.get(tableName);
    }

    /**
     * check if cache is enabled.
     *
     * @return true if enabled
     */
    public Boolean isCacheEnabled() {
        return enableCache;
    }

    /**
     * init caches and cache reload tasks.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadCaches();
        beginReloadProcess();
    }

    /**
     * load caches by configs.
     */
    private void loadCaches() {
        for (Map<String, String> params : cacheInfoList) {
            if (checkParameters(params)) {
                ReadonlyKvCache cache = (ReadonlyKvCache) initCacheByParameter(
                        params);
                if (cache != null) {
                    caches.put(params.get("tableName"), cache);
                    cachedTables.put(params.get("tableName"), params);
                }
            }
        }
    }

    /**
     * load one cache by config.
     *
     * @param params cache config. the correction is checked before function
     * @return loaded cache object or null
     */
    private Object initCacheByParameter(Map<String, String> params) {
        CacheReloadAutoAdapterForRdb reloadAdapter;
        reloadAdapter = new CacheReloadAutoAdapterForRdb();
        reloadAdapter.setRdbStorage(ibatisRdbStorage);
        reloadAdapter.setKeyFieldName(
                params.get("objectFieldNameOfColumn"));
        reloadAdapter.setQueryAllSqlName(
                params.get("queryAllSqlName"));
        reloadAdapter.setQueryByKeyListSqlName(
                params.get("queryByKeyListSqlName"));
        reloadAdapter.setQueryByModifyTimeSqlName(
                params.get("queryByModifyTimeSqlName"));

        LocalReadonlyKvMemoryCacheForDS cache;
        cache = new LocalReadonlyKvMemoryCacheForDS();
        cache.setVersionNamespace(params.get("namespace"));
        cache.setVersionQueryService(versionQueryService);
        cache.setStorageForConsistent(reloadAdapter);

        ReadonlyKvCacheAgent cacheAgent = new ReadonlyKvCacheAgent();
        cacheAgent.setImplement(cache);

        return cacheAgent;
    }

    /**
     * check the correctness of configs.
     *
     * @param params configs
     * @return true if passed
     */
    private Boolean checkParameters(Map<String, String> params) {
        String[] keys = new String[] {
                "tableName",
                "columnName",
                "objectFieldNameOfColumn",
                "namespace",
                "queryByKeyListSqlName",
                "queryByModifyTimeSqlName",
                "queryAllSqlName",
                "reloadInterval",
                "reloadAllInterval",
                "firstReloadDelay",
        };
        for (String key : keys) {
            if (StringUtils.isBlank(params.get(key))) {
                return false;
            }
        }
        return true;
    }

    /**
     * task class for reload.
     */
    class RunnableForReloadCache implements Runnable {
        /**
         * who the task serve.
         */
        @Setter
        private ReadonlyKvCache cache;

        /**
         * the config of the served cache.
         */
        @Setter
        private Map<String, String> configInfo;

        /**
         * how long to sleep(to wait next task).
         */
        private final Long sleepUnit = 10L;

        /**
         * thread function.
         */
        @Override
        public void run() {
            Long firstReloadDelay;
            Long reloadInterval;
            Long reloadAllInterval;
            Long nextReloadTime;
            Long nextReloadAllTime;

            firstReloadDelay = Long.parseLong(
                    configInfo.get("firstReloadDelay"));
            reloadInterval = Long.parseLong(
                    configInfo.get("reloadInterval"));
            reloadAllInterval = Long.parseLong(
                    configInfo.get("reloadAllInterval"));
            nextReloadTime = System.currentTimeMillis() + firstReloadDelay;
            nextReloadAllTime = System.currentTimeMillis() + firstReloadDelay;
            while (true) {
                if (cache.consistency()) {
                    break;
                }

                if (System.currentTimeMillis() > nextReloadTime) {
                    reloadCacheConfig(cache);
                    reloadCache(cache);
                    nextReloadTime = System.currentTimeMillis()
                            + reloadInterval;
                }

                if (System.currentTimeMillis() > nextReloadAllTime) {
                    reloadCacheAll(cache);
                    nextReloadAllTime = System.currentTimeMillis()
                            + reloadAllInterval;
                }

                try {
                    Thread.sleep(sleepUnit);
                } catch (Exception e) {
                    // I don't know what to do now
                }
            }
        }
    }

    /**
     * init reload threads and tasks.
     */
    private void beginReloadProcess() {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(cachedTables.size());
        for (String name : cachedTables.keySet()) {
            ReadonlyKvCache cache;
            cache = caches.get(name);
            Map<String, String> config = cachedTables.get(name);

            RunnableForReloadCache runnable = new RunnableForReloadCache();
            runnable.setCache(cache);
            runnable.setConfigInfo(config);

            executorService.submit(runnable);
        }
    }

    /**
     * re-read dynamic configs from region master's config center.
     *
     * @param cache who for
     */
    public void reloadCacheConfig(ReadonlyKvCache cache) {
        enableCache = kvConfigStorage.query("enableRdbCache", true);

        Boolean checkBeforeRead;
        checkBeforeRead = kvConfigStorage.query(
                "checkChangeBeforeReadRdbCache", false);

        if (cache instanceof LocalReadonlyKvMemoryCacheForDS) {
            ((LocalReadonlyKvMemoryCacheForDS) cache)
                    .setCheckChangeBeforeReadRdbCache(checkBeforeRead);
        } else if (cache instanceof ReadonlyKvCacheAgent) {
            ReadonlyKvCache realCache;
            realCache = ((ReadonlyKvCacheAgent) cache).getImplement();
            if (realCache instanceof LocalReadonlyKvMemoryCacheForDS) {
                ((LocalReadonlyKvMemoryCacheForDS) realCache)
                        .setCheckChangeBeforeReadRdbCache(checkBeforeRead);
            }
        }
    }

    /**
     * reload specified cache.
     *
     * @param cache specified cache
     */
    public void reloadCache(ReadonlyKvCache cache) {
        cache.updateChanges();
    }

    /**
     * reload all data for specified cache.
     *
     * @param cache specified cache
     */
    public void reloadCacheAll(ReadonlyKvCache cache) {
        if (cache instanceof LocalReadonlyKvMemoryCacheForDS) {
            rdbChangeHook.makeFullChange(((LocalReadonlyKvMemoryCacheForDS)
                    cache).getVersionNamespace());
        } else if (cache instanceof ReadonlyKvCacheAgent) {
            ReadonlyKvCache realCache;
            realCache = ((ReadonlyKvCacheAgent) cache).getImplement();
            if (realCache instanceof LocalReadonlyKvMemoryCacheForDS) {
                rdbChangeHook.makeFullChange(((LocalReadonlyKvMemoryCacheForDS)
                        realCache).getVersionNamespace());
            }
        }
        cache.updateChanges();
    }
}
