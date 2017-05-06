package com.kxy.general.common.exception.error_info;

import lombok.Getter;

/**
 * Created by xiangyunkong on 02/05/2017.
 */
public enum ErrorInfo {
    /**
     * resource is not enough.
     */
    E_NOT_ENOUGH_RESOURCE(-10002, "resource is not enough"),
    /**
     * the required action is not define.
     */
    E_ACTION_NOT_FOUND(-10000, "action not found: %s"),
    /**
     * unknown internal error. usually code bug
     */
    E_INTERNAL_ERROR(-10001, "system internal error"),
    /**
     * all be successful.
     */
    E_OK(200, "Success");

    /**
     * error code.
     */
    @Getter
    private Integer code;
    /**
     * error message.
     */
    @Getter
    private String message;

    /**
     * init error info with code and message.
     * @param code error code
     * @param message error message
     */
    ErrorInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
