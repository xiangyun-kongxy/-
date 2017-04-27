package com.kxy.general.entrance.http.pipe.impl;

import com.kxy.general.entrance.http.handler.AbstractActionRequest;
import com.kxy.general.entrance.http.handler.ActionHandler;
import com.kxy.general.entrance.http.handler.ActionResponse;
import com.kxy.general.entrance.http.pipe.ServletPipe;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ActionHandlePipe implements ServletPipe {
    private static final long serialVersionUID = 7916490217503240959L;

    /**
     * loaded requests.
     */
    private Map<String, AbstractActionRequest> requests;

    /**
     * loaded handlers.
     */
    private Map<String, ActionHandler> handlers;

    /**
     * to get the real action processor and do process.
     * @param req the request to process
     * @param resp the response
     */
    @Override
    public void doPipe(final ServletRequest req, ServletResponse resp) {
        String actionName = req.getParameter("action");
        if (StringUtils.isEmpty(actionName)) {
            try {
                resp.getOutputStream().println("ERROR: action not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AbstractActionRequest request = requests.get(actionName);
            for (Map.Entry<String, String[]> entry
                    :req.getParameterMap().entrySet()) {
                request.setActionField(entry.getKey(), entry.getValue()[0]);
            }

            ActionHandler handler = handlers.get(actionName);
            ActionResponse response = handler.handle(request);
        }
    }

    /**
     * init loaded requests and handlers.
     * @param ac application context
     */
    @Override
    public void setApplicationContext(ApplicationContext ac) {
        Map<String, AbstractActionRequest> loadedRequests;
        loadedRequests = ac.getBeansOfType(AbstractActionRequest.class);
        for (AbstractActionRequest request:loadedRequests.values()) {
            requests.put(request.actionName(), request);
            ActionHandler handler = ac.getBean(request.handlerType());
            handlers.put(request.actionName(), handler);
        }
    }
}
