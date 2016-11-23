package com.domi.rest.controller;

import com.domi.rest.pojo.ItemCatResult;
import com.domi.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author 卡卡
 * Created by jing on 2016/11/19.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

//    @RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//    @ResponseBody
//    public String genItemCatList(String callback){
//        ItemCatResult result = itemCatService.getItemCatList();
//        if (StringUtils.isBlank(callback)){
//            //需要把result转换成字符串
//            String json = JsonUtils.objectToJson(result);
//            return json;
//        }
//        //如果字符串不为空，需要支持jsonp调用
//        //需要把result转换成字符串
//        String json = JsonUtils.objectToJson(result);
//        return callback + "(" + json + ");";
//    }

    @RequestMapping("/list")
    @ResponseBody
    public Object genItemCatList(String callback){
        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)){
            //需要把result转换成字符串
            return result;
        }
        //如果字符串不为空，需要支持jsonp调用
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
