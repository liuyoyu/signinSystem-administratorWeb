package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.LoginService;
import com.blog.yongyu.demo.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoService userInfoService;

    /**
     * 登陆检查
     * @param uname
     * @param pwd
     * @return
     */
    @Override
    public UserInfo checkLogin(String uname, String pwd) {
        UserInfo user = userInfoRepository.findUserByAccountOrEmail(uname);
        if (user == null || UserInfo.STATUS.Disable.equals(user.getStatus())) {
            return null;//用户不存在
        }
        if (DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(user.getPwd())) {
            return user;//账户密码正确
        }
        return null;//密码错误
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Integer createUser(UserInfo user) {
//        if (user == null) {
//            return null;
//        }
//
//
//
//        UserInfo checkUser = userInfoRepository.findUserInfoByAccount(user.getAccount());
//        if (checkUser != null) {
//            return null;//账号已存在
//        }
//
//        UserInfo newUser = userInfoRepository.save(user);
//        return newUser;//增加成功
        Integer res = userInfoService.Insert(user);
        return res;
    }
}
