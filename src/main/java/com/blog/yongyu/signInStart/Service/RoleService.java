package com.blog.yongyu.signInStart.Service;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.Role;
import net.minidev.json.JSONObject;

import java.util.List;

public interface RoleService {
    Role findRoleById(Long id);

    List<JSONObject> findAll() throws FriendlyException;

    List<Role> findByRoleNames(String[] names);

    Integer Insert(Role role) throws FriendlyException;

    Integer Delete(Long roleId) throws FriendlyException;

    Integer modify(Role role) throws FriendlyException;
}