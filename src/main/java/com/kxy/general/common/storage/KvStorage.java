package com.kxy.general.common.storage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * storage with kv structure.
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface KvStorage extends ReadonlyKvStorage {
    /**
     * add a new value or update an exist value. the existence will be override.
     *
     * @param key key of value
     * @param value value to add
     * @param <T> value type
     */
    <T extends Serializable> void write(String key, T value);

    /**
     * add a new value or update an exist value. if value exists, callback will
     * be called.
     *
     * @param key key of value
     * @param value value to add
     * @param callback if value exists, callback will be used
     * @param <T> value type
     */
    <T extends Serializable> void write(String key, T value,
                                        DataConflictCallback<T> callback);

    /**
     * add a new value or update an exist value. the existence will be override.
     *
     * @param values value to add
     * @param <T> value type
     */
    <T extends Serializable> void write(Map<String, T> values);

    /**
     * add a new value or update an exist value. if value exists, callback will
     * be called.
     *
     * @param values value to add
     * @param callback if value exists, callback will be used
     * @param <T> value type
     */
    <T extends Serializable> void write(Map<String, T> values,
                                        DataConflictCallback<T> callback);

    /**
     * remove by key.
     *
     * @param key key to remove
     */
    void remove(String key);

    /**
     * remove by keys.
     *
     * @param keys keys to remove
     */
    void remove(List<String> keys);

    /**
     * remove all values.
     */
    void removeAll();
}
