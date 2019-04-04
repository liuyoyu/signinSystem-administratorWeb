package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.LoginService;
import com.blog.yongyu.demo.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;

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
    public Integer checkLogin(String uname, String pwd) {
        UserInfo user = userInfoRepository.findUserInfoByAccount(uname);
        if (user == null) {
            return 1;//账户不存在
        }
        if (DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(user.getPwd())) {
            return 0;//账户密码正确
        }
        return 1;//密码错误
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
        Integer res = userInfoService.createUser(user);
        return res;
    }
}
