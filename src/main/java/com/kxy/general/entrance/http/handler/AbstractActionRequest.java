package com.kxy.general.entrance.http.handler;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * Created by xiangyunkong on 21/04/2017.
 */
public abstract class AbstractActionRequest implements ActionRequest {
    private static final long serialVersionUID = 5931010996035255519L;

    /**
     * action name to the request.
     */
    @Getter @Setter
    private String action;

}
