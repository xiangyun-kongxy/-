package com.kxy.general.common.storage.rdb.ibatishook;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.kxy.general.common.storage.cache.manager.RdbCacheManager;
import com.kxy.general.common.versioncontrol.changelog.rdb.changegetter.RdbChangeHook;
import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.Map;

/**
 * a hooked {@code SqlMapClientTemplate} who intercept all updates and record
 * the changes.
 *
 * Created by xiangyun.kong on 6/13/17.
 */
public class CachedSqlMapClientTemplate extends SqlMapClientTemplate {

    /**
     * a change maker and sql executing event hook point handler.
     * it can detect what changed by the sql and then remember it.
     */
    @Setter
    private RdbChangeHook changeHook;

    /**
     * a database cache manager within cache configs.
     * it is used for checking switch if cache is enabled. hook doesn't need to
     * do if cache is disabled.
     */
    @Setter
    private RdbCacheManager rdbCacheManager;

    /**
     * @see SqlMapClientTemplate#insert(String)
     */
    @Override
    public Object insert(String statementName) throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, null);

            Object object = super.insert(statementName);

            changeHook.afterExecuteSql(context);
            return object;
        } else {
            return super.insert(statementName);
        }
    }

    /**
     * @see SqlMapClientTemplate#insert(String, Object)
     */
    @Override
    public Object insert(String statementName, Object parameterObject)
            throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, parameterObject);

            Object object = super.insert(statementName, parameterObject);

            changeHook.afterExecuteSql(context);
            return object;
        } else {
            return super.insert(statementName, parameterObject);
        }
    }

    /**
     * @see SqlMapClientTemplate#update(String)
     */
    @Override
    public int update(String statementName) throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, null);

            int object = super.update(statementName);

            changeHook.afterExecuteSql(context);
            return object;
        } else {
            return super.update(statementName);
        }
    }

    /**
     * @see SqlMapClientTemplate#update(String, Object)
     */
    @Override
    public int update(String statementName, Object parameterObject)
            throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, parameterObject);

            int object = super.update(statementName, parameterObject);

            changeHook.afterExecuteSql(context);
            return object;
        } else {
            return super.update(statementName, parameterObject);
        }
    }

    /**
     * @see SqlMapClientTemplate#update(String, Object, int)
     */
    @Override
    public void update(String statementName, Object parameterObject,
                       int requiredRowsAffected) throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, parameterObject);

            super.update(statementName, parameterObject, requiredRowsAffected);

            changeHook.afterExecuteSql(context);
        } else {
            super.update(statementName, parameterObject, requiredRowsAffected);
        }
    }

    /**
     * @see SqlMapClientTemplate#delete(String)
     */
    @Override
    public int delete(String statementName) throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, null);

            int object = super.delete(statementName);

            changeHook.afterExecuteSql(context);
            return object;
        } else {
            return super.delete(statementName);
        }
    }

    /**
     * @see SqlMapClientTemplate#delete(String, Object)
     */
    @Override
    public int delete(String statementName, Object parameterObject)
            throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, parameterObject);

            int object = super.delete(statementName, parameterObject);

            changeHook.afterExecuteSql(context);
            return object;
        } else {
            return super.delete(statementName, parameterObject);
        }
    }

    /**
     * @see SqlMapClientTemplate#delete(String, Object, int)
     */
    @Override
    public void delete(String statementName, Object parameterObject,
                       int requiredRowsAffected) throws DataAccessException {
        if (rdbCacheManager.isCacheEnabled()) {
            SqlMapExecutorDelegate delegate;
            delegate = ((SqlMapClientImpl) getSqlMapClient()).getDelegate();
            Map<String, String> context = changeHook.beforeExecuteSql(
                    delegate, statementName, parameterObject);

            super.delete(statementName, parameterObject, requiredRowsAffected);

            changeHook.afterExecuteSql(context);
        } else {
            super.delete(statementName, parameterObject, requiredRowsAffected);
        }
    }
}
