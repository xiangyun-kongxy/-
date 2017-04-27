package com.kxy.general.beta.datasource.dataaccess;

import com.kxy.general.beta.datasource.dataobject.ResourceTypeDo;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public interface ResourceTypeDao {
    /**
     * get <code>ResourceTypeDo</code> by name.
     * @param name type name
     * @return loaded <code>ResourceTypeDo</code>
     */
    ResourceTypeDo getResourceTypeByName(String name);

}
