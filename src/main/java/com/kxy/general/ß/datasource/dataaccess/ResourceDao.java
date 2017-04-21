package com.kxy.general.ß.datasource.dataaccess;

import com.kxy.general.ß.datasource.DataSource;
import com.kxy.general.ß.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 17/04/2017.
 */
public interface ResourceDao extends DataSource {
    /**
     *
     * @param id
     * @return
     */
    Resource loadResourceById(String id);

    /**
     *
     * @param type
     * @return
     */
    List<Resource> loadResourceByType(String type);

    /**
     *
     * @return
     */
    List<Resource> loadAllResource();
}
