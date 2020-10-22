package org.kiwi.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 前端显示
 * @author YanAnHuaZai
 * created at：2020-09-02 04:17
 */
@Getter
@Setter
@ToString
public class SiteVO implements Serializable {
    private static final long serialVersionUID = -4776376627795607993L;

    private String title;
    private String content;

}
