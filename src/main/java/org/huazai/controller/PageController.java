package org.huazai.controller;

import org.huazai.service.YellowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面跳转
 * @author YanAnHuaZai
 * created at：2020-09-02 04:10
 */
@Controller
public class PageController {

    @Resource
    private YellowService yellowService;

    /**
     * 首页
     * @author YanAnHuaZai
     * create 2020年09月02日04:23:50
     */
    @RequestMapping({"", "/", "index.html", "index"})
    public String homepage(Model model) {
        model.addAttribute("titleList", yellowService.queryTitleList());
        return "category";
    }

    @RequestMapping("content")
    public String content(Model model, String title) {
        if (StringUtils.isEmptyOrWhitespace(title)) {
            return "redirect:/index";
        }

        return "content";
    }
}
