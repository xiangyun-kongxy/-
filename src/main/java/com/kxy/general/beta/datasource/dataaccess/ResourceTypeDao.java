package com.kxy.general.beta.datasource.dataaccess;

import com.kxy.general.beta.datasource.dataobject.ResourceTypeDo;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public interface ResourceTypeDao {
    /**
     * get ResourceTypeDo by name.
     * @param name type name
     * @return loaded ResourceTypeDo
     */
    ResourceTypeDo getResourceTypeByName(String name);

}
