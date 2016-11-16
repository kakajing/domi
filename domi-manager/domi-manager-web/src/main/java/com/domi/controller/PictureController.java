package com.domi.controller;

import com.domi.pojo.PictureResult;
import com.domi.service.PictureService;
import com.domi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Created by jing on 2016/11/16.
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        PictureResult result = pictureService.uploadPic(uploadFile);
        //需要把java对象手工转换成json数据
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
