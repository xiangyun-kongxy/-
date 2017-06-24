package com.kxy.general.common.versioncontrol.service.rdb;

import com.kxy.general.common.storage.KvStorage;
import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.Version;
import com.kxy.general.common.versioncontrol.VersionQueryService;
import com.kxy.general.common.versioncontrol.changelog.rdb.RowChangeLog;
import com.kxy.general.common.versioncontrol.changelog.rdb.TimeChangeLog;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * a version service for querying.
 *
 * Created by xiangyun.kong on 6/12/17.
 */
public class KvVersionQueryService implements VersionQueryService {
    /**
     * where the change-log(version) stores.
     */
    @Setter
    private KvStorage changeLogStorage;

    /**
     * @see VersionQueryService#queryChangeLogByVersion(String, Version)
     */
    @Override
    public List<ChangeLog> queryChangeLogByVersion(String namespace,
                                                   Version version) {
        List<ChangeLog> result = new ArrayList<>();

        List<ChangeLog> changeLogs = queryAllChanges(namespace);
        for (ChangeLog changeLog:changeLogs) {
            if (version.compareTo(changeLog.getVersion()) == 0) {
                result.add(changeLog);
            }
        }
        return result;
    }

    /**
     * @see VersionQueryService#queryChangeLogSince(String, Version)
     */
    @Override
    public List<ChangeLog> queryChangeLogSince(String namespace,
                                               Version version) {
        List<ChangeLog> result = new ArrayList<>();

        List<ChangeLog> changeLogs = queryAllChanges(namespace);
        if (changeLogs != null) {
            for (ChangeLog changeLog : changeLogs) {
                if (changeLog.getVersion().compareTo(version) > 0) {
                    result.add(changeLog);
                }
            }
        }
        result = normalize(result);
        return result;
    }

    /**
     * @see VersionQueryService#queryVersionSince(String, Version)
     */
    @Override
    public List<Version> queryVersionSince(String namespace, Version begin) {
        List<Version> result = new ArrayList<>();

        List<ChangeLog> changeLogs = queryAllChanges(namespace);
        for (ChangeLog changeLog:changeLogs) {
            if (changeLog.getVersion().compareTo(begin) > 0) {
                result.add(changeLog.getVersion());
            }
        }
        return result;
    }

    /**
     * @see VersionQueryService#queryCurrentVersion(String)
     */
    @Override
    public Version queryCurrentVersion(String namespace) {
        Version currentVersion = null;

        List<ChangeLog> changeLogs = queryAllChanges(namespace);
        if (changeLogs != null) {
            for (ChangeLog changeLog : changeLogs) {
                if (currentVersion == null
                        || changeLog.getVersion()
                            .compareTo(currentVersion) > 0) {
                    currentVersion = changeLog.getVersion();
                }
            }
        }

        return currentVersion;
    }

    /**
     * get all changes from the storage.
     *
     * @param namespace namespace of the version
     * @return change-logs
     */
    private List<ChangeLog> queryAllChanges(String namespace) {
        return changeLogStorage.query(namespace);
    }

    /**
     * simplify change-logs.
     * varies RowChangeLog can be merged all objects with the max version;
     * varies TimeChangeLog can be merged by earliest time with the max version;
     * an 'all changed' change-log can merge others all with the max version.
     *
     * @param changeLogs change log
     * @return simplified change-logs
     */
    private List<ChangeLog> normalize(List<ChangeLog> changeLogs) {
        List<ChangeLog> rowChangeLogs = new ArrayList<>();
        List<ChangeLog> timeChangeLogs = new ArrayList<>();
        List<ChangeLog> otherChangeLogs = new ArrayList<>();

        for (ChangeLog changeLog : changeLogs) {
            if (changeLog instanceof RowChangeLog) {
                rowChangeLogs.add(changeLog);
            } else if (changeLog instanceof TimeChangeLog) {
                timeChangeLogs.add(changeLog);
            } else {
                otherChangeLogs.add(changeLog);
            }
        }

        if (timeChangeLogs.size() > 0) {
            otherChangeLogs.add(normalizeTime(timeChangeLogs));
        }
        if (rowChangeLogs.size() > 0) {
            otherChangeLogs.add(normalizeRow(rowChangeLogs));
        }

        return otherChangeLogs;
    }

    /**
     * merge TimeChangeLog.
     *
     * @param changeLogs change-log
     * @return merged change-log
     */
    private ChangeLog normalizeTime(List<ChangeLog> changeLogs) {
        Version maxVersion = null;
        String early = null;
        for (ChangeLog changeLog : changeLogs) {
            if (changeLog.getVersion().compareTo(maxVersion) > 0) {
                maxVersion = changeLog.getVersion();
            }
            if (early == null || early.compareTo(
                    changeLog.getChangedObjectIds().get(0)) > 0) {
                early = changeLog.getChangedObjectIds().get(0);
            }
        }
        return new TimeChangeLog(maxVersion, early, System.currentTimeMillis());
    }

    /**
     * merge RowChangeLog.
     *
     * @param changeLogs change-log
     * @return merged change-log
     */
    private ChangeLog normalizeRow(List<ChangeLog> changeLogs) {
        Version maxVersion = null;
        List<String> rows = new ArrayList<>();
        for (ChangeLog changeLog : changeLogs) {
            if (changeLog.getVersion().compareTo(maxVersion) > 0) {
                maxVersion = changeLog.getVersion();
            }
            rows.addAll(changeLog.getChangedObjectIds());
        }
        return new RowChangeLog(maxVersion, rows, System.currentTimeMillis());
    }
}
