package org.kiwi;

import org.kiwi.common.Redis;
import org.kiwi.mapper.SiteCollectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    public void test() throws IOException {
//        List<SiteCollectDO> result = siteCollectMapper.selectList(null);
//        System.out.println(result);
        String url = "https://ts1.cmzcj.club:8082/20200817/NMVrAlH4/1100kb/hls/key.key";
//        URL u = new URL(url);
//        System.out.println(u.getHost());
//        System.out.println(u.getAuthority());
//        System.out.println(u.getProtocol());
//        System.out.println(u.getUserInfo());
//        System.out.println(u.getQuery());
//
//        String line = u.getProtocol() + "://" + u.getAuthority();
//        System.out.println("line = " + line);

        int firstIndex = url.indexOf("://");
        if (firstIndex > 0) {
            int index = url.indexOf("/", firstIndex + 3);
            String substring = url.substring(0, index);
            System.out.println("substring = " + substring);
        }


    }

    @Test
    public void testRedis() {
        redis.set("TEST", "TEST", 1, TimeUnit.HOURS);
        Object test = redis.get("TEST");
        System.out.println("test = " + test);
    }

}
