package com.domi.controller;

import com.domi.pojo.DomiResult;
import com.domi.pojo.EasyUIDataGridResult;
import com.domi.pojo.TbItem;
import com.domi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品查询
 * Created by jing on 2016/11/15.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem item = itemService.getItemById(itemId);
        return item;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public DomiResult createItem(TbItem item, String desc, String itemParams){
        DomiResult result = itemService.createItem(item, desc, itemParams);
        return result;
    }
}
