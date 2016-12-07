package com.domi.order.service;

import com.domi.order.pojo.OrderInfo;
import com.domi.pojo.DomiResult;

/**
 * Author 卡卡
 * Created by jing on 2016/12/8.
 */
public interface OrderService {
    DomiResult createOreder(OrderInfo orderInfo);
}
