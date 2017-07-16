package com.kxy.general.common.storage.cache.local;

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by kongxiangyun on 6/25/17.
 */
public class KvMemoryCacheTest {
    private KvMemoryCache cache = new KvMemoryCache();

    @BeforeMethod
    public void setUp() throws Exception {
        for (Integer i = 0; i < 100; ++i) {
            cache.write(i.toString(), i);
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        cache.removeAll();
    }

    @Test
    public void testWriteThrough() throws Exception {
        cache.write("1", 2);

        Integer value = cache.query("1");
        Assert.assertEquals(value.intValue(), 2);
    }

    @Test
    public void testWriteWithCallback() throws Exception {
        cache.write("1", 2, (oldValue, newValue) -> {
            Assert.assertEquals(oldValue.size(), 1);
            Assert.assertEquals(newValue.size(), 1);
            Assert.assertEquals(oldValue.get("1").intValue(), 1);
            Assert.assertEquals(newValue.get("1").intValue(), 2);
            Map<String, Integer> result = new HashMap<>();
            result.put("1", 3);
            return result;
        });

        Integer value = cache.query("1");
        Assert.assertEquals(value.intValue(), 3);
    }

    @Test
    public void testWriteBench() throws Exception {
        Map<String, Integer> changes = new HashMap<>();
        for (Integer i = 1; i < 100; i += 2) {
            changes.put(i.toString(), i+1);
        }

        cache.write(changes);

        List<String> keys = new ArrayList<>();
        for (Integer i = 0; i < 100; ++i) {
            keys.add(i.toString());
        }

        Map<String, Integer> values = cache.query(keys);
        Assert.assertNotNull(values);
        Assert.assertEquals(values.size(), 100);
        for (Integer i = 0; i < 100; ++i) {
            Assert.assertEquals(values.get(i.toString()).intValue(),
                    i + i % 2);
        }
    }

    @Test
    public void testWrite3() throws Exception {
    }

    @Test
    public void testRemove() throws Exception {
    }

    @Test
    public void testRemove1() throws Exception {
    }

    @Test
    public void testRemoveAll() throws Exception {
    }

    @Test
    public void testGetVersion() throws Exception {
    }

    @Test
    public void testUpdateChanges() throws Exception {
    }

    @Test
    public void testQuery() throws Exception {
    }

    @Test
    public void testQuery1() throws Exception {
    }

    @Test
    public void testQuery2() throws Exception {
    }

    @Test
    public void testQuery3() throws Exception {
    }

    @Test
    public void testQueryAll() throws Exception {
    }

}