package com.kxy.general.beta.datasource.dataconvertor;

import com.kxy.general.beta.datasource.dataaccess.TypeHolder;
import com.kxy.general.beta.datasource.dataobject.AttributeDo;
import com.kxy.general.beta.datasource.dataobject.ResourceDo;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.resource.ResourceType;

import java.util.List;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public final class ResourceDto {
    /**
     * utility class should not be instanced.
     */
    private ResourceDto() {
    }

    /**
     * convert ResourceDo to Resource.
     * @param resourceDo ResourceDo to be converted
     * @param attributeDos Attribute belonged to Resource
     * @return the converted Resource
     */
    public static Resource toResource(ResourceDo resourceDo,
                                      List<AttributeDo> attributeDos) {
        ResourceType resourceType = TypeHolder.getType(
                resourceDo.getResourceType(), ResourceType.class);
        Resource resource = new Resource(resourceDo.getResourceId(),
                resourceType);
        attributeDos.forEach(attributeDo ->
                resource.addAttribute(AttributeDto.toAttribute(attributeDo)));
        return resource;
    }

    /**
     * convert Resource to ResourceDo.
     * @param resource Resource to be converted
     * @return converted ResourceDo
     */
    public static ResourceDo toResourceDo(Resource resource) {
        ResourceDo resourceDo = new ResourceDo();
        resourceDo.setResourceId(resource.getResourceId());
        resourceDo.setResourceType(resource.getResourceType().getName());
        return resourceDo;
    }
}
