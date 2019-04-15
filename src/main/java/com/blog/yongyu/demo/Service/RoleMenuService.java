/**
 * created by
 * Date:2019/4/15
 **/
package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.Menu;
import com.blog.yongyu.demo.Entity.RoleMenu;

import java.util.List;

public interface RoleMenuService {

    RoleMenu findById(Long id);

    List<RoleMenu> findAll();

    Integer add(RoleMenu menu);

    Integer remove(Long id);

    Integer modify(RoleMenu menu);

    Integer add(Menu menu, Long roleId);

    List<RoleMenu> findRoleMenuByMenuId(Long id);
}