package com.kxy.general.common.storage.dfs;

import com.kxy.general.common.storage.DataConflictCallback;
import com.kxy.general.common.storage.DataMissCallback;
import com.kxy.general.common.storage.KvStorage;
import com.kxy.general.common.storage.cache.KvCache;
import com.kxy.general.common.versioncontrol.Version;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A kv cache implement whose real storage is tair.
 *
 * Created by xiangyun.kong on 6/13/17.
 */
public class TairKvCache implements KvCache {
    /**
     * An implemented tair interface.
     */
    @Setter
    private KvStorage tairService;

    /**
     * @see KvCache#consistency()
     */
    @Override
    public Boolean consistency() {
        return true;
    }

    /**
     * @see KvCache#write(String, Serializable)
     */
    @Override
    public <T extends Serializable> void write(String key, T value) {
        tairService.write(key, value);
    }


    /**
     * @see KvCache#write(String, Serializable, DataConflictCallback)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> void write(String key, T value,
            DataConflictCallback<T> callback) {
        tairService.write(key, value, callback);
    }

    /**
     * @see KvCache#write(Map)
     */
    @Override
    public <T extends Serializable> void write(Map<String, T> values) {
        tairService.write(values);
    }

    /**
     * @see KvCache#write(Map, DataConflictCallback)
     */
    @Override
    public <T extends Serializable> void write(Map<String, T> values,
            DataConflictCallback<T> callback) {
        tairService.write(values);
    }

    /**
     * @see KvCache#remove(String)
     */
    @Override
    public void remove(String key) {
        tairService.remove(key);
    }

    /**
     * @see KvCache#remove(List)
     */
    @Override
    public void remove(List<String> keys) {
        tairService.remove(keys);
    }

    /**
     * @see KvCache#removeAll()
     */
    @Override
    public void removeAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * tair is a consistent storage, no version(for synchronization) needed.
     * @return null
     */
    @Override
    public Version getVersion() {
        return null;
    }

    /**
     * consistent cache does not update changes.
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
        return (T) tairService.query(key);
    }

    /**
     * @see KvCache#query(String, Serializable)
     */
    @Override
    public <T extends Serializable> T query(String key, T miss) {
        T value = query(key);
        if (value == null) {
            value = miss;
        }
        return value;
    }

    /**
     * @see KvCache#query(List)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> Map<String, T> query(List<String> keys) {
        return tairService.query(keys);
    }

    /**
     * @see KvCache#query(List, DataMissCallback)
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys,
            DataMissCallback<T> callback) {
        Map<String, T> result = query(keys);
        if (result == null) {
            result = new HashMap<>();
        }

        List<String> missed = new ArrayList<>();
        for (String key : keys) {
            if (!result.containsKey(key)) {
                missed.add(key);
            }
        }

        if (missed.size() > 0) {
            Map<String, T> missedValues = callback.callback(missed);
            if (missedValues != null) {
                result.putAll(missedValues);
            }
        }

        return result;
    }

    /**
     * @see KvCache#queryAll()
     */
    @Override
    public <T extends Serializable> Map<String, T> queryAll() {
        throw new UnsupportedOperationException();
    }

}
