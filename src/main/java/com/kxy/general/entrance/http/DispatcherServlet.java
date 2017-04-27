package com.kxy.general.entrance.http;

import com.kxy.general.entrance.http.pipe.ServletPipe;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
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

    /**
     * processors to the request.
     */
    @Setter
    private List<ServletPipe> pipes;

    /**
     * init pipes.
     * @param config servlet config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac;
        ac = WebApplicationContextUtils.getWebApplicationContext(
                config.getServletContext());
        pipes = (List<ServletPipe>) ac.getBean("pipeChain");

        for (ServletPipe pipe:pipes) {
            pipe.setApplicationContext(ac);
        }
    }

    /**
     * entrance point of a request.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(final ServletRequest req, ServletResponse resp)
            throws ServletException, IOException {
        for (ServletPipe pipe:pipes) {
            pipe.doPipe(req, resp);
        }
    }
}
