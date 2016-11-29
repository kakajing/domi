package com.domi.portal.controller;

import com.domi.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问首页
 * Created by jing on 2016/11/18.
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        //取大广告位内容
        String json = contentService.getAd1List();
        model.addAttribute("ad1",json);
        return "index";
    }
}
