package com.domi.rest.service;

import com.domi.pojo.TbItem;
import com.domi.pojo.TbItemDesc;
import com.domi.pojo.TbItemParamItem;

/**
 * Author 卡卡
 * Created by jing on 2016/12/4.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);
    TbItemParamItem getItemParamById(Long itemId);
}
