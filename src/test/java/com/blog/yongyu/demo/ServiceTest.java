package com.blog.yongyu.demo;

import com.blog.yongyu.demo.Entity.BaseClass.BaseRole;
import com.blog.yongyu.demo.Entity.Dictionary;
import com.blog.yongyu.demo.Entity.Menu;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.*;
import com.blog.yongyu.demo.Utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        UserInfo userById = userInfoService.findUserById(Long.parseLong("7"));
        Integer res = userInfoService.Delete(userById.getId());
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
//        Long key11 = RedisUtils.del("key1");
//        System.out.println(key11);
    }

    @Test
    public void userInfoTest(){
//        Optional<UserInfo> userById = userInfoService.findUserById(Long.parseLong("7"));
//        userById.get().setEmail("l673677179@163.com");
//        Integer res = userInfoService.modifyUserInfo(userById.get());
//        System.out.println(res);
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("673677179@qq.com");
        userInfo.setAccount("root");
        userInfo.setInitPassword();
        userInfo.setUserName("管理员");

        Integer user = userInfoService.Insert(userInfo);
        System.out.println(user);
    }
    @Autowired
    DictionaryService dictionaryService;
    @Test
    public void dictionaryTest(){
        Dictionary dc = new Dictionary();
        dc.setDataKey("lyy");
        dictionaryService.insert(dc);
    }

    @Autowired
    UserInfoRepository userInfoRepository;
    @Test
    public void loginTest(){
        UserInfo userByAccountOrEmail = userInfoRepository.findUserByAccountOrEmail("root");
        System.out.println(userByAccountOrEmail.getId());
    }

    @Test
    public void resetPwd() {
        userInfoService.allResetPwd(new Long[]{11L,18L});

    }
    @Test
    public void mapTest(){
        HashMap<String, String> map = new HashMap<>();
        System.out.println(map.get("123"));
    }
    @Autowired
    MenuService menuService;
    @Test
    public void loginInfoTest(){
//        Menu m = new Menu();
//        m.setMenuName("test");
//        Integer res = menuService.add(m);
//        System.out.println(res);
        Menu byId = menuService.findById(21L);
        System.out.println(byId.getId());
        byId.setMenuName("lyy_test");
        menuService.modify(byId);
    }

//    @Test
//    public roleTest(){
//        Role role = new Role();
//        role.setRoleName(BaseRole.User);
//        role.setParentRole(0L);
//        roleService.Insert()
//    }

}