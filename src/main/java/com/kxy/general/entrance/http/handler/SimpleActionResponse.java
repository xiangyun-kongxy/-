package com.kxy.general.entrance.http.handler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 28/04/2017.
 */
public class SimpleActionResponse implements ActionResponse {
    private static final long serialVersionUID = 1071349531074341569L;

    /**
     * response message.
     */
    @Getter
    private String message;

    /**
     * response code.
     */
    @Getter
    private Integer code;

    /**
     * response data object(if exists).
     */
    @Getter @Setter
    private Serializable data;

    /**
     * init with message and code.
     * @param message response message
     * @param code response code
     */
    public SimpleActionResponse(String message, Integer code) {
        this.setResult(message, code);
    }

    /**
     * set fields with message and code.
     * @param message response message
     * @param code response code
     */
    public void setResult(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
