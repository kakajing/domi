package com.domi.rest.controller;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbContent;
import com.domi.rest.service.ContentService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 发布内容查询服务
     */
    @RequestMapping("/content/{cid}")
    @ResponseBody
    public DomiResult getContentList(@PathVariable Long cid) {
        try {
            List<TbContent> list = contentService.getContentList(cid);
            return DomiResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 缓存同步
     */
    @RequestMapping("/syn/content/{cid}")
    @ResponseBody
    public DomiResult synContent(@PathVariable Long cid){
        try {
            DomiResult result = contentService.synContent(cid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }
}
