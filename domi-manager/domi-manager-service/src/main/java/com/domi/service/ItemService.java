package com.domi.service;

import com.domi.pojo.DomiResult;
import com.domi.pojo.EasyUIDataGridResult;
import com.domi.pojo.TbItem;

/**
 *
 * Created by jing on 2016/11/15.
 */
public interface ItemService {

    TbItem getItemById(Long itemId);
    EasyUIDataGridResult getItemList(int page, int rows);
    DomiResult createItem(TbItem item, String desc, String itemParam);
}
