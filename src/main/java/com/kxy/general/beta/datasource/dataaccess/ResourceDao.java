package com.kxy.general.beta.datasource.dataaccess;

import com.kxy.general.beta.datasource.DataSource;
import com.kxy.general.beta.datasource.dataobject.ResourceDo;

import java.util.List;

/**
 * resource data interface.
 * Created by xiangyunkong on 17/04/2017.
 */
public interface ResourceDao extends DataSource {
    /**
     * load resource by resource id.
     * @param id resource id
     * @return ResourceDo object
     */
    ResourceDo loadResourceById(String id);

    /**
     * load all resources whose type is type.
     * @param type resource type
     * @return ResourceDos loaded
     */
    List<ResourceDo> loadResourcesByType(String type);

    /**
     * load all resources.
     * @return ResourceDos loaded
     */
    List<ResourceDo> loadAllResources();
}
