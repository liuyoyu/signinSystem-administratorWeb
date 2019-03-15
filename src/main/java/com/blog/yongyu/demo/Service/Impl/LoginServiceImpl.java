package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserInfoRepository userInfoRepository;

    /**
     * 登陆检查
     * @param uname
     * @param pwd
     * @return
     */
    @Override
    public UserInfo checkLogin(String uname, String pwd) {
        UserInfo user = userInfoRepository.findUserInfoByAccount(uname);
        if (user == null) {
            return null;//账户不存在
        }
        String pwd_md5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (pwd_md5.equals(user.getPwd())) {
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
    public UserInfo createUser(UserInfo user) {
        if (user == null) {
            return null;
        }
        UserInfo checkUser = userInfoRepository.findUserInfoByAccount(user.getAccount());
        if (checkUser != null) {
            return null;//账号已存在
        }

        UserInfo newUser = userInfoRepository.save(user);
        return newUser;//增加成功
    }
}
