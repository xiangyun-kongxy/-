package com.kxy.general.ß.datasource.dataaccess;

import com.kxy.general.ß.datasource.DataSource;
import com.kxy.general.ß.trigger.Trigger;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyunkong on 21/04/2017.
 */
public class TypeHolder implements InitializingBean {
    private static Map<String, Object> types = new HashMap<>();
    private static String STATUS = "loading";

    private Trigger x;
    @Setter
    private List<DataSource> dataSources;

    @Override
    public void afterPropertiesSet() throws Exception {
        STATUS = "loaded";
    }

    public static <T> T getType(String name, Class<T> javaPresent) {
        if(STATUS.equals("loaded")) {
            Object object = types.get(name);
            if (object != null && javaPresent.isInstance(object)) {
                return (T)object;
            }
        }
        return null;
    }

}
