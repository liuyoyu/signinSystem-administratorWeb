package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.Role;

public interface RoleService {
    Role findRoleById(String id);

    Integer addRole(Role role);

    Integer removeRole(String roleId);
}