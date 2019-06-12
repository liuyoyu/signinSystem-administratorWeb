package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.UserInfo;

public interface LoginService {
    UserInfo checkLogin(String uname, String pwd) throws FriendlyException;

    Integer createUser(UserInfo user) throws FriendlyException;

    void resetPassword(String account,String email, String password) throws FriendlyException;
}
