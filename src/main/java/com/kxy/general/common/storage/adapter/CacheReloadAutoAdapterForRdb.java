package com.kxy.general.common.storage.adapter;

import com.kxy.general.common.storage.RdbStorage;
import com.kxy.general.common.storage.cache.CacheReloadAdapter;
import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.changelog.rdb.RowChangeLog;
import com.kxy.general.common.versioncontrol.changelog.rdb.TimeChangeLog;
import lombok.Setter;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A general implement for adapting data between database and cache.
 * The database should be read by sqlmap of ibatis and should have implemented
 * three query sqlmap: "query all items by page", "query items by id list",
 * "query items since a modify time"
 * <p>
 * Created by xiangyun.kong on 6/21/17.
 */
public class CacheReloadAutoAdapterForRdb implements CacheReloadAdapter {

    /**
     * read data from database by {@link RdbStorage}. it should be a general
     * DAO implement
     */
    @Setter
    private RdbStorage rdbStorage;

    /**
     * the id column in database(any unique column) used by cache.
     */
    @Setter
    private String keyFieldName;

    /**
     * sqlmap name of querying items by id list.
     *
     * @see CacheReloadAutoAdapterForRdb
     */
    @Setter
    private String queryByKeyListSqlName;

    /**
     * sqlmap name of querying items by a start time.
     *
     * @see CacheReloadAutoAdapterForRdb
     */
    @Setter
    private String queryByModifyTimeSqlName;

    /**
     * sqlmap name of querying all items.
     *
     * @see CacheReloadAutoAdapterForRdb
     */
    @Setter
    private String queryAllSqlName;


    /**
     * default page size when query all items.
     */
    private static final Integer QUERY_PAGE_SIZE = 1000;

    /**
     * @see CacheReloadAdapter
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Serializable> Map<String, T> query(
            List<ChangeLog> changeLogs) {
        Map<String, T> objectMap = new HashMap<>();

        if (changeLogs != null) {
            if (isChangeAll(changeLogs)) {
                List objectDOs;

                Integer start = 0;
                do {
                    Map<String, Object> param = new HashMap<>();
                    param.put("size", QUERY_PAGE_SIZE);
                    param.put("start", start);
                    start += QUERY_PAGE_SIZE;

                    objectDOs = rdbStorage.queryForList(queryAllSqlName, param);

                    objectMap.putAll(listToMap(objectDOs));
                } while (objectDOs != null && objectDOs.size() > 0);
            } else {
                for (ChangeLog changeLog : changeLogs) {
                    if (changeLog instanceof RowChangeLog) {
                        List objectDOs = rdbStorage.queryForList(
                                queryByKeyListSqlName,
                                changeLog.getChangedObjectIds());
                        objectMap.putAll(listToMap(objectDOs));
                    } else if (changeLog instanceof TimeChangeLog) {
                        List objectDOs = rdbStorage.queryForList(
                                queryByModifyTimeSqlName,
                                changeLog.getChangedObjectIds().get(0));
                        objectMap.putAll(listToMap(objectDOs));
                    }
                }
            }
        }

        return objectMap;
    }

    /**
     * convert list to map.
     * the key of item is configured column.
     *
     * @param objectDOs list
     * @param <T>       object type
     * @return map
     */
    private <T> Map<String, T> listToMap(List<T> objectDOs) {
        Map<String, T> result = new HashMap<>();
        if (objectDOs != null) {
            for (T object : objectDOs) {
                result.put(getFieldValue(object, keyFieldName), object);
            }
        }
        return result;
    }

    /**
     * check if changeLogs contains rule: 'change all'(reload all).
     *
     * @param changeLogs change logs
     * @return true if contains change all
     */
    private Boolean isChangeAll(List<ChangeLog> changeLogs) {
        for (ChangeLog changeLog : changeLogs) {
            if (changeLog.isAllChanged()) {
                return true;
            }
        }
        return false;
    }

    /**
     * get field value by field name.
     *
     * @param object    object to get attribute
     * @param fieldName field name
     * @return field value
     */
    private String getFieldValue(Object object, String fieldName) {
        Field field = ReflectionUtils.findField(object.getClass(), fieldName);
        if (field != null) {
            return String.valueOf(ReflectionUtils.getField(field, object));
        }
        return "";
    }
}
