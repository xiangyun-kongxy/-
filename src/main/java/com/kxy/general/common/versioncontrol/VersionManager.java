package com.kxy.general.common.versioncontrol;

/**
 * a type who can makes unique and sequence version.
 * Created by xiangyun.kong on 6/13/17.
 */
public interface VersionManager {
    /**
     * generate uu-version in namespace.
     *
     * @param namespace scope of unique.
     * @return uu-version
     */
    Version getIncrementedVersion(String namespace);
}
