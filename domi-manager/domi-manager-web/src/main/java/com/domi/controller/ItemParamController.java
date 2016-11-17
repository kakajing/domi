package com.domi.controller;

import com.domi.pojo.DomiResult;
import com.domi.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格参数模板管理Controller
 * Created by jing on 2016/11/17.
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
   private ItemParamService itemParamService;

    /**
     * 判断模板是否存在
     */
    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public DomiResult getItemCatByCid(@PathVariable Long cid){
        DomiResult result = itemParamService.getItemParamByCid(cid);
        return result;
    }

    /**
     * 提交规格参数模板
     */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public DomiResult insertItemParam(@PathVariable Long cid, String paramData){
        DomiResult result = itemParamService.insertItemParam(cid, paramData);
        return result;
    }

    /**
     * 功能未实现
     */
    @RequestMapping("/delete")
    @ResponseBody
    public DomiResult deleteItemParam(@PathVariable Long cid){
        DomiResult result = itemParamService.deleteItemParam(cid);
        return result;
    }
}
