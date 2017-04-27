package com.kxy.general.entrance.http.handler;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public interface ActionRequest extends Serializable {
    /**
     * get the name of an action who processes this type of request.
     * @return the action name
     */
    String actionName();

    /**
     * get the type of the handler who can process this request.
     * @return handler type to this request
     */
    Class<ActionHandler> handlerType();
}
