package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.BaseClass.BaseRole;
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

    @Autowired
    LoginInfoServiceImpl loginInfoService;


    @Override
    public UserInfo findUserByAccount(String account) {
        return userInfoRepository.findUserInfoByAccount(account);
    }

    @Override
    public UserInfo findUserById(Long id) {
        Optional<UserInfo> byId = userInfoRepository.findById(id);
        if (!byId.isPresent()) {
            return null;
        }
        return byId.get();
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
        if (user.getAccount().length() <= 4 || user.getAccount().length() > 10) {
            return 7;
        }

        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes())); //md5加密
        Role byRoleName = roleRepository.findByRoleId(BaseRole.UserId);//角色中的用户
        if (byRoleName == null) {//不存在则创建
            byRoleName = new Role();
            roleService.Insert(byRoleName);
        }
        user.setCreateDate(new Date());
        user.setModifyDate(new Date());
        user.setCreateBy(loginInfoService.getAccount());
        user.setModifyBy(loginInfoService.getAccount());
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
        UserInfo userInfo = findUserById(userId);
        if (userInfo != null) {
            userInfoRepository.delete(userInfo);
        }
        return 0;//删除成功
    }

    /**
     * 修改用户信息，不能修改用户账户信息
     * 管理员界面调用
     * @param userInfo
     * @return
     */
    @Override
    public Integer modify(UserInfo userInfo) {
        UserInfo userById = findUserById(userInfo.getId());
        if (null == userById ) {
            return 1;//修改对象不存在
        }
        if (!"".equals(userInfo.getEmail()) && userInfo.getEmail() != null) {
            if (findUserByEmail(userInfo.getEmail()) != null && !userInfo.getEmail().equals(userById.getEmail())) {
                return 2; //邮箱已被占用
            }
            userById.setEmail(userInfo.getEmail());
        } else {
            return 3; //邮箱不能为空
        }
        if (!"".equals(userInfo.getUserName()) && userInfo.getUserName() != null) {
            userById.setUserName(userInfo.getUserName());
        }
        if (!"".equals(userInfo.getStatus()) && userInfo.getStatus() != null) {
            userById.setStatus(userInfo.getStatus());
        }
        if (!"".equals(userInfo.getPhone()) && userInfo.getPhone() != null) {
            userById.setPhone(userInfo.getPhone());
        }
        if (!"".equals(userInfo.getSex()) && userInfo.getSex() != null) {
            userById.setSex(userInfo.getSex());
        }
        userById.setModifyDate(new Date());
        userById.setModifyBy(loginInfoService.getAccount());
        userInfoRepository.save(userById);
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
