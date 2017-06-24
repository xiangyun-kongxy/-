package com.kxy.general.common.storage;

import java.util.List;

/**
 * a readonly interface for database.
 * sql is sqlmap compatible.
 *
 * Created by xiangyun.kong on 6/12/17.
 */
public interface ReadonlyRdbStorage extends Storage {

    /**
     * query object by sql. use ibatis
     *
     * @param key sqlmap key
     * @param param parameter for sqlmap
     * @return query result
     */
    Object query(String key, Object param);

    /**
     * query objects by sql. use ibatis
     *
     * @param key sqlmap key
     * @param param parameter for sqlmap
     * @return query result
     */
    List queryForList(String key, Object param);
}
