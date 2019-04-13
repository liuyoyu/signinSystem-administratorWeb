/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Entity.BaseClass;

import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;


public class LoginInfor {

    private String userRoleId;

    private Role role;

    private UserInfo  user;

    private String status;

    public String getRoleName(){
        return role.getRoleName();
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}