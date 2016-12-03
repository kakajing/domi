package com.domi.rest.controller;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbItem;
import com.domi.pojo.TbItemDesc;
import com.domi.pojo.TbItemParamItem;
import com.domi.rest.service.ItemService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author 卡卡
 * Created by jing on 2016/12/4.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 查询商品基本信息
     * @param itemId
     * @return
     */
    @RequestMapping("/base/{itemId}")
    @ResponseBody
    public DomiResult getItemById(@PathVariable Long itemId){

        try {
            TbItem item = itemService.getItemById(itemId);
            return DomiResult.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public DomiResult getItemDescById(@PathVariable Long itemId){

        try {
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            return DomiResult.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public DomiResult getItemParamById(@PathVariable Long itemId){
        try {
            TbItemParamItem itemParamItem = itemService.getItemParamById(itemId);
            return DomiResult.ok(itemParamItem);
        }catch (Exception e){
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }

    }
}
