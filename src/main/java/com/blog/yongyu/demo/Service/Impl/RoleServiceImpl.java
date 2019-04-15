package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.BaseClass.BaseRole;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Repository.RoleRepository;
import com.blog.yongyu.demo.Service.RoleService;
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
        role.setCreateDate(new Date());
        role.setModifyDate(new Date());
        roleRepository.save(role);
        return 0;
    }

    @Override
    public Integer Delete(Long roleId) {
        Role role = findRoleById(roleId);
        if (role == null) {
            return 1;//删除对象不存在
        }
        if (Objects.equals(roleId, BaseRole.AdminId) || Objects.equals(roleId, BaseRole.UserId)) {
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
        roleRepository.save(role);
        return 0;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}