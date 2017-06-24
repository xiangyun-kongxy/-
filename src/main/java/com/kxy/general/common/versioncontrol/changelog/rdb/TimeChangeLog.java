package com.kxy.general.common.versioncontrol.changelog.rdb;

import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.Version;

import java.util.Collections;
import java.util.List;

/**
 * marker for the time the change token place.
 *
 * Created by xiangyun.kong on 6/19/17.
 */
public class TimeChangeLog implements ChangeLog {
    private static final long serialVersionUID = -6903433951631831397L;

    /**
     * mark all objects are changed.
     */
    private static final String ALL_CHANGED =
            "@__sys_reserved_for_version_all_changed__@";

    /**
     * version of the change token.
     */
    private Version version;

    /**
     * the time the change token place.
     */
    private String changeTime;

    /**
     * when should the change-log be expired.
     */
    private Long expireTime;

    /**
     * construct with all parameters.
     *
     * @param version version
     * @param changeTime the time the change token place
     * @param expireTime the expire time of the creating change-log
     */
    public TimeChangeLog(Version version, String changeTime, Long expireTime) {
        this.version = version;
        this.changeTime = changeTime;
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
        return Collections.singletonList(changeTime);
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
        return ALL_CHANGED.equals(changeTime);
    }

}
