package com.domi.controller;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbContent;
import com.domi.service.ContentService;
import com.domi.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYN_URL}")
    private String REST_CONTENT_SYN_URL;

    /**
     * 新增内容
     * @param content
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public DomiResult insertContent(TbContent content){
        DomiResult result = contentService.insertContent(content);
        //调用domi-rest发布的服务同步缓存
        HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYN_URL + content.getCategoryId());
        return result;
    }
}
