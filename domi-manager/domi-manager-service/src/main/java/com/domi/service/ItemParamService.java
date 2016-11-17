package com.domi.service;

import com.domi.pojo.DomiResult;

/**
 *
 * Created by jing on 2016/11/17.
 */
public interface ItemParamService {

    DomiResult getItemParamByCid(Long cid);
    DomiResult insertItemParam(Long cid, String paramData);
    DomiResult deleteItemParam(Long cid);
}
