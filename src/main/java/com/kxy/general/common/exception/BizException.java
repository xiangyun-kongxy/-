package com.kxy.general.common.exception;

import com.kxy.general.common.exception.error_info.ErrorInfo;

/**
 * Created by xiangyunkong on 02/05/2017.
 */
public class BizException extends Exception {
    private static final long serialVersionUID = -8711792218635012184L;

    /**
     * biz error code.
     */
    private Integer code;
    /**
     * biz error message.
     */
    private String message;

    /**
     * @see Exception#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * get error code.
     * @return error code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * biz exception must be binded with an error info const.
     * @param errorInfo error info
     * @param params parameters for errorInfo.message(formatter)
     */
    public BizException(ErrorInfo errorInfo, Object...params) {
        super();
        this.code = errorInfo.getCode();
        if (params != null && params.length > 0) {
            this.message = String.format(errorInfo.getMessage(), params);
        } else {
            this.message = errorInfo.getMessage();
        }
    }

}
