package com.domi.portal.controller;

import com.domi.pojo.DomiResult;
import com.domi.portal.pojo.CartItem;
import com.domi.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/7.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num,
                          HttpServletRequest request, HttpServletResponse response){
        DomiResult result = cartService.addCart(itemId, num, request, response);
        return "cartSuccess";
    }


    /**
     * 展示购物车商品列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/cart")
    public String showCarts(HttpServletRequest request, Model model){
        List<CartItem> itemList = cartService.getCartItems(request);
        model.addAttribute("cartList", itemList);
        return "cart";
    }

    @RequestMapping("/update/num/{itemId}/{num}.action")
    public DomiResult updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num,
                                        HttpServletRequest request, HttpServletResponse response){
        DomiResult result = cartService.updateCartItem(itemId, num, request, response);
        return result;
    }


    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){
        DomiResult result = cartService.deleteCartItem(itemId, request, response);
        return "redirect:/cart/cart.html";
    }
}
