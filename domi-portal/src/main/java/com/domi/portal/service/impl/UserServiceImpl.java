package com.domi.portal.service.impl;

import com.domi.pojo.DomiResult;
import com.domi.pojo.TbUser;
import com.domi.portal.service.UserService;
import com.domi.utils.CookieUtils;
import com.domi.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author 卡卡
 * Created by jing on 2016/12/7.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_SERVICE}")
    private String SSO_USER_TOKEN_SERVICE;

    @Override
    public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            //从cookie中取token
            String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
            //判断token是否有值
            if (StringUtils.isEmpty(token)){
                return null;
            }
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
            DomiResult result = DomiResult.format(json);
            if (result.getStatus() != 200){
                return null;
            }
            result = DomiResult.formatToPojo(json, TbUser.class);
            TbUser user = (TbUser) result.getData();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
