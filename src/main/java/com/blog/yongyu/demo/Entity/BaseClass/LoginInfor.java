/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Entity.BaseClass;

import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Entity.UserInfo;

import java.util.Date;


public class LoginInfor {

    private Long userRoleId;
    private String roleName;

    private Role role;

    private Long userId;
    private String uname;
    private String Account;
    private String email;
    private Date lastLogin;

//    private UserInfo  user;

    private String status;

    private Date loginDate;

    public String getRoleName(){
        return role.getRoleName();
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }
    public Long getRoleId(){
        if (role != null) {
            return role.getRoleId();
        }
        return null;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getUserId() {
        return userId;
    }
}