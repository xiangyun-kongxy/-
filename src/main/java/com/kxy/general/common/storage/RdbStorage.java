package com.kxy.general.common.storage;

/**
 * database storage.
 * sql is sqlmap compatible.
 *
 * Created by xiangyun.kong on 6/12/17.
 */
public interface RdbStorage extends ReadonlyRdbStorage {
    /**
     * insert new record into rdb.
     *
     * @param key sqlmap key
     * @param param parameters for sqlmap
     * @return new object
     */
    Object insert(String key, Object param);

    /**
     * update records.
     *
     * @param key sqlmap key
     * @param param parameters for sqlmap
     * @return effected row count
     */
    Integer update(String key, Object param);

    /**
     * delete records.
     *
     * @param key sqlmap key
     * @param param parameters for sqlmap
     * @return effected row count
     */
    Integer delete(String key, Object param);
}
