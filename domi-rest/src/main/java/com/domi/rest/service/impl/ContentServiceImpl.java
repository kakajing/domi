package com.domi.rest.service.impl;

import com.domi.mapper.TbContentMapper;
import com.domi.pojo.DomiResult;
import com.domi.pojo.TbContent;
import com.domi.pojo.TbContentExample;
import com.domi.rest.component.JedisClient;
import com.domi.rest.service.ContentService;
import com.domi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容管理
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 发布内容查询服务
     */
    @Override
    public List<TbContent> getContentList(Long cid) {
        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
        try {
            //从redis中取缓存数据
            String json = jedisClient.hget(REDIS_CONTENT_KEY, cid + "");
            if (!StringUtils.isBlank(json)){
                //把json转换成List
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据cid查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);

        //返回结果之前，向缓存中添加数据
        try {
            //为了规范key可以使用hash
            //定义一个保存内容的key，hash中每个项就是cid
            //value是list，需要把list转换成json数据
            jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 缓存同步
     * @param cid
     * @return
     */
    @Override
    public DomiResult synContent(Long cid) {
       jedisClient.hdel(REDIS_CONTENT_KEY, cid + "");

        return DomiResult.ok();
    }

}
