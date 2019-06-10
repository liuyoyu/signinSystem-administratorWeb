/**
 * created by
 * Date:2019/4/3
 **/
package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.BaseSetting;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.UserRole;
import com.signInStart.Repository.UserRoleRepository;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    LoginInfoService loginInfoService;

    @Override
    public UserRole findById(Long id) {
        if (id == null) {
            return null;
        }
        Optional<UserRole> byId = userRoleRepository.findById(id);
        if (byId.get() == null) {
            return null;
        }
        return byId.get();
    }

    @Override
    public Integer addUserRole(UserRole userRole)throws FriendlyException {
        if (userRole == null) {
            throw new FriendlyException("对象不存在");
        }
        if (userRole.getUserInfo() == null) {
            throw new FriendlyException("用户不存在");
        }
        if (userRole.getRole() == null) {
            throw new FriendlyException("角色不存在");
        }
        if (userRole.getUserType().equals(loginInfoService.getCurrUserType())) {
            throw new FriendlyException("管理员间不能相互操作");
        }
        if (userRoleRepository.findByUserIdRoleId(userRole.getUserId(), userRole.getRoleId())!=null) {
            return 0;
        }
        userRole.setCreateDate(new Date());
        userRole.setCreateBy(loginInfoService.getAccount());
        userRoleRepository.save(userRole);
        return 0;
    }

    @Override
    public Integer removeUserRole(Long id) {
        UserRole userRole = findById(id);
        if (id == null || userRole == null) {
            return 1; // 对象不存在
        }
        userRoleRepository.delete(userRole);
        return 0;
    }

    /**
     * 删除用户的所有角色
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Integer removeAllUserRoleByUserId(Long userId) {
        if (userId == null) {
            return 1; // 对象不存在
        }
        userRoleRepository.deleteAllByUserId(userId);
        return 0;
    }

    @Override
    public Integer modifyUserRole(UserRole userRole) {
        if (userRole == null || findById(userRole.getId()) == null) {
            return 1; // 对象不存在
        }
        userRoleRepository.save(userRole);
        return 0;
    }

    /**
     * 判断是否有管理员或超管身份
     * @param userId
     * @return
     */
    @Override
    public Boolean isAdmin(Long userId) {
        List<UserRole> allByUserId = userRoleRepository.findAllByUserId(userId);
        for (UserRole userrole : allByUserId) {
            if (userrole.getUserType().equals(BaseSetting.ROLE.Admin_SYS.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否有超级管理员的身份
     * @param userId
     * @return
     */
    @Override
    public Boolean isSupperAdmin(Long userId) {
        List<UserRole> allByUserId = userRoleRepository.findAllByUserId(userId);
        for (UserRole userrole : allByUserId) {
            if (userrole.getUserType().equals(BaseSetting.ROLE.SupperAdmin_SYS.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找用户默认角色
     * @param userId
     * @return
     */
    @Override
    public UserRole findDefaultRoleByUserId(Long userId) {
        List<UserRole> allByUserId = userRoleRepository.findAllByUserId(userId);
        for (UserRole userRole : allByUserId) {
            if (userRole.getIsDefault().equals(BaseSetting.ISDEFAULT.isDefault_SYS.toString())) {
                return userRole;
            }
        }
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public List<UserRole> findByUserID(Long id) {
        return userRoleRepository.findByUserId(id);
    }
}