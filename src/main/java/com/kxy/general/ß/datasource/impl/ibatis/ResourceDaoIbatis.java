package com.kxy.general.ß.datasource.impl.ibatis;

import com.kxy.general.ß.datasource.dataaccess.ResourceDao;
import com.kxy.general.ß.resource.Resource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by xiangyunkong on 20/04/2017.
 */
public class ResourceDaoIbatis extends SqlMapClientDaoSupport implements ResourceDao {
    private final static String namespace = "resource.";
    private final static String ST_LOAD_RESOURCE_BY_ID = namespace + "load_resource_by_id";

    @Override
    public Resource loadResourceById(String id) {
        return (Resource) getSqlMapClientTemplate().queryForObject(ST_LOAD_RESOURCE_BY_ID, id);
    }

    @Override
    public List<Resource> loadResourceByType(String type) {
        return null;
    }

    @Override
    public List<Resource> loadAllResource() {
        return null;
    }
}
