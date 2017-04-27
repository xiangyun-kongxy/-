package com.kxy.general.entrance.http.handler;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public interface ActionHandler extends Serializable {
    /**
     * the real code to process the request.
     * @param request the request to process
     * @return the result of processing
     */
    ActionResponse handle(ActionRequest request);
}
