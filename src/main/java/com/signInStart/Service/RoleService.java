package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.Role;
import net.minidev.json.JSONObject;

import java.util.List;

public interface RoleService {
    Role findRoleById(Long id);

    List<JSONObject> findAll() throws FriendlyException;

    List<Role> findByRoleNames(String[] names);

    List<Role> findByUserType(String userType);

    Integer Insert(Role role) throws FriendlyException;

    Integer Delete(Long roleId) throws FriendlyException;

    Integer modify(Role role) throws FriendlyException;

    List<Role> getAll() throws FriendlyException;
}