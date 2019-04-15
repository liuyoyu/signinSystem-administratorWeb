/**
 * created by
 * Date:2019/4/15
 **/
package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.Menu;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.RoleMenu;
import com.blog.yongyu.demo.Repository.RoleMenuRepository;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.RoleMenuService;
import com.blog.yongyu.demo.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    RoleMenuRepository roleMenuRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    LoginInfoService loginInfoService;

    @Override
    public RoleMenu findById(Long id) {
        Optional<RoleMenu> byId = roleMenuRepository.findById(id);
        if (!byId.isPresent()) {
            return null;
        }
        return byId.get();
    }

    @Override
    public List<RoleMenu> findAll() {
        List<RoleMenu> all = findAll();
        if (all == null) {
            return null;
        }
        return all;
    }

    @Override
    public Integer add(RoleMenu menu) {
        if (menu == null) {
            return 1;
        }
        roleMenuRepository.save(menu);
        return 0;
    }

    @Override
    public Integer remove(Long id) {
        RoleMenu byId = findById(id);
        if (byId == null) {
            return 1;
        }
        roleMenuRepository.delete(byId);
        return 0;
    }

    @Override
    public Integer modify(RoleMenu menu) {
        if (menu == null) {
            return 1;
        }
        roleMenuRepository.save(menu);
        return 0;
    }

    @Override
    public Integer add(Menu menu, Long roleId) {
        if (menu == null || roleId == null) {
            return 1; //不能传入空值
        }
        Role roleById = roleService.findRoleById(roleId);
        if (roleById == null) {
            return 2; //必须赋予角色
        }
        RoleMenu rm = new RoleMenu();
        rm.setRole(roleById);
        rm.setMenu(menu);
        rm.setCreateDate(new Date());
        rm.setCreateBy(loginInfoService.getAccount());
        add(rm);
        return 0;
    }

    @Override
    public List<RoleMenu> findRoleMenuByMenuId(Long id) {
        List<RoleMenu> roleMenuByRoleId = roleMenuRepository.findRoleMenuByRoleId(id);
        if (roleMenuByRoleId == null || roleMenuByRoleId.size() < 1) {
            return null;
        }
        return roleMenuByRoleId;
    }
}