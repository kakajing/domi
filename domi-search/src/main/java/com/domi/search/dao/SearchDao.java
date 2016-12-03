package com.domi.search.dao;

import com.domi.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Author 卡卡
 * Created by jing on 2016/12/3.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws Exception;
}
