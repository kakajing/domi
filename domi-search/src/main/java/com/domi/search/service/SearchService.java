package com.domi.search.service;

import com.domi.search.pojo.SearchResult;

/**
 * Author 卡卡
 * Created by jing on 2016/12/3.
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
