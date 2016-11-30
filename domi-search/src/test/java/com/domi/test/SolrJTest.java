package com.domi.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Author 卡卡
 * Created by jing on 2016/11/30.
 */
public class SolrJTest {

    @Test
    public void testSorlJ() throws Exception {

        SolrServer solrServer = new HttpSolrServer("http://192.168.154.130:8080/solr");
        SolrInputDocument document = new SolrInputDocument();

        document.addField("id", "solrtest02");
        document.addField("item_title", "测试服务");
        document.addField("item_sell_point", "服务点");

        solrServer.add(document);

        solrServer.commit();
    }

    @Test
    public void testQuery() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.154.130:8080/solr");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");

        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));

        }

    }

}
