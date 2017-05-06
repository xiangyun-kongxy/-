package com.kxy.general.beta.service.impl;

import com.kxy.general.beta.datasource.dataaccess.AttributeDao;
import com.kxy.general.beta.datasource.dataaccess.ResourceDao;
import com.kxy.general.beta.datasource.dataconvertor.ResourceDto;
import com.kxy.general.beta.datasource.dataobject.AttributeDo;
import com.kxy.general.beta.datasource.dataobject.ResourceDo;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.service.ResourceService;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public class ResourceServiceImpl implements ResourceService {
    /**
     * resource data access service.
     */
    @Setter
    private ResourceDao resourceDao;
    /**
     * attribute data access service.
     */
    @Setter
    private AttributeDao attributeDao;

    /**
     * @see ResourceService#loadAllResources()
     */
    @Override
    public List<Resource> loadAllResources() {
        List<ResourceDo> resourceDos = resourceDao.loadAllResources();
        List<AttributeDo> attributeDos = attributeDao.loadAllAttributes();

        // sort attributes by resource id
        Map<String, List<AttributeDo>> resourceAttribute = new HashMap<>();
        for (AttributeDo attributeDo:attributeDos) {
            List<AttributeDo> attr = resourceAttribute.computeIfAbsent(
                    attributeDo.getResourceId(), k -> new ArrayList<>());
            attr.add(attributeDo);
        }

        // convert ResourceDo and AttributeDo to Resource
        List<Resource> resources = new ArrayList<>();
        for (ResourceDo resourceDo:resourceDos) {
            resources.add(ResourceDto.toResource(resourceDo,
                    resourceAttribute.get(resourceDo.getResourceId())));
        }

        return resources;
    }
}
