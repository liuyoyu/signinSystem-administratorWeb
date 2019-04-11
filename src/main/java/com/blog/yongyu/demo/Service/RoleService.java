package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.Role;

import java.util.List;

public interface RoleService {
    Role findRoleById(Long id);

    List<Role> findAll();

    Integer Insert(Role role);

    Integer Delete(Long roleId);

    Integer modify(Role role);
}