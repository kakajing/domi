package com.domi.search.dao.impl;

import com.domi.search.dao.SearchDao;
import com.domi.search.pojo.SearchItem;
import com.domi.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * solr查询
 * Author 卡卡
 * Created by jing on 2016/12/3.
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery query) throws Exception {

        QueryResponse response = solrServer.query(query);

        SolrDocumentList solrDocumentList = response.getResults();
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem item = new SearchItem();
           item.setCategory_name((String) solrDocument.get("item_category_name" ));
            item.setId((String) solrDocument.get("id"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));

            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0){
                //取高亮后的结果
                itemTitle = list.get(0);
            }else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            item.setTitle(itemTitle);
            //添加到列表
            itemList.add(item);
        }
        SearchResult result = new SearchResult();
        result.setItemList(itemList);
        //查询结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());

        return result;
    }
}
