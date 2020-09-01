package org.huazai.controller;

import org.huazai.service.YellowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YanAnHuaZai
 * created at：2020-09-01 22:22
 */
@RestController
@RequestMapping("yellow")
public class YellowController {

    @Resource
    private YellowService yellowService;

    /**
     * 获取具体的内容
     * @author YanAnHuaZai
     * create 2020年09月02日04:40:00
     * @param title 标题
     */
    @RequestMapping("{title}")
    public String content(@PathVariable String title) {
        return yellowService.queryContentByTitle(title);
    }
}
