package com.kxy.general.common.versioncontrol.changelog.rdb;

import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.Version;

import java.util.List;

/**
 * marker for a list of single object changed event.
 *
 * Created by xiangyun.kong on 6/12/17.
 */
public class RowChangeLog implements ChangeLog {
    private static final long serialVersionUID = 5680113111251837993L;

    /**
     * mark all objects are changed.
     */
    public static final String ALL_CHANGED =
            "@__sys_reserved_for_version_all_changed__@";

    /**
     * version of the change token.
     */
    private Version version;

    /**
     * changed object list.
     */
    private List<String> changedIds;

    /**
     * when should the change-log be expired.
     */
    private Long expireTime;

    /**
     * construct with all parameters.
     *
     * @param version version
     * @param changedIds changed objects
     * @param expireTime the expire time of the creating change-log
     */
    public RowChangeLog(Version version, List<String> changedIds,
                        Long expireTime) {
        this.version = version;
        this.changedIds = changedIds;
        this.expireTime = expireTime;
    }

    /**
     * @see ChangeLog#getVersion()
     */
    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * @see ChangeLog#getChangedObjectIds()
     */
    @Override
    public List<String> getChangedObjectIds() {
        return changedIds;
    }

    /**
     * @see ChangeLog#expireTime()
     */
    @Override
    public Long expireTime() {
        return expireTime;
    }

    /**
     * @see ChangeLog#isAllChanged()
     */
    @Override
    public Boolean isAllChanged() {
        for (String id : changedIds) {
            if (ALL_CHANGED.equals(id)) {
                return true;
            }
        }
        return false;
    }

}
