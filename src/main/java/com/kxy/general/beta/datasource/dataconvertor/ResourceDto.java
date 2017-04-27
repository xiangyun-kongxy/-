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
     * convert <code>ResourceDo</code> to <code>Resource</code>.
     * @param resourceDo <code>ResourceDo</code> to be converted
     * @param attributeDos <code>Attribute</code> belonged to
     *                     <code>Resource</code>
     * @return the converted <code>Resource</code>
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
     * convert <code>Resource</code> to <code>ResourceDo</code>.
     * @param resource <code>Resource</code> to be converted
     * @return converted <code>ResourceDo</code>
     */
    public static ResourceDo toResourceDo(Resource resource) {
        ResourceDo resourceDo = new ResourceDo();
        resourceDo.setResourceId(resource.getResourceId());
        resourceDo.setResourceType(resource.getResourceType().getName());
        return resourceDo;
    }
}
