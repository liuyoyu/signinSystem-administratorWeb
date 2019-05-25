package com.blog.yongyu.signInStart.Service;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.UserInfo;

public interface LoginService {
    UserInfo checkLogin(String uname, String pwd) throws FriendlyException;

    Integer createUser(UserInfo user) throws FriendlyException;
}
