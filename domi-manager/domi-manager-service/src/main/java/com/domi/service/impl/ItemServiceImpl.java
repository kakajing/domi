package com.domi.service.impl;

import com.domi.mapper.TbItemDescMapper;
import com.domi.mapper.TbItemMapper;
import com.domi.mapper.TbItemParamItemMapper;
import com.domi.pojo.*;
import com.domi.service.ItemService;
import com.domi.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品查询Service
 * Created by jing on 2016/11/15.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    /**
     * 根据商品id查询
     */
    @Override
    public TbItem getItemById(Long itemId) {

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        //创建查询条件
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(example);
        //判断list中是否为空
        TbItem item = null;
        if (list != null && list.size() > 0){
            item = list.get(0);
        }
        return item;
    }

    /**
     * 商品列表查询
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);

        //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public DomiResult createItem(TbItem item, String desc, String itemParam) {

        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全TbItem属性
        item.setId(itemId);
        // '商品状态，1-正常，2-下架，3-删除'
        item.setStatus((byte) 1);
        // 创建时间和更新时间
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入商品表
        itemMapper.insert(item);

        //商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //插入商品描述数据
        itemDescMapper.insert(itemDesc);

        ////添加商品规格参数处理
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //插入商品规格参数数据
        itemParamItemMapper.insert(itemParamItem);

        return DomiResult.ok();
    }

    /**
     * 添加商品描述
     */
    public DomiResult insertItemDesc(Long itemId, String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());

        return DomiResult.ok();
    }

    /**
     * 添加规格参数
     */
    public DomiResult insertItemParamItem(Long itemId, String itemParam){
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());

        return DomiResult.ok();
    }
}
