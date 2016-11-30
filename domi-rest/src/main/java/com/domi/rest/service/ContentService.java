package com.domi.rest.service;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbContent;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
public interface ContentService {
    List<TbContent> getContentList(Long cid);
    DomiResult synContent(Long cid);
}
