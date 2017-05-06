package com.kxy.general.beta.service;

import com.kxy.general.beta.resource.Resource;

import java.util.List;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public interface ResourceService {
    /**
     * load all resources from data source.
     * @return all loaded resources
     */
    List<Resource> loadAllResources();
}
