package com.domi.rest.service.impl;

import com.domi.mapper.TbItemCatMapper;
import com.domi.pojo.TbItemCat;
import com.domi.pojo.TbItemCatExample;
import com.domi.rest.pojo.CatNode;
import com.domi.rest.pojo.ItemCatResult;
import com.domi.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类服务
 * Author 卡卡
 * Created by jing on 2016/11/19.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {

        //调用递归方法查询商品分类列表
        List catList = getItemCatList(0L);
        //返回结果
        ItemCatResult result = new ItemCatResult();
        result.setData(catList);
        return result;
    }

    /**
     * 查询分类列表
     * 使用了递归方法
     */
    private List getItemCatList(Long parentId){
        //根据parentId查询列表
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);

        List resultList = new ArrayList<>();
        int index = 0;
        for (TbItemCat tbItemCat : list) {
            if (index >= 14){
                break;
            }
            //如果是父节点
            if (tbItemCat.getIsParent()) {
                CatNode node = new CatNode();
                node.setUrl("/products/" + tbItemCat.getId() + ".html");
                //如果当前节点为第一级节点
                if (tbItemCat.getParentId() == 0) {
                    node.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                    index++;
                } else {
                    node.setName(tbItemCat.getName());
                }
                node.setItems(getItemCatList(tbItemCat.getId()));
                //把node添加到列表
                resultList.add(node);

            }else {
                //如果是叶子节点
                String item = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                resultList.add(item);
            }
        }
        return resultList;
    }
}
