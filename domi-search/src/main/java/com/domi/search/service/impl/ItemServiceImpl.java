package com.domi.search.service.impl;

import com.domi.pojo.DomiResult;
import com.domi.search.mapper.ItemMapper;
import com.domi.search.pojo.SearchItem;
import com.domi.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/11/30.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public DomiResult importItems() throws Exception {
        //查询数据库获得商品列表
        List<SearchItem> itemList = itemMapper.getItemList();

        for (SearchItem item : itemList) {
            SolrInputDocument  document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_des());

            solrServer.add(document);
        }
        solrServer.commit();
        return DomiResult.ok();
    }
}
