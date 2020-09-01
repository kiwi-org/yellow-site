package org.huazai.service;

import com.sun.tools.corba.se.idl.StringEntry;
import org.huazai.mapper.SiteCollectMapper;
import org.huazai.utils.OkHttpKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;

/**
 * @author YanAnHuaZai
 * created at：2020-09-01 22:22
 */
@Service
public class YellowService {

    @Resource
    private SiteCollectMapper siteCollectMapper;

    private Map.Entry<String, String> getYellowSiteM3u8Url() {
        String response = null;
        try {
            response = OkHttpKit.get("http://mu.coroad.cn/bf2.php?lx=rha&t=" + System.currentTimeMillis()).header("User-Agent", "Mozilla/5.0 (Linux; Android 10; ALP-AL00 Build/HUAWEIALP-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/79.0.3945.116 Mobile Safari/537.36 hap/1068/huawei com.huawei.fastapp/2.2.2.303 com.yunshuo.cloudbrowser/8.5.1 ({\"packageName\":\"com.tencent.mobileqq\",\"type\":\"other\",\"extra\":\"{}\"})").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        Document document = Jsoup.parse(response, "http://mu.coroad.cn//bf2.php");
        String videoName = document.selectFirst("body > font:eq(1) > h1").text().substring("视频名称:".length());
        Elements scriptTags = document.getElementsByTag("script");
        String url = null;
        for (Element script : scriptTags) {
            if (script.html().contains(".m3u8")) {
                String[] lineArr = script.html().split(System.lineSeparator());
                for (String line : lineArr) {
                    if (line.contains(".m3u8")) {
                        url = line.substring(line.indexOf('\'') + 1, line.lastIndexOf('\''));
                        break;
                    }
                }
                break;
            }
        }
        return new AbstractMap.SimpleEntry<>(videoName, url);
    }
}
