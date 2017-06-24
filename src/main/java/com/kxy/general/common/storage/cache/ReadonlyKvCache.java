package com.kxy.general.common.storage.cache;

import com.kxy.general.common.storage.ReadonlyKvStorage;
import com.kxy.general.common.versioncontrol.Version;

/**
 * a readonly cache with kv structure.
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface ReadonlyKvCache extends ReadonlyKvStorage {
    /**
     * get cache current version.
     *
     * @return current version
     */
    Version getVersion();

    /**
     * refresh values by change logs.
     *
     */
    void updateChanges();
}
