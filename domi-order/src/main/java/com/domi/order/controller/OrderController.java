package com.domi.order.controller;

import com.domi.order.pojo.OrderInfo;
import com.domi.order.service.OrderService;
import com.domi.pojo.DomiResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author 卡卡
 * Created by jing on 2016/12/8.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public DomiResult createOrder(@RequestBody OrderInfo orderInfo){

        try {
            DomiResult result = orderService.createOreder(orderInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

}
