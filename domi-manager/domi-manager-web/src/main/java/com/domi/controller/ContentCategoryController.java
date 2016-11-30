package com.domi.controller;

import com.domi.pojo.DomiResult;
import com.domi.pojo.EasyUITreeNode;
import com.domi.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId){

        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public DomiResult createNode(Long parentId, String name){
        DomiResult result = contentCategoryService.insertCategory(parentId, name);
        return result;
    }

}
