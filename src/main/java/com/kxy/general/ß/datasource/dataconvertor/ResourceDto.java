package com.kxy.general.ß.datasource.dataconvertor;

import com.kxy.general.ß.datasource.dataaccess.TypeHolder;
import com.kxy.general.ß.datasource.dataobject.AttributeDo;
import com.kxy.general.ß.datasource.dataobject.ResourceDo;
import com.kxy.general.ß.resource.Resource;
import com.kxy.general.ß.resource.ResourceType;

import java.util.List;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceDto {
    public static Resource toResource(ResourceDo resourceDo, List<AttributeDo> attributeDos) {
        ResourceType resourceType = TypeHolder.getType(resourceDo.getResourceType(),
                ResourceType.class);
        Resource resource = new Resource(resourceDo.getResourceId(), resourceType);
        attributeDos.forEach(attributeDo ->
                resource.addAttribute(AttributeDto.toAttribute(attributeDo)));
        return resource;
    }

    public static ResourceDo toResourceDo(Resource resource) {
        ResourceDo resourceDo = new ResourceDo();
        resourceDo.setResourceId(resource.getResourceId());
        resourceDo.setResourceType(resource.getResourceType().getName());
        return resourceDo;
    }
}
