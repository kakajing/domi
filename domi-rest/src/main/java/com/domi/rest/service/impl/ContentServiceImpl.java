package com.domi.rest.service.impl;

import com.domi.mapper.TbContentMapper;
import com.domi.pojo.TbContent;
import com.domi.pojo.TbContentExample;
import com.domi.rest.service.ContenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
@Service
public class ContentServiceImpl implements ContenService {

    @Autowired
    private TbContentMapper  contentMapper;

    @Override
    public List<TbContent> getContentList(Long cid) {

        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);

        return list;
    }
}
