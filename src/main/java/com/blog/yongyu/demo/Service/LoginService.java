package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.UserInfo;

public interface LoginService {
    UserInfo checkLogin(String uname, String pwd);

    Integer createUser(UserInfo user);
}
