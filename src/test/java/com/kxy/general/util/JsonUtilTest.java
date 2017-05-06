package com.kxy.general.util;

import com.kxy.general.entrance.http.handler.SimpleActionResponse;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Test;

import java.io.Serializable;

/**
 * Created by xiangyunkong on 27/04/2017.
 */
public class JsonUtilTest {
    public static class TestSubClass implements Serializable {
        private static final long serialVersionUID = -7725004886260948594L;

        public static class TestSubClass2 implements Serializable {
            private static final long serialVersionUID = 5111905092643042044L;
            @Getter @Setter
            private String description;
        }
        @Getter @Setter
        private String name;
        @Getter @Setter
        private Integer age;
        @Getter @Setter
        private TestSubClass2 desc;
    }

    @Test
    public void testObjectToJson() throws Exception {
        TestSubClass testSubClass = new TestSubClass();
        testSubClass.setAge(18);
        testSubClass.setName("Lily");
        testSubClass.setDesc(new TestSubClass.TestSubClass2());
        testSubClass.getDesc().setDescription("she is beautiful!");
        SimpleActionResponse rsp = new SimpleActionResponse("ok", 200);
        rsp.setData(testSubClass);
        String json = JsonUtil.objectToJson(rsp);
        System.out.println(json);
    }

    @Test
    public void testJsonToObject() throws Exception {
        String json = "{\"age\":18," +
                "\"desc\":{\"description\":\"she is beautiful!\"}," +
                "\"name\":\"Lily\"}";
        TestSubClass testSubClass = JsonUtil.jsonToObject(json,
                TestSubClass.class);
        System.out.println(testSubClass.toString());
    }

}