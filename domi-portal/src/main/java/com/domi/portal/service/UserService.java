package com.domi.portal.service;

import com.domi.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author 卡卡
 * Created by jing on 2016/12/7.
 */
public interface UserService {
    TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response);
}
