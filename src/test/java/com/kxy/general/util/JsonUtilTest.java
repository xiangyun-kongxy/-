package com.kxy.general.util;

import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Test;

/**
 * Created by xiangyunkong on 27/04/2017.
 */
public class JsonUtilTest {
    public static class TestSubClass {
        public static class TestSubClass2 {
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
        String json = JsonUtil.objectToJson(testSubClass);
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