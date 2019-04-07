package com.blog.yongyu.demo;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Service.ShortMessageService;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Service.UserRoleService;
import com.blog.yongyu.demo.Utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {


    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserInfoService userInfoService;
    @Test
    public void addRoleTest(){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setPwd("123");
//        userInfo.setAccount("root");
//        Integer user = userInfoService.createUser(userInfo);
        Optional<UserInfo> userById = userInfoService.findUserById(Long.parseLong("7"));
        if (userById.isPresent()) {
            System.out.println("1");
        }
        Integer res = userInfoService.removeUser(userById.get().getId());
        System.out.println(res);
    }

    @Autowired
    ShortMessageService shortMessageService;
    @Test
    public void emailTest(){
//        Integer integer = shortMessageService.sendEmailMessage("root", "673677179@qq.com", "找回密码");
        Integer integer = shortMessageService.verifyEmailMessage("tbkmqJJkCo2JQhEW4EkSokqal", "root", "673677179@qq.com");
        System.out.println(integer);
    }

    @Autowired
    RedisTemplate<String,Object> template;
    @Test
    public void testRedis(){
//       template.opsForValue().set("key1","value1");
//        System.out.println(template.opsForValue().get("key1"));
//        String res = RedisUtils.set("key1", "hello");
//        System.out.println(res);
        String key1 = RedisUtils.get("key1");
        System.out.println(key1);
        Long key11 = RedisUtils.del("key1");
        System.out.println(key11);
    }

    @Test
    public void userInfoTest(){
        Optional<UserInfo> userById = userInfoService.findUserById(Long.parseLong("7"));
        userById.get().setEmail("l673677179@163.com");
        Integer res = userInfoService.modifyUserInfo(userById.get());
        System.out.println(res);
    }

}