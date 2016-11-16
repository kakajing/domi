package com.domi.service;

import com.domi.pojo.EasyUITreeNode;

import java.util.List;

/**
 *
 * Created by jing on 2016/11/15.
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(long parentId);
}
