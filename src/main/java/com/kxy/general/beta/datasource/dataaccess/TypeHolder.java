package com.kxy.general.beta.datasource.dataaccess;

import com.kxy.general.beta.datasource.DataSource;
import com.kxy.general.beta.trigger.Trigger;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type manager.
 * Created by xiangyunkong on 21/04/2017.
 */
public class TypeHolder implements InitializingBean {
    /**
     * all holden types.
     */
    private static Map<String, Object> types = new HashMap<>();

    /**
     * the status of the TypeHolder. the class' instance will provide service
     * only when STATUS is "loaded"
     */
    private static String status = "loading";

    /**
     * trigger for auto refresh.
     */
    private Trigger x;

    /**
     * data source which to load types.
     */
    @Setter
    private List<DataSource> dataSources;

    /**
     * init trigger and types.
     */
    @Override
    public void afterPropertiesSet() {
        status = "loaded";
    }

    /**
     * get type by type name name and type javaPresent.
     * @param name type name
     * @param javaPresent java class
     * @param <T> java class of the type
     * @return the found type
     */
    public static <T> T getType(String name, Class<T> javaPresent) {
        if (status.equals("loaded")) {
            Object object = types.get(name);
            if (object != null && javaPresent.isInstance(object)) {
                return (T) object;
            }
        }
        return null;
    }

}
