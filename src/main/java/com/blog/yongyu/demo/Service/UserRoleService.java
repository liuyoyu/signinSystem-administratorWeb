/**
 * created by
 * Date:2019/4/3
 **/
package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;

import java.util.List;


public interface UserRoleService {

    UserRole findById(Long id);

    UserRole findDefaultRoleByUserId(Long userId);

    List<UserRole> findAll();

    Boolean isAdmin(Long userId);

    Integer addUserRole(UserRole userRole);

    Integer removeUserRole(Long id);

    Integer removeAllUserRoleByUserId(Long userId);

    Integer modifyUserRole(UserRole userRole);
}