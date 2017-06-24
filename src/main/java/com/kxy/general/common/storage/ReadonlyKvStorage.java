package com.kxy.general.common.storage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * storage with read functions. data structure is key-value indexed
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface ReadonlyKvStorage extends Storage {
    /**
     * query value by key.
     *
     * @param key key of value
     * @param <T> value type
     * @return value, null if miss
     */
    <T extends Serializable> T query(String key);

    /**
     * query value by key.
     *
     * @param key key of value
     * @param miss if value miss, this value will return
     * @param <T> value type
     * @return value
     */
    <T extends Serializable> T query(String key, T miss);

    /**
     * query values by keys.
     *
     * @param keys keys of values
     * @param <T> value type
     * @return values, empty HashMap if all values are missed
     */
    <T extends Serializable> Map<String, T> query(List<String> keys);

    /**
     * query values by keys.
     *
     * @param keys keys of values
     * @param callback if values miss, this callback will be called. if callback
     *                 returns value, the value will return
     * @param <T> value type
     * @return values
     */
    <T extends Serializable> Map<String, T> query(List<String> keys,
                                                  DataMissCallback<T> callback);

    /**
     * query all values from the storage.
     *
     * @param <T> value type
     * @return all values
     */
    <T extends Serializable> Map<String, T> queryAll();
}
