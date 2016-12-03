package com.domi.search.controller;

import com.domi.pojo.DomiResult;
import com.domi.search.service.ItemService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author 卡卡
 * Created by jing on 2016/11/30.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 索引库导入数据
     * @return
     */
    @RequestMapping("/importall")
    @ResponseBody
    public DomiResult importaAll(){
        try {
            DomiResult result = itemService.importItems();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }
}
