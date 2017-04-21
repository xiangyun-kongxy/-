package com.kxy.general.entrance.http;

import lombok.Setter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 8841140279394231872L;

    @Setter
    private List<ServletPipe> pipes;

    @Override
    public void service(ServletRequest req, ServletResponse resp)
            throws ServletException, IOException {
        for(ServletPipe pipe:pipes) {
            pipe.doPipe(req, resp);
        }
    }
}
