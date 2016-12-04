package com.domi.sso.service;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbUser;

/**
 * Author 卡卡
 * Created by jing on 2016/12/5.
 */
public interface RegisterService {
    DomiResult checkData(String param, int type);
    DomiResult register(TbUser user);
}
