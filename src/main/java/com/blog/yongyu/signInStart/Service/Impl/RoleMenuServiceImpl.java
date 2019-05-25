/**
 * created by
 * Date:2019/4/15
 **/
package com.blog.yongyu.signInStart.Service.Impl;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.Menu;
import com.blog.yongyu.signInStart.Entity.Role;
import com.blog.yongyu.signInStart.Entity.RoleMenu;
import com.blog.yongyu.signInStart.Repository.RoleMenuRepository;
import com.blog.yongyu.signInStart.Service.LoginInfoService;
import com.blog.yongyu.signInStart.Service.RoleMenuService;
import com.blog.yongyu.signInStart.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 修改菜单角色；先删除已存在的菜单角色，再添加新的菜单角色
     * roleNames为空时，则只删除已有角色菜单
     * @param menu
     * @param roleNames
     * @return
     */
    @Transactional
    @Override
    public Integer modify(Menu menu, String[] roleNames)throws FriendlyException {
        if (menu == null) {
            return 1;//菜单为空
        }
//        if (roleNames == null || roleNames.length < 1) {
//            return 1; //角色为空
//        }
        roleMenuRepository.deleteByMenuID(menu.getId());    //删除菜单角色
        List<Role> byRoleNames = roleService.findByRoleNames(roleNames);
        for (Role r : byRoleNames) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(r);
            roleMenu.setMenu(menu);
            roleMenu.setModifyBy(loginInfoService.getAccount());
            roleMenu.setModifyDate(new Date());
            add(roleMenu);
        }
        return 0;
    }

    @Override
    public Integer add(Menu menu, Long roleId)throws FriendlyException  {
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


    /**
     * 根据用户角色名称添加权限
     *
     * @param menu
     * @param roleNames
     * @return
     */
    @Override
    public Integer add(Menu menu, String[] roleNames)throws FriendlyException  {
        if (menu == null) {
            return 1;//菜单为空
        }
        if (roleNames == null || roleNames.length < 1) {
            return 1; //角色为空
        }

        List<Role> byRoleNames = roleService.findByRoleNames(roleNames);
        for (Role role : byRoleNames) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenu(menu);
            roleMenu.setRole(role);
            roleMenu.setCreateDate(new Date());
            roleMenu.setCreateBy(loginInfoService.getAccount());
            add(roleMenu);
        }
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