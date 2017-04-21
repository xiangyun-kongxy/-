package com.kxy.general.entrance.http;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public interface ServletPipe extends Serializable {
    /**
     *
     * @param req
     * @param resp
     */
    public void doPipe(ServletRequest req, ServletResponse resp);
}
