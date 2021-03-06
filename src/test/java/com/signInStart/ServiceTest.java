package com.signInStart;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.BaseClass.MsgContent;
import com.signInStart.Entity.Organization;
import com.signInStart.Entity.RoleMenu;
import com.signInStart.Entity.UserInfo;
import com.signInStart.Repository.DictionaryRepository;
import com.signInStart.Repository.RoleMenuRepository;
import com.signInStart.Repository.UserInfoRepository;
import com.signInStart.Service.*;
import com.signInStart.Utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
//        UserInfo userById = userInfoService.findUserById(Long.parseLong("7"));
//        Integer res = userInfoService.Delete(userById.getId());
//        System.out.println(res);
    }

    @Autowired
    ShortMessageService shortMessageService;
    @Test
    public void emailTest(){
//        Integer integer = shortMessageService.sendEmailMessage("root", "673677179@qq.com", "找回密码");
//        Integer integer = shortMessageService.verifyEmailMessage("tbkmqJJkCo2JQhEW4EkSokqal", "root", "673677179@qq.com");
//        System.out.println(integer);
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
    public void userInfoTest() throws FriendlyException {
//        Optional<UserInfo> userById = userInfoService.findUserById(Long.parseLong("7"));
//        userById.get().setEmail("l673677179@163.com");
//        Integer res = userInfoService.modifyUserInfo(userById.get());
//        System.out.println(res);
//        UserInfo userInfo = new UserInfo();
//        userInfo.setEmail("673677179@qq.com");
//        userInfo.setAccount("root");
//        userInfo.setInitPassword();
//        userInfo.setUserName("管理员");

//        Integer user = userInfoService.Insert(userInfo);
//        System.out.println(user);

        //重置密码
        UserInfo u = userInfoService.findUserById(428L);
        u.setNewPassword("123456");
        userInfoRepository.save(u);
    }
    @Autowired
    DictionaryService dictionaryService;
    @Test
    public void dictionaryTest(){
//        Dictionary dc = new Dictionary();
//        dc.setDataKey("lyy");
//        dictionaryService.insert(dc);
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
//        userInfoService.allResetPwd(new Long[]{11L,18L});

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
//        Menu byId = menuService.findById(21L);
//        System.out.println(byId.getId());
//        byId.setMenuName("lyy_test");
//        menuService.modify(byId);
    }

//    @Test
//    public roleTest(){
//        Role role = new Role();
//        role.setRoleName(BaseRole.User);
//        role.setParentRole(0L);
//        roleService.Insert()
//    }

    @Autowired
    DictionaryRepository dicRepository;
    @Test()
    public void RepositoryTest(){
        List<Map<String, Object>> map = dicRepository.getDicIdValue();
        for (Map<String, Object> m : map) {
            System.out.println(m.get("id"));
        }
    }

    @Autowired
    OrganizationService organizationService;
    @Test()
    public void OrganizationTest(){
        Organization o = new Organization();
        try {
            organizationService.insert(o);
        } catch (Exception e) {
            System.out.println("------------");
            System.out.println("messsge: "+e.getMessage());
        }

    }

//    @Autowired
//    RoleMenuRepository roleMenuRepository;
//    @Test
//    public void repositoryTest(){
//        RoleMenu jk = roleMenuRepository.findByMenuIdUsetType(117L, "Admin_SYS");
//        System.out.println(jk.getId());
//    }

    @Test
    public void StringTest(){
        MsgContent m = new MsgContent("123","444");
        System.out.println(m.toString());
    }

    @Test
    public void RoleMenuRepositoryTest(){
        List<Map<String, String>> menuIdAndURLByRoleId = menuService.getMenuIdAndURLByRoleId(138L);
        System.out.println(menuIdAndURLByRoleId.size());
    }

}