package com.kxy.general.beta.datasource.dataaccess;

import com.kxy.general.beta.datasource.dataobject.AttributeDo;

import java.util.List;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public interface AttributeDao {
    /**
     * load attributes by resource id.
     * @param resourceId resource id
     * @return loaded attributes
     */
    List<AttributeDo> loadAttributesByResourceId(String resourceId);

    /**
     * load all attributes.
     * @return loaded attributes
     */
    List<AttributeDo> loadAllAttributes();
}
