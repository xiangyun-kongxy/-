package com.kxy.general.common.storage.adapter;

import com.kxy.general.common.storage.RdbStorage;
import com.kxy.general.common.versioncontrol.ChangeLog;
import com.kxy.general.common.versioncontrol.changelog.rdb.RowChangeLog;
import com.kxy.general.common.versioncontrol.changelog.rdb.TimeChangeLog;
import com.kxy.general.common.versioncontrol.version.SimpleVersion;
import lombok.Getter;
import lombok.Setter;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by kongxiangyun on 6/24/17.
 */
public class CacheReloadAutoAdapterForRdbTest {
    @Tested
    CacheReloadAutoAdapterForRdb cacheReloadAutoAdapterForRdb;

    @Injectable
    RdbStorage rdbStorage;

    private class TestedDO implements Serializable {
        private static final long serialVersionUID = 3111839261797795194L;

        @Setter
        @Getter
        private String key;

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof TestedDO) &&
                           key.equals(((TestedDO)obj).key);
        }
    }

    @BeforeMethod
    public void setUp() {
        cacheReloadAutoAdapterForRdb = new CacheReloadAutoAdapterForRdb();
        cacheReloadAutoAdapterForRdb.setKeyFieldName("key");
        cacheReloadAutoAdapterForRdb.setQueryAllSqlName("queryAll");
        cacheReloadAutoAdapterForRdb.setQueryByKeyListSqlName("queryByKeys");
        cacheReloadAutoAdapterForRdb.setQueryByModifyTimeSqlName("queryByTime");
    }

    @Test
    public void testQueryByRowChangeLog() throws Exception {
        final List<ChangeLog> changeLogs = new ArrayList<>();
        changeLogs.add(new RowChangeLog(new SimpleVersion(1L),
                Collections.singletonList("r1"),
                System.currentTimeMillis()));

        TestedDO testedDO = new TestedDO();
        testedDO.setKey("r1");

        new Expectations() {
            {
                rdbStorage.queryForList("queryByKeys",
                        changeLogs.get(0).getChangedObjectIds());
                result = Arrays.asList(testedDO);
            }
        };

        Map<String, TestedDO> result;
        result = cacheReloadAutoAdapterForRdb.query(changeLogs);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(result.get("r1"), testedDO);
    }

    @Test
    public void testQueryByTimeChangeLog() throws Exception {
        final List<ChangeLog> changeLogs = new ArrayList<>();
        changeLogs.add(new TimeChangeLog(new SimpleVersion(1L),
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                System.currentTimeMillis()));

        TestedDO testedDO = new TestedDO();
        testedDO.setKey("r1");

        new Expectations() {
            {
                rdbStorage.queryForList("queryByTime",
                        changeLogs.get(0).getChangedObjectIds().get(0));
                result = Arrays.asList(testedDO);
            }
        };

        Map<String, TestedDO> result;
        result = cacheReloadAutoAdapterForRdb.query(changeLogs);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(result.get("r1"), testedDO);
    }


    @Test
    public void testQueryByChangeAll() throws Exception {
        final List<ChangeLog> changeLogs = new ArrayList<>();
        changeLogs.add(new RowChangeLog(new SimpleVersion(1L),
                Collections.singletonList(RowChangeLog.ALL_CHANGED),
                System.currentTimeMillis()));

        TestedDO testedDO1 = new TestedDO();
        testedDO1.setKey("r1");
        TestedDO testedDO2 = new TestedDO();
        testedDO2.setKey("r2");

        new Expectations() {
            {
                rdbStorage.queryForList("queryAll", any);
                result = Arrays.asList(testedDO1, testedDO2);
                result = null;
            }
        };

        Map<String, TestedDO> result;
        result = cacheReloadAutoAdapterForRdb.query(changeLogs);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get("r1"), testedDO1);
        Assert.assertEquals(result.get("r2"), testedDO2);
    }

}