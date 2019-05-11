package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.BaseClass.FriendLyException;
import com.blog.yongyu.demo.Entity.Role;

import java.util.List;

public interface RoleService {
    Role findRoleById(Long id);

    List<Role> findAll();

    List<Role> findByRoleNames(String[] names);

    Integer Insert(Role role) throws FriendLyException;

    Integer Delete(Long roleId) throws FriendLyException;

    Integer modify(Role role) throws FriendLyException;
}