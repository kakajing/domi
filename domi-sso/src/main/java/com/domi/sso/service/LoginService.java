package com.domi.sso.service;

import com.domi.pojo.DomiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author 卡卡
 * Created by jing on 2016/12/6.
 */
public interface LoginService {
    DomiResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);
    DomiResult getUserByToken(String token);
    DomiResult loginoutByToken(String token);
}
