package com.kxy.general.common.storage.cache;

import com.kxy.general.common.versioncontrol.ChangeLog;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * data interface adapter between cache and other storage.
 *
 * Created by xiangyun.kong on 6/19/17.
 */
public interface CacheReloadAdapter {
    /**
     * query all values by change log. the actual change log structure is
     * implementation dependence.
     *
     * @param changeLogs change log for distinguishing values changed
     * @param <T> value type
     * @return special values related with change log
     */
    <T extends Serializable> Map<String, T> query(List<ChangeLog> changeLogs);
}
