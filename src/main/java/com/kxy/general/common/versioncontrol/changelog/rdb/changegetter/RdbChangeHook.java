package com.kxy.general.common.versioncontrol.changelog.rdb.changegetter;

import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.kxy.general.common.storage.KvStorage;
import com.kxy.general.common.storage.RdbStorage;
import com.kxy.general.common.storage.cache.manager.RdbCacheManager;
import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.Version;
import com.kxy.general.common.versioncontrol.VersionManager;
import com.kxy.general.common.versioncontrol.changelog.rdb.RowChangeLog;
import com.kxy.general.common.versioncontrol.changelog.rdb.TimeChangeLog;
import lombok.Setter;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a change-log maker and also a {@code SqlMapClientTemplate} intercept handler.
 * it can analysis what is to be changed after a sql and remember it by change-
 * log.
 * <p>
 * Created by xiangyun.kong on 6/12/17.
 */
public class RdbChangeHook {

    /**
     * a storage who can execute sqlmap.
     */
    @Setter
    private RdbStorage rdbStorage;

    /**
     * a version manager who generates sequence, unique version.
     */
    @Setter
    private VersionManager versionManager;

    /**
     * a storage where to save the change-log.
     */
    @Setter
    private KvStorage changeLogStorage;

    /**
     * cache(config) manager.
     * I can get information by table name from the manager.
     */
    @Setter
    private RdbCacheManager rdbCacheManager;

    /**
     * every thing has life. so a change-log has.
     * when I create a change-log, it should die after changeLogExpireInterval
     * millisecond.
     */
    @Setter
    private Long changeLogExpireInterval;

    /**
     * space pattern.
     */
    private static final String PT_SPACE = "\\p{Space}*";

    /**
     * sql pattern for update, delete, insert.
     */
    private static final String SQL_PATTERN =
            // update
            PT_SPACE + "update" + PT_SPACE
                    + "(\\p{javaJavaIdentifierPart}*).*"
                    + "where(.*)|"
                    // delete
                    + PT_SPACE + "delete" + PT_SPACE
                    + "from" + PT_SPACE + "(.*)" + PT_SPACE
                    + "where(.*)|"
                    // insert
                    + PT_SPACE + "insert" + PT_SPACE
                    + "into" + PT_SPACE + "(\\p{javaJavaIdentifierPart}*).*";

    /**
     * regex group index for table name in update.
     */
    private static final Integer POS_UPDATE_TABLE_NAME = 1;

    /**
     * regex group index for condition in update.
     */
    private static final Integer POS_UPDATE_CONDITION = 2;

    /**
     * regex group index for table name in delete.
     */
    private static final Integer POS_DELETE_TABLE_NAME = 3;

    /**
     * regex group index for condition in delete.
     */
    private static final Integer POS_DELETE_CONDITION = 4;

    /**
     * regex group index for table name in insert.
     */
    private static final Integer POS_INSERT_TABLE_NAME = 5;

    /**
     * create a 'all had changed' change-log. for reload all cached data.
     *
     * @param namespace cache namespace(is configured by user)
     */
    public void makeFullChange(String namespace) {
        Version version = versionManager.getIncrementedVersion(namespace);
        ChangeLog changeLog = new RowChangeLog(version,
                Collections.singletonList(RowChangeLog.ALL_CHANGED),
                System.currentTimeMillis() + changeLogExpireInterval);

        recordChangeLog(namespace, changeLog);
    }

    /**
     * prepare information before execute sql.
     * time is remembered (for time change-log. a time after executing is
     * inconsistent).
     *
     * @param delegate executor binded delegate
     * @param sqlName  sql name in sqlmap(include namespace)
     * @param param    parameters for the sql
     * @return context for {@code afterExecuteSql}
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> beforeExecuteSql(SqlMapExecutorDelegate delegate,
                                                String sqlName, Object param) {
        String changeSql = getSql(delegate, sqlName, param);
        Map<String, String> paramForQueryChange = makeParamForQueryChange(
                changeSql);
        if (paramForQueryChange != null
                   && "insert".equals(paramForQueryChange.get("type"))) {
            List<String> changes;
            changes = (List<String>) rdbStorage.queryForList(
                    "ChangeLog.getChanges", paramForQueryChange);
            if (changes != null && changes.size() > 0) {
                paramForQueryChange.put("startTime", changes.get(0));
            } else {
                paramForQueryChange.put("startTime",
                        DateFormatUtils.format(new Date(),
                                "yyy-MM-dd HH:mm:ss"));
            }
        }
        return paramForQueryChange;
    }

    /**
     * handler after executing sql.
     * create change-log and save.
     *
     * @param context generated by {@code beforeExecuteSql}
     */
    @SuppressWarnings("unchecked")
    public void afterExecuteSql(Map<String, String> context) {
        if (context != null) {
            ChangeLog changeLog = null;
            String namespace = getNamespaceByTable(context.get("tableName"));
            Version version = versionManager.getIncrementedVersion(namespace);

            if (context.get("type").equals("insert")) {
                changeLog = new TimeChangeLog(version, context.get("startTime"),
                        System.currentTimeMillis()
                                + changeLogExpireInterval);
            } else {
                List<String> changes;
                changes = rdbStorage.queryForList("ChangeLog.getChanges",
                        context);
                if (changes != null && changes.size() > 0) {
                    changeLog = new RowChangeLog(version, changes,
                            System.currentTimeMillis()
                                    + changeLogExpireInterval);
                }
            }
            if (changeLog != null) {
                recordChangeLog(namespace, changeLog);
            }
        }
    }

    /**
     * save change-log in configured storage.
     *
     * @param namespace namespace to save change-log
     * @param changeLog change-log to be saved
     */
    private void recordChangeLog(String namespace, ChangeLog changeLog) {
        if (changeLog.isAllChanged()) {
            List<ChangeLog> changeLogs = new ArrayList<>();
            changeLogs.add(changeLog);
            changeLogStorage.write(namespace, (Serializable) changeLogs);
        } else {
            List<ChangeLog> changeLogs = changeLogStorage.query(namespace);
            if (changeLogs == null) {
                changeLogs = new ArrayList<>();
            }
            changeLogs.removeIf(history -> System.currentTimeMillis()
                                                   >= history.expireTime());

            changeLogs.add(changeLog);
            changeLogStorage.write(namespace, (Serializable) changeLogs);
        }
    }

    /**
     * create parameters for querying what changed by the original sql.
     *
     * @param srcSql the original sql
     * @return the parameter map
     */
    private Map<String, String> makeParamForQueryChange(String srcSql) {
        return parseSqlKeyInfo(srcSql);
    }

    /**
     * parse sql and get information we need.
     * * table name
     * * column name
     * * sql condition
     * * sql type
     *
     * @param sql original sql to be executed
     * @return the information map
     */
    private Map<String, String> parseSqlKeyInfo(String sql) {
        Pattern pattern = Pattern.compile(SQL_PATTERN,
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String tableName;
            String condition;
            if (matcher.group(POS_UPDATE_TABLE_NAME) != null) {
                tableName = matcher.group(POS_UPDATE_TABLE_NAME);
                condition = matcher.group(POS_UPDATE_CONDITION);

                Map<String, String> result = new HashMap<>();
                result.put("tableName", tableName);
                result.put("columnName", getColumnByTable(tableName));
                result.put("condition", condition);
                result.put("type", "update");

                return result;
            } else if (matcher.group(POS_DELETE_TABLE_NAME) != null) {
                tableName = matcher.group(POS_DELETE_TABLE_NAME);
                condition = matcher.group(POS_DELETE_CONDITION);

                Map<String, String> result = new HashMap<>();
                result.put("tableName", tableName);
                result.put("columnName", getColumnByTable(tableName));
                result.put("condition", condition);
                result.put("type", "delete");

                return result;
            } else if (matcher.group(POS_INSERT_TABLE_NAME) != null) {
                tableName = matcher.group(POS_INSERT_TABLE_NAME);

                // we can not get id before sql execute, try to record change
                // time
                Map<String, String> result = new HashMap<>();
                result.put("tableName", tableName);
                result.put("columnName", "now()");
                result.put("condition", "1=1 limit 1");
                result.put("type", "insert");

                return result;
            }
        }
        return null;
    }

    /**
     * only match requirement for this class use.
     * it is not the sql real executed.
     *
     * @param delegate sqlmap client delegate
     * @param sqlName  statement name
     * @param param    parameters for sql
     * @return sql
     */
    private String getSql(SqlMapExecutorDelegate delegate, String sqlName,
                          Object param) {
        MappedStatement statement = delegate.getMappedStatement(sqlName);

        Sql sql = statement.getSql();
        SessionScope sessionScope = new SessionScope();
        sessionScope.incrementRequestStackDepth();
        StatementScope statementScope = new StatementScope(sessionScope);
        statement.initRequest(statementScope);

        String sqlString = sql.getSql(statementScope, param);
        ParameterMap parameterMap = sql.getParameterMap(statementScope, param);
        statement.setParameterMap(parameterMap);
        Object[] attributes = parameterMap.getParameterObjectValues(
                statementScope, param);
        for (Object attr : attributes) {
            if (attr != null) {
                sqlString = sqlString.replaceFirst("\\?",
                        "'" + attr.toString() + "'");
            }
        }

        return sqlString;
    }

    /**
     * get column name by table name.
     *
     * @param tableName table name
     * @return column name
     */
    private String getColumnByTable(String tableName) {
        return rdbCacheManager.getKeyColumnByTable(tableName);
    }

    /**
     * get namespace by table name.
     *
     * @param tableName table name
     * @return namespace
     */
    private String getNamespaceByTable(String tableName) {
        return rdbCacheManager.getNamespaceByTable(tableName);
    }
}
