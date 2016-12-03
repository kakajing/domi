package com.domi.portal.service.impl;

import com.domi.pojo.DomiResult;
import com.domi.portal.pojo.SearchResult;
import com.domi.portal.service.SearchService;
import com.domi.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现搜索服务
 * Author 卡卡
 * Created by jing on 2016/12/3.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public com.domi.portal.pojo.SearchResult search(String keyword, int page, int rows) {

        //调用服务查询商品列表
        Map<String, String> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("page", page + "");
        param.put("rows", rows + "");

        //调用服务
        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);

        DomiResult domiResult = DomiResult.formatToPojo(json, SearchResult.class);
        SearchResult searchResult = (SearchResult) domiResult.getData();
        return searchResult;
    }
}
