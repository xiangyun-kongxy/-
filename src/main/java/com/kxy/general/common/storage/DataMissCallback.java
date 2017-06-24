package com.kxy.general.common.storage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * callback for storage value missing.
 * @param <T> item type
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface DataMissCallback<T extends Serializable> {
    /**
     * when data can't read from storage by key, this callback would be called.
     *
     * @param keys key of missed value
     * @return if data returned, the data will be used in storage(once or long
     *          time, implement depended)
     */
    Map<String, T> callback(List<String> keys);
}
