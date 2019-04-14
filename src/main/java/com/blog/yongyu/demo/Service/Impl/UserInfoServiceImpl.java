package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Entity.UserRole;
import com.blog.yongyu.demo.Repository.RoleRepository;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Service.UserInfoService;
import com.blog.yongyu.demo.Service.UserRoleService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
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
    public List<UserInfo> findUserByEmail(String email) {
        List<UserInfo>user =  userInfoRepository.findUserByEmail(email);
        if (user.size() < 1 || user == null) {
            return null;
        }
        return user;
    }

    @Override
    public Integer Insert(UserInfo user) {
        if (user == null) {
            return 1; //不能为空
        }
        if (user.getAccount() == null) {
            return 2; //账户不能为空
        }
        if (user.getPwd() == null) {
            return 5;//密码不能空
        }
        if (user.getEmail() == null) {
            return 6;//邮箱不能空
        }
        if (findUserByAccount(user.getAccount())!=null) {
            return 3; //该账户已被注册
        }
        if (findUserByEmail(user.getEmail()) != null) {
            return 4;//该邮箱已被注册
        }

        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes())); //md5加密
        Role byRoleName = roleRepository.findByRoleName(Role.ROLE.User.toString());//角色中的正常用户
        if (byRoleName == null) {//不存在则创建
            byRoleName = new Role();
            roleService.Insert(byRoleName);
        }
        userInfoRepository.save(user);
        UserRole userRole = new UserRole(user, byRoleName);
        userRole.setIsDefault(UserRole.ISDEFAULT.isDefault.toString());//设置默认角色
        userRoleService.addUserRole(userRole);
        return 0;
    }

    @Override
    public Integer Delete(Long userId) {
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
    public Integer modify(UserInfo userInfo) {
        if (null == findUserById(userInfo.getId())) {
            return 1;//修改对象不存在
        }
        if ("".equals(userInfo.getEmail()) || userInfo.getEmail() == null) {
            return 2; //邮箱不能为空
        }
        if ("".equals(userInfo.getPwd()) || userInfo.getPwd() == null) {
            return 3;//密码不能为空
        }
        if ("".equals(userInfo.getUserName()) || userInfo.getUserName() == null) {
            return 4;//昵称不能为空
        }
        userInfo.setModifyDate(new Date());
        UserInfo save = userInfoRepository.save(userInfo);
        return 0;//修改成功
    }

    @Override
    public List<UserInfo> findAll() {
        List<UserInfo> all = userInfoRepository.findAll();
        return all;
    }

    @Override
    @Transactional
    public void allResetPwd(Long[] list) {
        userInfoRepository.allResetPwd(DigestUtils.md5DigestAsHex("8888".getBytes()), new Date(),list);
    }
}
