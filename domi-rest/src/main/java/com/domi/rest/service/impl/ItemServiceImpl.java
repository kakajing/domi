package com.domi.rest.service.impl;

import com.domi.mapper.TbItemDescMapper;
import com.domi.mapper.TbItemMapper;
import com.domi.mapper.TbItemParamItemMapper;
import com.domi.pojo.TbItem;
import com.domi.pojo.TbItemDesc;
import com.domi.pojo.TbItemParamItem;
import com.domi.pojo.TbItemParamItemExample;
import com.domi.rest.component.JedisClient;
import com.domi.rest.service.ItemService;
import com.domi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Author 卡卡
 * Created by jing on 2016/12/4.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;

    /**
     * 商品基本信息服务
     */
    @Override
    public TbItem getItemById(Long itemId) {

        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId);
            //判断数据是否存在
            if (StringUtils.isNotBlank(json)){
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据商品id查询商品基本信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //向redis中添加缓存。
        //添加缓存原则是不能影响正常的业务逻辑
        try {
            //向redis中添加缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId,
                    JsonUtils.objectToJson(item));
            //设置key的过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" +itemId,
                    ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * 商品描述服务
     */
    @Override
    public TbItemDesc getItemDescById(Long itemId) {

        //查询缓存，如果有缓存，直接返回
        String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_DESC_KEY + ":" + itemId);
        if (StringUtils.isNotBlank(json)){
            TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
            return itemDesc;
        }

        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        //向redis中添加缓存。
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_DESC_KEY + ":" + itemId,
                    JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_KEY + ":" +ITEM_DESC_KEY +":" + itemId,
                    ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

    /**
     * 商品规格参数服务
     */
    @Override
    public TbItemParamItem getItemParamById(Long itemId) {

        //添加缓存逻辑
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId);
            //判断数据是否存在
            if (StringUtils.isNotBlank(json)){
                //把json数据转换成java对象
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return itemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        //取规格参数
        if (list != null && list.size() > 0){
            TbItemParamItem itemParamItem = list.get(0);
            try {
                //添加缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId,
                        JsonUtils.objectToJson(itemParamItem));
                //设置key的过期时间
                jedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId, ITEM_EXPIRE_SECOND);
            }catch (Exception e){
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }

}
