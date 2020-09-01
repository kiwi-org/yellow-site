package org.huazai.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 网站汇总
 * @author YanAnHuaZai
 * created at：2020-09-02 00:44
 */
@Getter
@Setter
@ToString
@TableName("t_site_collect")
public class SiteCollectDO {

    private String title;
    private String url;
    private String content;

}
