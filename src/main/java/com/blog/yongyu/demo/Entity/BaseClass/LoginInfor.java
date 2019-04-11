/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Entity.BaseClass;

import com.blog.yongyu.demo.Entity.UserInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("loginInfo")
@Scope("request")
public class LoginInfor {

    private String userRoleId;

    private String role;

    private UserInfo  user;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}