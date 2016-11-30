package com.domi.test;

import com.domi.rest.component.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author 卡卡
 * Created by jing on 2016/11/29.
 */
public class JedisClientTest {

    @Test
    public void testJedisClient() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");

        JedisClient jedisClient = context.getBean(JedisClient.class);

        jedisClient.set("client2","200");
        String string = jedisClient.get("client2");
        System.out.println(string);

    }
}
