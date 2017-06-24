package com.kxy.general.common.versioncontrol;

import java.io.Serializable;
import java.util.List;

/**
 * an object to mark changes.
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface ChangeLog extends Serializable {

    /**
     * get version which made this change.
     *
     * @return version
     */
    Version getVersion();

    /**
     * get change object's id.
     *
     * @return changed ids
     */
    List<String> getChangedObjectIds();

    /**
     * when the change is expired(will be dropped).
     *
     * @return expire time
     */
    Long expireTime();

    /**
     * if it is all changed.
     *
     * @return if it is all changed
     */
    Boolean isAllChanged();
}
