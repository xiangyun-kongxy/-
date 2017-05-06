package com.kxy.general.entrance.http.handler;

import com.kxy.general.common.exception.BizException;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public interface ActionHandler {
    /**
     * get action corresponded request class.
     * @return class of request
     */
    Class<? extends AbstractActionRequest> getRequestClass();

    /**
     * get the name of an action who processes this type of request.
     * @return the action name
     */
    String actionName();

    /**
     * the real code to process the request.
     * @param request the request to process
     * @return the result of processing
     * @throws BizException business exception
     */
    ActionResponse handle(ActionRequest request) throws BizException;
}
