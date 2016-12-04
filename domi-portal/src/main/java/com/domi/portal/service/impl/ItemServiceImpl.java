package com.domi.portal.service.impl;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbItem;
import com.domi.pojo.TbItemDesc;
import com.domi.pojo.TbItemParamItem;
import com.domi.portal.pojo.PortalItem;
import com.domi.portal.service.ItemService;
import com.domi.utils.HttpClientUtil;
import com.domi.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author 卡卡
 * Created by jing on 2016/12/4.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;
    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;
    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    @Override
    public TbItem getItemById(Long itemId) {

        // 根据商品id查询商品基本信息
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
        DomiResult domiResult = DomiResult.formatToPojo(json, PortalItem.class);
        TbItem item = (TbItem) domiResult.getData();
        return item;
    }

    @Override
    public String getItemDescById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
        DomiResult domiResult = DomiResult.formatToPojo(json, TbItemDesc.class);
        TbItemDesc itemDesc = (TbItemDesc) domiResult.getData();
        String desc = itemDesc.getItemDesc();
        return desc;
    }

    @Override
    public String getItemParamById(Long itemId) {

        // 根据商品id获得对应的规格参数
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
        DomiResult domiResult = DomiResult.formatToPojo(json, TbItemParamItem.class);
        TbItemParamItem itemParamItem = (TbItemParamItem) domiResult.getData();
        // 取规格参数
        String paramJson = itemParamItem.getParamData();

        // 把规格参数的json数据转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);

        // 遍历list生成html
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");
        for (Map map : mapList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("		</tr>\n");
            // 取规格项
            List<Map> mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("		</tr>\n");
            }
        }
        sb.append("	</tboy>\n");
        sb.append("</table>");

        return sb.toString();
    }
}
