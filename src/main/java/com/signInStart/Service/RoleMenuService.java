/**
 * created by
 * Date:2019/4/15
 **/
package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.Menu;
import com.signInStart.Entity.RoleMenu;

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

    RoleMenu findByMenuIdUserType(Long id, String userType) throws FriendlyException;

    Integer deleteByMenuIdRoleId(Long id, Long roleId) throws FriendlyException;

    Integer deleteByMenuIdUserType(Long id, String userType) throws FriendlyException;
}