package com.domi.portal.service;

import com.domi.pojo.DomiResult;
import com.domi.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/7.
 */
public interface CartService {
    DomiResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCartItems(HttpServletRequest request);
    DomiResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
    DomiResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
}
