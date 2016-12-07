package com.domi.order.service.impl;

import com.domi.mapper.TbOrderItemMapper;
import com.domi.mapper.TbOrderMapper;
import com.domi.mapper.TbOrderShippingMapper;
import com.domi.order.component.JedisClient;
import com.domi.order.pojo.OrderInfo;
import com.domi.order.service.OrderService;
import com.domi.pojo.DomiResult;
import com.domi.pojo.TbOrderItem;
import com.domi.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/8.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Value("${REDIS_ORDER_GEN_KEY}")
    private String REDIS_ORDER_GEN_KEY;
    @Value("${ORDER_ID_BEGIN}")
    private String ORDER_ID_BEGIN;
    @Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
    private String REDIS_ORDER_DETAIL_GEN_KEY;


    @Autowired
    private JedisClient jedisClient;

    @Override
    public DomiResult createOreder(OrderInfo orderInfo) {

        //插入订单表
        //取订单号
        String id = jedisClient.get(REDIS_ORDER_GEN_KEY);
        if (StringUtils.isBlank(id)){
            //如果订单号生成key不存在设置初始值
            jedisClient.set(REDIS_ORDER_GEN_KEY, ORDER_ID_BEGIN);
        }
        Long orderId = jedisClient.incr(REDIS_ORDER_GEN_KEY);
        //补全字段
        orderInfo.setOrderId(orderId.toString());
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());

        orderMapper.insert(orderInfo);

        //插入订单明细
        //补全字段
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            // 1、生成订单明细id，使用redis的incr命令生成
            Long detailId = jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
            orderItem.setId(detailId.toString());
            //订单号
            orderItem.setId(orderId.toString());
            //插入数据
            orderItemMapper.insert(orderItem);
        }

        //插入物流表
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());

        // 插入数据
        orderShippingMapper.insert(orderShipping);
        // 返回DomiResult装订单号
       return DomiResult.ok(orderId);
    }
}
