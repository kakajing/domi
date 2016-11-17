package com.domi.service.impl;

import com.domi.mapper.TbItemParamMapper;
import com.domi.pojo.DomiResult;
import com.domi.pojo.TbItemParam;
import com.domi.pojo.TbItemParamExample;
import com.domi.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品规格参数模板
 * Created by jing on 2016/11/17.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;


    @Override
    public DomiResult getItemParamByCid(Long cid) {
        //根据cid查询规格参数模板
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(cid);
        //执行查询
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list != null && list.size() > 0){
            TbItemParam itemParam = list.get(0);
            return DomiResult.ok(itemParam);
        }
        return DomiResult.ok();

    }

    /**
     * 提交规格参数模板
     * @param cid
     * @param paramData
     * @return
     */
    @Override
    public DomiResult insertItemParam(Long cid, String paramData) {
        //创建一个pojo
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());

        //插入记录
        itemParamMapper.insert(itemParam);
        return DomiResult.ok();
    }

    /**
     * 功能未实现
     */
    @Override
    public DomiResult deleteItemParam(Long cid) {
       TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(cid);
        //执行查询
        itemParamMapper.deleteByExample(example);
        return DomiResult.ok();
    }
}
