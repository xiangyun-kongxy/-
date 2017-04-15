package com.kxy.general.entrance.http;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 8841140279394231872L;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        List<String> param = new ArrayList<String>();
        Enumeration<String> params = req.getParameterNames();
        while(params.hasMoreElements()) {
            param.add(params.nextElement());
        }
        res.getOutputStream().print("Your parameters:");
        res.getOutputStream().print(param.toString());
        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
