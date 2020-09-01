package org.huazai.controller;

import org.huazai.service.YellowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 页面跳转
 * @author YanAnHuaZai
 * created at：2020-09-02 04:10
 */
@Controller
public class PageController {

    @Resource
    private YellowService yellowService;

    @RequestMapping({"", "/", "index.html", "index"})
    public String homepage(Model model) {
        return "category";
    }
}
