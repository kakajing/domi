package com.domi.portal.pojo;

import com.domi.pojo.TbItem;

/**
 * Author 卡卡
 * Created by jing on 2016/12/4.
 */
public class PortalItem extends TbItem {
    public String[] getImages(){
        String images = this.getImage();
        if (images != null && !images.equals("")){
            String[] imgs = images.split(",");
            return imgs;
        }
        return null;
    }
}
