package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.Role;

public interface RoleService {
    Role findRoleById(Long id);

    Integer addRole(Role role);

    Integer removeRole(Long roleId);

    Integer modifyRole(Role role);
}