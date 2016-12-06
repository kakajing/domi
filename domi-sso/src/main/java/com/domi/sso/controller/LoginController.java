package com.domi.sso.controller;

import com.domi.pojo.DomiResult;
import com.domi.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author 卡卡
 * Created by jing on 2016/12/6.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public DomiResult login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response){
        try {
            DomiResult result = loginService.login(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 根据token查询用户信息
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback){
        try {
            DomiResult result = loginService.getUserByToken(token);
            //支持jsonp调用
            if (StringUtils.isNotEmpty(callback)){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(callback);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 安全退出
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object loginoutByToken(@PathVariable String token, String callback){
        try {
            DomiResult result = loginService.loginoutByToken(token);
            if (StringUtils.isNotEmpty(callback)){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(callback);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return DomiResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }

}
