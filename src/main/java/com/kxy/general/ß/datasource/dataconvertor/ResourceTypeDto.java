package com.kxy.general.ß.datasource.dataconvertor;

import com.kxy.general.ß.datasource.dataobject.ResourceTypeDo;
import com.kxy.general.ß.resource.ResourceType;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ResourceTypeDto {
    public static ResourceType toResourceType(ResourceTypeDo resourceTypeDo) {
        ResourceType resourceType = new ResourceType();
        resourceType.setName(resourceTypeDo.getTypename());
        resourceTypeDo.getAttributes().forEach(resourceType::addAttribute);
        return resourceType;
    }

    public static ResourceTypeDo toResourceTypeDo(ResourceType resourceType) {
        ResourceTypeDo resourceTypeDo = new ResourceTypeDo();
        resourceTypeDo.setTypename(resourceType.getName());
        resourceTypeDo.setAttributes(resourceType.getAttributes());
        return resourceTypeDo;
    }
}
