package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.UserInfo;
import com.blog.yongyu.demo.Repository.UserInfoRepository;
import com.blog.yongyu.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Override
    public Integer checkLogin(String uname, String pwd) {
        UserInfo user = userInfoRepository.findUserInfoByAccount(uname);
        if (user == null) {
            return 1;//账户不存在
        }
        String pwd_md5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (pwd_md5.equals(user.getPwd())) {
            return 0;//账户密码正确
        }
        return 2;//密码不存在
    }
}
