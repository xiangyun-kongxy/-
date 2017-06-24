package com.kxy.general.common.versioncontrol;

import java.util.List;

/**
 * version service for querying.
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface VersionQueryService {
    /**
     * get change logs which made by version.
     *
     * @param namespace namespace
     * @param version version to query
     * @return change logs
     */
    List<ChangeLog> queryChangeLogByVersion(String namespace, Version version);

    /**
     * get all change logs since given version.
     *
     * @param namespace namespace
     * @param version begin version to query
     * @return all change logs
     */
    List<ChangeLog> queryChangeLogSince(String namespace, Version version);

    /**
     * get all versions since given version.
     *
     * @param namespace namespace
     * @param begin begin version
     * @return versions
     */
    List<Version> queryVersionSince(String namespace, Version begin);

    /**
     * get the newest version.
     *
     * @param namespace namespace
     * @return the newest version
     */
    Version queryCurrentVersion(String namespace);
}
