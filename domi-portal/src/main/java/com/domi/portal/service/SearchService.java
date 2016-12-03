package com.domi.portal.service;

/**
 * Author 卡卡
 * Created by jing on 2016/12/3.
 */
public interface SearchService {
    com.domi.portal.pojo.SearchResult search(String keyword, int page, int rows);
}
