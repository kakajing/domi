package com.domi.test.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
public class HttpTest {

    @Test
    public void testHttpGet() throws Exception{
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpGet对象，需要制定一个请求的url
        HttpGet get = new HttpGet("http://www.itheima.com");
        //执行请求。
        CloseableHttpResponse response = httpClient.execute(get);
        //接收返回结果。HttpEntity对象。
        HttpEntity entity = response.getEntity();
        //取响应的内容。
        String html = EntityUtils.toString(entity);
        response.close();
        httpClient.close();
    }

    @Test
    public void testHttpPost() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
        //创建一个list模拟表单，list中每个元素是一个NameValuePair对象
        List<NameValuePair> formList = new ArrayList<>();
        formList.add(new BasicNameValuePair("name","张三"));
        formList.add(new BasicNameValuePair("pass","123"));
        //需要把表单包装到Entity对象中。StringEntity
        StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);

        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);

        System.out.println(result);

        response.close();
        httpClient.close();
    }
}
