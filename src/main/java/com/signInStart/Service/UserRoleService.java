/**
 * created by
 * Date:2019/4/3
 **/
package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.UserRole;

import java.util.List;


public interface UserRoleService {

    UserRole findById(Long id)throws FriendlyException;

    UserRole findDefaultRoleByUserId(Long userId)throws FriendlyException ;

    List<UserRole> findAll()throws FriendlyException ;

    Boolean isAdmin(Long userId)throws FriendlyException ;

    Boolean isSupperAdmin(Long userId)throws FriendlyException ;

    Integer addUserRole(UserRole userRole)throws FriendlyException ;

    Integer removeUserRole(Long id)throws FriendlyException ;

    Integer removeAllUserRoleByUserId(Long userId)throws FriendlyException ;

    Integer modifyUserRole(UserRole userRole)throws FriendlyException ;

    List<UserRole> findByUserID(Long id)throws FriendlyException ;

    void editRole(Long userID, Long roleID, Long newID) throws FriendlyException;
}