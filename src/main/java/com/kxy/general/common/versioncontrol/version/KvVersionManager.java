package com.kxy.general.common.versioncontrol.version;

import com.kxy.general.common.storage.KvStorage;
import com.kxy.general.common.versioncontrol.Version;
import com.kxy.general.common.versioncontrol.VersionManager;

import java.util.Map;

/**
 * a version generator who can generate unique and sequence version.
 *
 * Created by xiangyun.kong on 6/14/17.
 */
public class KvVersionManager implements VersionManager {
    /**
     * a consistent storage which will be used for generate consistent number.
     */
    private KvStorage consistentKvStorage;

    /**
     * key used for making consistent number.
     */
    private static final String UUID_KEY = "@__sys_reserved_value_for_uuid__@";

    /**
     * init consistent number.
     */
    private static final Long INIT_VALUE = 1L;

    /**
     * @see VersionManager#getIncrementedVersion(String)
     */
    @Override
    public Version getIncrementedVersion(String namespace) {
        synchronized (this) {
            updateVersion(namespace + "." + UUID_KEY);
            Long version = consistentKvStorage.query(
                    namespace + "." + UUID_KEY);
            return new SimpleVersion(version);
        }
    }

    /**
     * increase consistent number.
     *
     * @param key version storing key
     */
    private void updateVersion(String key) {
        consistentKvStorage.write(key, INIT_VALUE,
                (oldValues, newValues) -> {
                    for (Map.Entry<String, Long> entry : oldValues.entrySet()) {
                        entry.setValue(entry.getValue() + 1L);
                    }
                    return oldValues;
                });

    }

    /**
     * set field.
     * @param consistentKvStorage storage for generate version
     */
    public void setConsistentKvStorage(KvStorage consistentKvStorage) {
        synchronized (this) {
            this.consistentKvStorage = consistentKvStorage;
        }
    }
}
