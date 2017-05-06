package com.kxy.general.entrance.http.handler.impl.system.status.service_status;

import com.kxy.general.common.exception.error_info.ErrorInfo;
import com.kxy.general.entrance.http.handler.AbstractActionRequest;
import com.kxy.general.entrance.http.handler.ActionHandler;
import com.kxy.general.entrance.http.handler.ActionRequest;
import com.kxy.general.entrance.http.handler.ActionResponse;
import com.kxy.general.entrance.http.handler.SimpleActionResponse;

/**
 * Created by xiangyunkong on 02/05/2017.
 */
public class StatusHandler implements ActionHandler {

    /**
     * @see ActionHandler#getRequestClass()
     */
    @Override
    public Class<? extends AbstractActionRequest> getRequestClass() {
        return StatusRequest.class;
    }

    /**
     * @see ActionHandler#actionName()
     */
    @Override
    public String actionName() {
        return "status";
    }

    /**
     * just return ok if reaches here.
     * @param request the request to process
     * @return ok response
     */
    @Override
    public ActionResponse handle(ActionRequest request) {
        ErrorInfo msg = ErrorInfo.E_OK;
        return new SimpleActionResponse(msg.getMessage(), msg.getCode());
    }
}
