package com.domi.order.pojo;

import com.domi.pojo.TbOrder;
import com.domi.pojo.TbOrderItem;
import com.domi.pojo.TbOrderShipping;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/8.
 */
public class OrderInfo extends TbOrder {

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
