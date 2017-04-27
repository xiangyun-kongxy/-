package com.kxy.general.beta.datasource.dataaccess;

import com.kxy.general.beta.datasource.DataSource;
import com.kxy.general.beta.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 17/04/2017.
 */
public interface ResourceDao extends DataSource {
    /**
     * load resource by resource id.
     * @param id resource id
     * @return <code>Resource</code> object
     */
    Resource loadResourceById(String id);

    /**
     * load all resources whose type is <code>type</code>.
     * @param type resource type
     * @return <code>Resource</code>s loaded
     */
    List<Resource> loadResourceByType(String type);

    /**
     * load all resources.
     * @return <code>Resource</code>s loaded
     */
    List<Resource> loadAllResource();
}
