package com.kxy.general.entrance.http;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public interface ActionHandler {
    /**
     *
     * @param request
     * @return
     */
    ActionResponse handle(ActionRequest request);
}
