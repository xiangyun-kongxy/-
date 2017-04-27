package com.kxy.general.entrance.http.pipe;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public interface ServletPipe extends Serializable {
    /**
     * do something on request <code>req</code>, and put the result in
     * <code>resp</code>.
     * @param req the request to process
     * @param resp the response
     */
    void doPipe(ServletRequest req, ServletResponse resp);

    /**
     * set application context.
     * @param ac application context
     */
    void setApplicationContext(ApplicationContext ac);
}
