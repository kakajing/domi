package com.domi.portal.service.impl;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbItem;
import com.domi.portal.pojo.CartItem;
import com.domi.portal.service.CartService;
import com.domi.portal.service.ItemService;
import com.domi.utils.CookieUtils;
import com.domi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/7.
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;


    @Override
    public DomiResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {

        //从cookie中购物车商品列表
        List<CartItem> list = getCartItemList(request);
        //从商品列表中查询列表是否存在此商品
        boolean haveFlg = false;
        for (CartItem cartItem : list) {
            if (cartItem.getId().longValue() == itemId){
                //如果存在商品的数量加上参数中的商品数量
                cartItem.setNum(cartItem.getNum() + num);
                haveFlg = true;
                break;
            }
        }

        //如果不存在，调用rest服务，根据商品id获得商品数据
        if (!haveFlg){
            TbItem item = itemService.getItemById(itemId);
            CartItem cartItem = new CartItem();
            cartItem.setId(itemId);
            cartItem.setNum(num);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if (StringUtils.isNotBlank(item.getImage())){
                String image = item.getImage();
                String[] strings = image.split(",");
                cartItem.setImage(strings[0]);
            }
            //添加到购物车商品列表
            list.add(cartItem);
        }

        //把购物车商品列表写入cookie
        CookieUtils.setCookie(request, response,
                "TT_CART", JsonUtils.objectToJson(list),
                COOKIE_EXPIRE, true);
        return DomiResult.ok();
    }

    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {

        List<CartItem> itemList = getCartItemList(request);
        return itemList;
    }

    @Override
    public DomiResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //根据商品id查询商品
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getId() == itemId){
                //更新数量
                cartItem.setNum(num);
                break;
            }
        }

        CookieUtils.setCookie(request, response,
                "TT_CART", JsonUtils.objectToJson(cartItemList),
                COOKIE_EXPIRE, true);

        return DomiResult.ok();
    }

    @Override
    public DomiResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {

        List<CartItem> itemList = getCartItemList(request);
        for (CartItem cartItem : itemList) {
            if (cartItem.getId() == itemId){
                itemList.remove(cartItem);
                break;
            }
        }

        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList),
                COOKIE_EXPIRE, true);
        return DomiResult.ok();
    }

    /**
     * 取购物车商品列表
     * @param request
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request){
        try {
            //从cookie中取商品列表
            String json = CookieUtils.getCookieValue(request, "TT_CART", true);
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return list == null?new ArrayList<CartItem>():list;
        } catch (Exception e) {
            return new ArrayList<CartItem>();
        }
    }
}
