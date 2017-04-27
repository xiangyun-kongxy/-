package com.kxy.general.entrance.http.handler;

import java.lang.reflect.Field;

/**
 *
 * Created by xiangyunkong on 21/04/2017.
 */
public abstract class AbstractActionRequest implements ActionRequest {
    private static final long serialVersionUID = 5931010996035255519L;

    /**
     * set object field whose name is 'name'(if exists).
     *
     * @param name field name
     * @param value the value to set
     */
    public void setActionField(final String name, final String value) {
        Class thisClass = this.getClass();
        try {
            Field field = thisClass.getField(name);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
