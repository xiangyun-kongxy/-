package com.kxy.general.beta.datasource.impl.ibatis;

import com.kxy.general.beta.datasource.dataaccess.ResourceDao;
import com.kxy.general.beta.datasource.dataobject.ResourceDo;
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
     * sql name in sqlmap.
     */
    private static final String ST_LOAD_ALL_RESOURCES = NAMESPACE
            + "load_all_resources";

    /**
     * load Resource by resource id.
     * @param id resource id
     * @return loaded Resource
     */
    @Override
    public ResourceDo loadResourceById(String id) {
        return (ResourceDo) getSqlMapClientTemplate().queryForObject(
                ST_LOAD_RESOURCE_BY_ID, id);
    }

    /**
     * load Resources by resource type.
     * @param type resource type
     * @return loaded Resources
     */
    @Override
    public List<ResourceDo> loadResourcesByType(String type) {
        return null;
    }

    /**
     * load all Resources.
     * @return loaded Resources
     */
    @Override
    public List<ResourceDo> loadAllResources() {
        return getSqlMapClientTemplate().queryForList(ST_LOAD_ALL_RESOURCES);
    }
}
