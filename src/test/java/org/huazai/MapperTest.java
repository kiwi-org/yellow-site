package org.huazai;

import org.huazai.common.Redis;
import org.huazai.mapper.SiteCollectMapper;
import org.huazai.model.entity.SiteCollectDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YanAnHuaZai
 * created at：2020-09-02 01:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Resource
    private Redis redis;

    @Resource
    private SiteCollectMapper siteCollectMapper;

    @Test
    public void test() {
        List<SiteCollectDO> result = siteCollectMapper.selectList(null);
        System.out.println(result);
    }

    @Test
    public void testRedis() {
        Object test = redis.get("TEST");
        System.out.println("test = " + test);
    }

}
