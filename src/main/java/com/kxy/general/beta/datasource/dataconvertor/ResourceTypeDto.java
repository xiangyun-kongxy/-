package com.kxy.general.beta.datasource.dataconvertor;

import com.kxy.general.beta.datasource.dataobject.ResourceTypeDo;
import com.kxy.general.beta.resource.ResourceType;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public final class ResourceTypeDto {
    /**
     * utility class should not be instanced.
     */
    private ResourceTypeDto() {
    }

    /**
     * convert <code>ResourceTypeDo</code> to <code>ResourceType</code>.
     * @param resourceTypeDo <code>ResourceTypeDo</code> to be converted
     * @return converted <code>ResourceType</code>
     */
    public static ResourceType toResourceType(ResourceTypeDo resourceTypeDo) {
        ResourceType resourceType = new ResourceType();
        resourceType.setName(resourceTypeDo.getTypename());
        resourceTypeDo.getAttributes().forEach(resourceType::addAttribute);
        return resourceType;
    }

    /**
     * convert <code>ResourceType</code> to <code>ResourceTypeDo</code>.
     * @param resourceType <code>ResourceType</code> to be converted
     * @return converted <code>ResourceTypeDo</code>
     */
    public static ResourceTypeDo toResourceTypeDo(ResourceType resourceType) {
        ResourceTypeDo resourceTypeDo = new ResourceTypeDo();
        resourceTypeDo.setTypename(resourceType.getName());
        resourceTypeDo.setAttributes(resourceType.getAttributes());
        return resourceTypeDo;
    }
}
