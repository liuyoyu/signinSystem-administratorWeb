package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.BaseClass.BaseSetting;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Repository.RoleRepository;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LoginInfoService loginInfoService;

    @Override
    public Role findRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }

    @Override
    public Integer Insert(Role role) {
        if (role == null) {
            return 1;//角色不能为空
        }
        if (roleRepository.findByRoleName(role.getRoleName()) != null) {
            return 2; //角色名称重复
        }
        role.setCreateDate(new Date());
        role.setCreateBy(loginInfoService.getAccount());
        roleRepository.save(role);
        return 0;
    }

    @Override
    public Integer Delete(Long roleId) {
        Role role = findRoleById(roleId);
        if (role == null) {
            return 1;//删除对象不存在
        }
        if (BaseSetting.ROLE.Admin_SYS.toString().equals(role.getRoleName()) ||
                BaseSetting.ROLE.User_SYS.toString().equals(role.getRoleName()) ||
                BaseSetting.ROLE.SupperAdmin_SYS.toString().equals(role.getRoleName())) {
            return 2; //基本角色不能删除
        }
        roleRepository.delete(role);
        return 0;
    }

    @Override
    public Integer modify(Role role) {
        if (role == null) {
            return 1; // 修改对象不能为空
        }
        Role oldRole = findRoleById(role.getRoleId());
        if (oldRole == null) {
            return 2; // 修改对象不存在
        }
        oldRole.setModifyBy(loginInfoService.getAccount());
        oldRole.setModifyDate(new Date());
        DataUtils.copyProperty(role, oldRole);
        roleRepository.save(oldRole);
        return 0;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}