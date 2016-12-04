package com.domi.portal.service;

import com.domi.pojo.TbItem;

/**
 * Author 卡卡
 * Created by jing on 2016/12/4.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParamById(Long itemId);
}
