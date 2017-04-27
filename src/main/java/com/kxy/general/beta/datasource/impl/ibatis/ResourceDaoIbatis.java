package com.kxy.general.beta.datasource.impl.ibatis;

import com.kxy.general.beta.datasource.dataaccess.ResourceDao;
import com.kxy.general.beta.resource.Resource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class ResourceDaoIbatis extends SqlMapClientDaoSupport
        implements ResourceDao {
    /**
     * namespace of this ibatis.
     */
    private static  final String NAMESPACE = "resource.";

    /**
     * sql of this function.
     */
    private static  final String ST_LOAD_RESOURCE_BY_ID = NAMESPACE
                                    + "load_resource_by_id";

    /**
     * load <code>Resource</code> by resource id.
     * @param id resource id
     * @return loaded <code>Resource</code>
     */
    @Override
    public Resource loadResourceById(String id) {
        return (Resource) getSqlMapClientTemplate().queryForObject(
                ST_LOAD_RESOURCE_BY_ID, id);
    }

    /**
     * load <code>Resource</code>s by resource type.
     * @param type resource type
     * @return loaded <code>Resource</code>s
     */
    @Override
    public List<Resource> loadResourceByType(String type) {
        return null;
    }

    /**
     * load all <code>Resource</code>s.
     * @return loaded <code>Resource</code>s
     */
    @Override
    public List<Resource> loadAllResource() {
        return null;
    }
}
