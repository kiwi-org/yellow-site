package org.huazai.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.huazai.mapper.SiteCollectMapper;
import org.huazai.model.entity.SiteCollectDO;
import org.huazai.model.vo.SiteVO;
import org.huazai.utils.OkHttpKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * @author YanAnHuaZai
 * created at：2020-09-01 22:22
 */
@Service
public class YellowService {

    @Resource
    private SiteCollectMapper siteCollectMapper;

    /**
     * 查询所有标题
     * @author YanAnHuaZai
     * create 2020年09月02日04:20:34
     * @return 所有标题
     */
    public List<String> queryTitleList() {
        return siteCollectMapper.selectTitleList();
    }

    /**
     * 根据标题查询具体的内容
     * @author YanAnHuaZai
     * create 2020年09月02日04:20:50
     * @param title 标题
     * @return 具体的内容
     */
    public String queryContentByTitle(String title) {
        SiteCollectDO siteCollectDO = siteCollectMapper.selectOne(Wrappers.<SiteCollectDO>lambdaQuery().eq(SiteCollectDO::getTitle, title));
        if (null == siteCollectDO) {
            return null;
        }
        return siteCollectDO.getContent();
    }

    /**
     * 自动获取链接并保存链接
     * @author YanAnHuaZai
     * create 2020年09月02日03:36:37
     */
    @SuppressWarnings("all")
    @PostConstruct
    private void autoSaveSiteUrl() {
        System.out.println("invoke autoSaveSiteUrl");
        new Thread(() -> {
            while (true) {
                try {
                    Map.Entry<String, String> yellowSiteM3u8Url = getYellowSiteM3u8Url();
                    System.out.println("保存：" + yellowSiteM3u8Url.getKey());
                    SiteCollectDO siteCollectDO = new SiteCollectDO();
                    siteCollectDO.setTitle(yellowSiteM3u8Url.getKey());
                    siteCollectDO.setUrl(yellowSiteM3u8Url.getValue());
                    siteCollectDO.setContent(OkHttpKit.get(yellowSiteM3u8Url.getValue()).execute());
                    siteCollectMapper.insert(siteCollectDO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Map.Entry<String, String> getYellowSiteM3u8Url() {
        String response = null;
        try {
            // rha: 日韩
            // gch: 国产
            // wm: 无码
            // omei: 欧美
            response = OkHttpKit.get("http://mu.coroad.cn/bf2.php?lx=gch&t=" + System.currentTimeMillis()).header("User-Agent", "Mozilla/5.0 (Linux; Android 10; ALP-AL00 Build/HUAWEIALP-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/79.0.3945.116 Mobile Safari/537.36 hap/1068/huawei com.huawei.fastapp/2.2.2.303 com.yunshuo.cloudbrowser/8.5.1 ({\"packageName\":\"com.tencent.mobileqq\",\"type\":\"other\",\"extra\":\"{}\"})").execute();
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
