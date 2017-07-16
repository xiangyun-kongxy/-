package com.kxy.general.common.versioncontrol.version;

import com.kxy.general.common.versioncontrol.Version;

/**
 * a version uses a number.
 * Created by xiangyun.kong on 6/12/17.
 */
public class SimpleVersion implements Version {
    private static final long serialVersionUID = 2958277556556984734L;

    /**
     * version.
     */
    private Long version;

    /**
     * construct with all parameters.
     * @param v version
     */
    public SimpleVersion(Long v) {
        this.version = v;
    }

    /**
     * @see Version#compareTo(Version)
     */
    @Override
    public int compareTo(Version v) {
        if (v == null || !(v instanceof SimpleVersion)
                || this.version > ((SimpleVersion) v).version) {
            return 1;
        } else if (this.version.equals(((SimpleVersion) v).version)) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * get number string.
     */
    @Override
    public String toString() {
        return version.toString();
    }
}
