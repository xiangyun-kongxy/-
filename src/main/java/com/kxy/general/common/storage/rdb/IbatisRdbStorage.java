package com.kxy.general.common.storage.rdb;

import com.kxy.general.common.storage.RdbStorage;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * a general sqlmap executor.
 *
 * Created by xiangyun.kong on 6/13/17.
 */
public class IbatisRdbStorage extends SqlMapClientDaoSupport
        implements RdbStorage {
    /**
     * @see RdbStorage#insert(String, Object)
     */
    @Override
    public Object insert(String key, Object param) {
        return getSqlMapClientTemplate().insert(key, param);
    }

    /**
     * @see RdbStorage#update(String, Object)
     */
    @Override
    public Integer update(String key, Object param) {
        return getSqlMapClientTemplate().update(key, param);
    }

    /**
     * @see RdbStorage#delete(String, Object)
     */
    @Override
    public Integer delete(String key, Object param) {
        return getSqlMapClientTemplate().delete(key, param);
    }

    /**
     * database is consistent.
     * @return true
     */
    @Override
    public Boolean consistency() {
        // rdb is implemented with consistent
        return true;
    }

    /**
     * @see RdbStorage#query(String, Object)
     */
    @Override
    public Object query(String key, Object param) {
        return getSqlMapClientTemplate().queryForObject(key, param);
    }

    /**
     * @see RdbStorage#queryForList(String, Object)
     */
    @Override
    public List queryForList(String key, Object param) {
        return getSqlMapClientTemplate().queryForList(key, param);
    }
}
