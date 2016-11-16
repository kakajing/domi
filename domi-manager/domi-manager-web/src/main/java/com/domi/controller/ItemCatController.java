package com.domi.controller;

import com.domi.pojo.EasyUITreeNode;
import com.domi.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理
 * Created by jing on 2016/11/15.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }
}
