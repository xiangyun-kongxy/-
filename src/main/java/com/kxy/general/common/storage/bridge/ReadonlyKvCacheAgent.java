package com.kxy.general.common.storage.bridge;

import com.kxy.general.common.storage.DataMissCallback;
import com.kxy.general.common.storage.cache.ReadonlyKvCache;
import com.kxy.general.common.versioncontrol.Version;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * an agent type of kv cache, who makes the storage thread safe.
 *
 * Created by xiangyun.kong on 6/23/17.
 */
public class ReadonlyKvCacheAgent implements ReadonlyKvCache {

    /**
     * the real cache.
     */
    @Setter
    @Getter
    private ReadonlyKvCache implement;

    /**
     * lock to make it sync.
     * we can not use read write lock, as cache may update data in querying.
     */
    private ReentrantLock lock = new ReentrantLock(true);

    /**
     * @see ReadonlyKvCache#consistency()
     */
    @Override
    public Boolean consistency() {
        try {
            lock.lock();
            return implement.consistency();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#getVersion()
     */
    @Override
    public Version getVersion() {
        try {
            lock.lock();
            return implement.getVersion();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#updateChanges()
     */
    @Override
    public void updateChanges() {
        try {
            lock.lock();
            implement.updateChanges();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#query(String)
     */
    @Override
    public <T extends Serializable> T query(String key) {
        try {
            lock.lock();
            return implement.query(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#query(String, Serializable)
     */
    @Override
    public <T extends Serializable> T query(String key, T miss) {
        try {
            lock.lock();
            return implement.query(key, miss);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#query(List)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys) {
        try {
            lock.lock();
            return implement.query(keys);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#query(List, DataMissCallback)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys,
            DataMissCallback<T> callback) {
        try {
            lock.lock();
            return implement.query(keys, callback);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @see ReadonlyKvCache#queryAll()
     */
    @Override
    public <T extends Serializable> Map<String, T> queryAll() {
        try {
            lock.lock();
            return implement.queryAll();
        } finally {
            lock.unlock();
        }
    }
}
