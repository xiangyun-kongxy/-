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
     * convert ResourceTypeDo to ResourceType.
     * @param resourceTypeDo ResourceTypeDo to be converted
     * @return converted ResourceType
     */
    public static ResourceType toResourceType(ResourceTypeDo resourceTypeDo) {
        ResourceType resourceType = new ResourceType();
        resourceType.setName(resourceTypeDo.getTypename());
        resourceTypeDo.getAttributes().forEach(resourceType::addAttribute);
        return resourceType;
    }

    /**
     * convert ResourceType to ResourceTypeDo.
     * @param resourceType ResourceType to be converted
     * @return converted ResourceTypeDo
     */
    public static ResourceTypeDo toResourceTypeDo(ResourceType resourceType) {
        ResourceTypeDo resourceTypeDo = new ResourceTypeDo();
        resourceTypeDo.setTypename(resourceType.getName());
        resourceTypeDo.setAttributes(resourceType.getAttributes());
        return resourceTypeDo;
    }
}
