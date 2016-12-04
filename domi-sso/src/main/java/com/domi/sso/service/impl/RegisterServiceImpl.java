package com.domi.sso.service.impl;

import com.domi.mapper.TbUserMapper;
import com.domi.pojo.DomiResult;
import com.domi.pojo.TbUser;
import com.domi.pojo.TbUserExample;
import com.domi.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/5.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

   @Autowired
   private TbUserMapper userMapper;

    /**
     * 数据校验
     * @param param
     * @param type
     * @return
     */
    @Override
    public DomiResult checkData(String param, int type) {

        //根据数据类型检查数据
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1、2、3分别代表username、phone、email
        if (1 == type){
            criteria.andUsernameEqualTo(param);
        }else if (2 == type){
            criteria.andPhoneEqualTo(param);
        }else if (3 == type){
            criteria.andPasswordEqualTo(param);
        }

        List<TbUser> list = userMapper.selectByExample(example);
        //判断查询结果是否为空
        if (list == null || list.isEmpty()){
            return DomiResult.ok(true);
        }
        return DomiResult.ok(false);

    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public DomiResult register(TbUser user) {

       //校验数据
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())){
            return DomiResult.build(400, "用户名密码不能为空");
        }

        DomiResult result = checkData(user.getUsername(), 1);
        if (!(boolean)result.getData()){
            return DomiResult.build(400, "用户名重复");
        }

        if (user.getPhone() != null){
            result = checkData(user.getPhone(), 2);
            if (!(boolean)result.getData()){
                return DomiResult.build(400, "手机号重复");
            }
        }

        if (user.getEmail() != null){
            result = checkData(user.getEmail(), 3);
            if (!(boolean)result.getData()){
                return DomiResult.build(400, "邮箱重复");
            }
        }

        //插入数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        userMapper.insert(user);

        return DomiResult.ok();
    }
}
