package com.kxy.general.entrance.http.pipe.impl;

import com.kxy.general.common.exception.BizException;
import com.kxy.general.common.exception.error_info.ErrorInfo;
import com.kxy.general.entrance.http.handler.AbstractActionRequest;
import com.kxy.general.entrance.http.handler.ActionHandler;
import com.kxy.general.entrance.http.handler.ActionRequest;
import com.kxy.general.entrance.http.handler.ActionResponse;
import com.kxy.general.entrance.http.handler.SimpleActionResponse;
import com.kxy.general.entrance.http.pipe.ServletPipe;
import com.kxy.general.util.JsonUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class ActionHandlePipe implements ServletPipe {
    private static final long serialVersionUID = 7916490217503240959L;

    /**
     * loaded handlers.
     */
    private Map<String, ActionHandler> handlers = new HashMap<>();

    /**
     * to get the real action processor and do process.
     * @param req the request to process
     * @param resp the response
     */
    @Override
    public void doPipe(final ServletRequest req, ServletResponse resp) {
        ActionResponse response;
        try {
            ActionHandler handler = initHandler(req);
            ActionRequest request = initRequest(req, handler);
            response = handler.handle(request);
        } catch (BizException e) {
            response = new SimpleActionResponse(e.getMessage(), e.getCode());
        } catch (Exception e) {
            ErrorInfo er = ErrorInfo.E_INTERNAL_ERROR;
            response = new SimpleActionResponse(er.getMessage(), er.getCode());
            e.printStackTrace();
        }
        response(resp, response);
    }

    /**
     * init loaded requests and handlers.
     * @param ac application context
     */
    @Override
    public void setApplicationContext(ApplicationContext ac) {
        Map<String, ActionHandler> loadedHandlers;
        loadedHandlers = ac.getBeansOfType(ActionHandler.class);
        for (ActionHandler handler:loadedHandlers.values()) {
            handlers.put(handler.actionName(), handler);
        }
    }

    /**
     * get handler by request.
     * @param request servlet request
     * @return the proper handler
     * @throws BizException no proper handler for the request
     */
    private ActionHandler initHandler(ServletRequest request) throws
    BizException {
        String actionName = request.getParameter("action");
        if (StringUtils.isEmpty(actionName)) {
            throw new BizException(ErrorInfo.E_ACTION_NOT_FOUND, actionName);
        }
        ActionHandler handler = handlers.get(actionName);
        if (handler == null) {
            throw new BizException(ErrorInfo.E_ACTION_NOT_FOUND, actionName);
        }
        return handler;
    }

    /**
     * get handler parameter by servlet request and handler.
     * @param request servlet request
     * @param handler handler for servlet request
     * @return handler parameter
     * @throws BizException can not init handler parameter
     */
    private ActionRequest initRequest(ServletRequest request,
                                      ActionHandler handler) throws
    BizException {
        Class<?extends AbstractActionRequest> requestClass;
        requestClass = handler.getRequestClass();
        try {
            AbstractActionRequest req = requestClass.newInstance();
            for (Map.Entry<String, String[]> entry
                    : request.getParameterMap().entrySet()) {
                initField(req, entry.getKey(), entry.getValue()[0]);
            }
            return req;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new BizException(ErrorInfo.E_INTERNAL_ERROR);
        }
    }

    /**
     * write response message into servlet response.
     * @param resp servlet response
     * @param r handler result
     */
    private void response(ServletResponse resp, ActionResponse r) {
        try {
            resp.getOutputStream().println(JsonUtil.objectToJson(r));
            resp.getOutputStream().flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * set field for object.
     * @param object object to be inited
     * @param fieldName field to be inited
     * @param value field vlaue
     */
    private void initField(Object object, String fieldName, String value) {
        Field field = getField(object.getClass(), fieldName);
        if (field != null) {
            try {
                Class fieldType = field.getType();
                Constructor constructor;
                constructor = fieldType.getConstructor(String.class);
                if (constructor != null) {
                    field.setAccessible(true);
                    field.set(object, constructor.newInstance(value));
                }
            } catch (IllegalAccessException | NoSuchMethodException
                    | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get field from class or super class by field name.
     * @param type in which class to get field
     * @param name field name
     * @return field
     */
    private Field getField(Class type, final String name) {
        try {
            return type.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            Class superClass = type.getSuperclass();
            if (superClass != null) {
                return getField(superClass, name);
            }
        }
        return null;
    }
}
