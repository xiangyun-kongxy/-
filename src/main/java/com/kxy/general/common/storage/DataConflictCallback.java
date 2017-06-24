package com.kxy.general.common.storage;

import java.io.Serializable;
import java.util.Map;

/**
 * callback used when update value which existed already.
 * @param <T> item type
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface DataConflictCallback<T extends Serializable> {
    /**
     * when data update with conflict, give a right one.
     *
     * @param oldValues original value
     * @param newValues the new given value
     * @return the right value(this value will be bind with the key at last)
     */
    Map<String, T> callback(Map<String, T> oldValues, Map<String, T> newValues);
}
