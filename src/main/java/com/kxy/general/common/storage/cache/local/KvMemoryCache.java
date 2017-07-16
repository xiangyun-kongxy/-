package com.kxy.general.common.storage.cache.local;

import com.kxy.general.common.storage.DataConflictCallback;
import com.kxy.general.common.storage.DataMissCallback;
import com.kxy.general.common.storage.cache.KvCache;
import com.kxy.general.common.versioncontrol.Version;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * a local only memory cache implement for kv storage.
 *
 * Created by xiangyun.kong on 6/13/17.
 */
public class KvMemoryCache implements KvCache {
    /**
     * memory kv storage.
     */
    private Map<String, Serializable> storage = new HashMap<>();

    /**
     * local only memory hasn't get a conflict write.
     * @return true
     */
    @Override
    public Boolean consistency() {
        // local memory cache with one copy, on consistency issues.
        return true;
    }

    /**
     * @see KvCache#write(String, Serializable)
     */
    @Override
    public <T extends Serializable> void write(String key, T value) {
        storage.put(key, value);
    }

    /**
     * @see KvCache#write(String, Serializable, DataConflictCallback)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> void write(String key, T value,
            DataConflictCallback<T> callback) {
        Serializable oldValue = storage.get(key);
        if (oldValue == null) {
            storage.put(key, value);
        } else if (!oldValue.equals(value)) {
            Map<String, T> oldValues = new HashMap<>();
            Map<String, T> newValues = new HashMap<>();
            Class tClass = value.getClass();
            if (tClass.isInstance(oldValue)) {
                oldValues.put(key, (T) oldValue);
            }
            newValues.put(key, value);

            Map<String, T> values = callback.callback(oldValues, newValues);
            if (values != null) {
                for (Map.Entry<String, T> entry : values.entrySet()) {
                    storage.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * @see KvCache#write(Map)
     */
    @Override
    public <T extends Serializable> void write(Map<String, T> values) {
        if (values != null) {
            for (Map.Entry<String, T> entry : values.entrySet()) {
                storage.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @see KvCache#write(Map, DataConflictCallback)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> void write(Map<String, T> values,
            DataConflictCallback<T> callback) {
        Map<String, T> conflictOldValues = new HashMap<>();
        Map<String, T> conflictNewValues = new HashMap<>();

        if (values != null) {
            for (Map.Entry<String, T> entry : values.entrySet()) {
                Serializable oldValue = storage.get(entry.getKey());
                if (oldValue == null) {
                    storage.put(entry.getKey(), entry.getValue());
                } else {
                    Class tClass = entry.getValue().getClass();
                    if (tClass.isInstance(oldValue)) {
                        conflictOldValues.put(entry.getKey(), (T) oldValue);
                    }
                    conflictNewValues.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (conflictOldValues.size() > 0 || conflictNewValues.size() > 0) {
            Map<String, T> newValues = callback.callback(conflictOldValues,
                    conflictNewValues);
            if (newValues != null) {
                for (Map.Entry<String, T> entry : newValues.entrySet()) {
                    storage.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * @see KvCache#remove(String)
     */
    @Override
    public void remove(String key) {
        storage.remove(key);
    }

    /**
     * @see KvCache#remove(List)
     */
    @Override
    public void remove(List<String> keys) {
        for (String key : keys) {
            storage.remove(key);
        }
    }

    /**
     * @see KvCache#removeAll()
     */
    @Override
    public void removeAll() {
        storage.clear();
    }

    /**
     * consistent cache doesn't need a version.
     * @return null
     */
    @Override
    public Version getVersion() {
        // consistency cache don't need a version
        return null;
    }

    /**
     * consistent cache doesn't need to update changes.
     */
    @Override
    public void updateChanges() {

    }

    /**
     * @see KvCache#query(String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T query(String key) {
        return (T) storage.get(key);
    }

    /**
     * @see KvCache#query(String, Serializable)
     */
    @Override
    public <T extends Serializable> T query(String key, T miss) {
        T result = query(key);
        if (result == null) {
            result = miss;
        }
        return result;
    }

    /**
     * @see KvCache#query(List)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys) {
        Map<String, T> result = new HashMap<>();
        for (String key : keys) {
            T value = query(key);
            if (value != null) {
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * @see KvCache#query(List, DataMissCallback)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys,
            DataMissCallback<T> callback) {
        Map<String, T> result = new HashMap<>();

        List<String> missedKeys = new ArrayList<>();
        for (String key : keys) {
            T value = query(key);
            if (value != null) {
                result.put(key, value);
            } else {
                missedKeys.add(key);
            }
        }

        if (missedKeys.size() > 0) {
            Map<String, T> values = callback.callback(missedKeys);
            if (values != null) {
                result.putAll(values);
            }
        }
        return result;
    }

    /**
     * @see KvCache#queryAll()
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> Map<String, T> queryAll() {
        return new HashMap<>((Map<String, T>) storage);
    }
}
