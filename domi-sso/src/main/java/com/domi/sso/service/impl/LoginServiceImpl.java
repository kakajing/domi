package com.domi.sso.service.impl;

import com.domi.mapper.TbUserMapper;
import com.domi.pojo.DomiResult;
import com.domi.pojo.TbUser;
import com.domi.pojo.TbUserExample;
import com.domi.sso.component.JedisClient;
import com.domi.sso.service.LoginService;
import com.domi.utils.CookieUtils;
import com.domi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.tftp.TFTPPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Author 卡卡
 * Created by jing on 2016/12/6.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;


    /**
     * 登录用户
     */
    @Override
    public DomiResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {

        //校验用户名是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<TbUser> list = userMapper.selectByExample(example);
        //取用户信息
        if (list == null || list.isEmpty()){
            return DomiResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);

        //校验密码
        if (user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return DomiResult.build(400,"用户名或密码错误");
        }
        //登录成功，生成token
        String token = UUID.randomUUID().toString();
        //把用户信息写入redis
        //key:REDIS_SESSION:{TOKEN}
        //value:user转json
        user.setPassword(null);
        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SESSION_EXPIRE);

        //写入cookie
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);

        return DomiResult.ok(token);
    }

    @Override
    public DomiResult getUserByToken(String token) {

        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        if (StringUtils.isEmpty(json)){
            return DomiResult.build(400, "用户session已经过期");
        }
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SESSION_EXPIRE);

        return DomiResult.ok(user);
    }

    @Override
    public DomiResult loginoutByToken(String token) {
        jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
        return DomiResult.ok();
    }
}
