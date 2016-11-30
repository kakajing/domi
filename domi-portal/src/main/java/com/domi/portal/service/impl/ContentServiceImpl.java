package com.domi.portal.service.impl;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbContent;
import com.domi.portal.pojo.AdNode;
import com.domi.portal.service.ContentService;
import com.domi.utils.HttpClientUtil;
import com.domi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;
    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;


    /**
     * 获得大广告位的内容
     * @return
     */
    @Override
    public String getAd1List() {



        //调用服务获得数据
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
        //把json转换成java对象
        DomiResult domiResult = DomiResult.formatToList(json, TbContent.class);
        //取data属性，内容列表
        List<TbContent> list = (List<TbContent>) domiResult.getData();
        //把内容列表转换成AdNode列表
        List<AdNode> resultList = new ArrayList<>();
        for (TbContent tbContent : list) {
            AdNode node = new AdNode();
            node.setHeight(240);
            node.setWidth(670);
            node.setSrc(tbContent.getPic());

            node.setHeightB(240);
            node.setWidthB(550);
            node.setSrcB((tbContent.getPic2()));

            resultList.add(node);
        }
        //需要把resultList转换成json数据
        String resultJson = JsonUtils.objectToJson(resultList);

        return resultJson; 
    }
}
