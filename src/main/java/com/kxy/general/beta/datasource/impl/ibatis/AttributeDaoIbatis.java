package com.kxy.general.beta.datasource.impl.ibatis;

import com.kxy.general.beta.datasource.dataaccess.AttributeDao;
import com.kxy.general.beta.datasource.dataobject.AttributeDo;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by xiangyunkong on 04/05/2017.
 */
public class AttributeDaoIbatis extends SqlMapClientDaoSupport
        implements AttributeDao {

    /**
     * namespace of this sqlmap.
     */
    private static final String NAMESPACE = "attribute.";

    /**
     * sql name of "load all attributes".
     */
    private static final String ST_LOAD_ALL_ATTRIBUTES = NAMESPACE
            + "load_all_attributes";

    /**
     * @see AttributeDao#loadAttributesByResourceId(String)
     */
    @Override
    public List<AttributeDo> loadAttributesByResourceId(String resourceId) {
        return null;
    }

    /**
     * @see AttributeDao#loadAllAttributes()
     */
    @Override
    public List<AttributeDo> loadAllAttributes() {
        return getSqlMapClientTemplate().queryForList(ST_LOAD_ALL_ATTRIBUTES);
    }
}
