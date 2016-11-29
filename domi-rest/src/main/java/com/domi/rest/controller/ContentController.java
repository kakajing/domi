package com.domi.rest.controller;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbContent;
import com.domi.rest.service.ContenService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 发布内容查询服务
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Controller
public class ContentController {

    @Autowired
    private ContenService contenService;

    @RequestMapping("/content/{cid}")
    @ResponseBody
    public DomiResult getContentLIst(@PathVariable Long cid){
        try {
            List<TbContent> list = contenService.getContentList(cid);
            return DomiResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }
}
