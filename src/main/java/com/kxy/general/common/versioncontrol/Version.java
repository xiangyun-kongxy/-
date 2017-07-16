package com.kxy.general.common.versioncontrol;

import java.io.Serializable;

/**
 * version.
 * Created by xiangyun.kong on 6/9/17.
 */
public interface Version extends Serializable {
    /**
     * 1: this > version<br/>
     * 0: this == version<br/>
     * -1: this < version.<br/>
     *
     * @param version version to compare
     * @return 1, 0, -1
     */
    int compareTo(Version version);
}
