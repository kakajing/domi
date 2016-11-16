package com.domi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Created by jing on 2016/11/15.
 */
@Controller
public class PageController {

    /**
     * 打开首页
     * @return
     */
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    /**
     * 展示其他页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
