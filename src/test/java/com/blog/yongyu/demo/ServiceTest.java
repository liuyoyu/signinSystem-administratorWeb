package com.blog.yongyu.demo;

import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {


    @Autowired
    RoleService roleService;
    @Test
    public void addRoleTest(){
        Role role = new Role();
        role.setRoleName("教师");
        role.setRoleId("002");
        Long time = System.currentTimeMillis();
        role.setCreateTime(new Date(time));
        role.setModifyTime(new Date(time));
        role.setCreatorName("liuyoyu");
        role.setMenderName("liuyoyu");
        role.setDetail("基本教师角色");

        Integer res = roleService.addRole(role);
        System.out.println(res);
    }
}