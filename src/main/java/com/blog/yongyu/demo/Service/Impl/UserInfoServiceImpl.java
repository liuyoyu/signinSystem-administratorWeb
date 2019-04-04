package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;
import com.blog.yongyu.demo.Repository.RoleRepository;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Date;
import java.util.Optional;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;


    @Override
    public UserInfo findUserByAccount(String account) {
        return userInfoRepository.findUserInfoByAccount(account);
    }

    @Override
    public Optional<UserInfo> findUserById(Long id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public Integer createUser(UserInfo user) {
        if (user == null) {
            return 1; //不能为空
        }
        Optional<UserInfo> userById = findUserById(user.getId());
        if (!userById.isPresent()) {
            return 1;//用户存在
        }
        Long time = System.currentTimeMillis();
        user.setCreateTime(new Date(time));
        user.setModifyTime(new Date(time));
        user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes()));
        Role byRoleName = roleRepository.findByRoleName(Role.ROLE.NormalUser.toString());
        if (byRoleName == null) {
            byRoleName = new Role();
            roleService.addRole(byRoleName);
        }
        userInfoRepository.save(user);
        UserRole userRole = new UserRole(user, byRoleName);
        userRole.setIsDefault(UserRole.ISDEFAULT.isDefault.toString());//设置默认角色
        userRoleService.addUserRole(userRole);
        return 0;
    }

    @Override
    public Integer removeUser(Long userId) {
        if (userId == null) {
            return 1;//删除对象不存在
        }
//        userRoleService.removeAllUserRoleByUserId(userId);
        Optional<UserInfo> userInfo = findUserById(userId);
        if (userInfo.isPresent()){
            return 0;
        }
        userInfoRepository.delete(userInfo.get());
        return 0;//删除成功
    }

    @Override
    public Integer modifyUserInfo(UserInfo userInfo) {
        UserInfo save = userInfoRepository.save(userInfo);
        if (save == null) {
            return 1;//修改对象不存在
        }
        return 0;//修改成功
    }

    /**
     * 修改教师信息
     *
     * @param user
     * @return
     */
    @Override
    public Integer modifyUser(UserInfo user) {
        if (user == null) {
            return 1; //修改对象不存在
        }
        UserInfo userNew = userInfoRepository.save(user);
        return 0;
    }
}
