/**
 * created by
 * Date:2019/4/15
 **/
package com.blog.yongyu.signInStart.Service;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.Menu;
import com.blog.yongyu.signInStart.Entity.RoleMenu;

import java.util.List;

public interface RoleMenuService {

    RoleMenu findById(Long id)throws FriendlyException;

    List<RoleMenu> findAll()throws FriendlyException ;

    Integer add(RoleMenu menu)throws FriendlyException ;

    Integer remove(Long id)throws FriendlyException ;

    Integer modify(RoleMenu menu)throws FriendlyException ;

    Integer modify(Menu menu, String[] roleNames)throws FriendlyException ;

    Integer add(Menu menu, Long roleId)throws FriendlyException ;

    Integer add(Menu menu, String[] roleNames)throws FriendlyException ;

    List<RoleMenu> findRoleMenuByMenuId(Long id)throws FriendlyException ;
}