package com.kxy.general.ß.datasource.dataaccess;

import com.kxy.general.ß.resource.ResourceType;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public interface ResourceTypeDao {
    /**
     *
     * @param typename
     * @return
     */
    ResourceType getResourceTypeByName(String typename);

}
