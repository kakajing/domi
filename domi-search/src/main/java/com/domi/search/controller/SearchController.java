package com.domi.search.controller;

import com.domi.pojo.DomiResult;
import com.domi.search.pojo.SearchResult;
import com.domi.search.service.SearchService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 发布搜索服务
 * Author 卡卡
 * Created by jing on 2016/12/3.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/q")
    @ResponseBody
    public DomiResult search(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "30") int rows){

        try {
            keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            SearchResult searchResult = searchService.search(keyword, page, rows);
            return DomiResult.ok(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }

    }
}
