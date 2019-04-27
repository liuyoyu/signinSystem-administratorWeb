/**
 * created by liuyoyu
 * Date:2019/4/3
 **/
package com.blog.yongyu.demo.Entity;

import com.blog.yongyu.demo.Entity.BaseClass.BaseSetting;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "userRole", schema = "dbo", catalog = "et")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createDate;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date modifyDate;

    @Column()
    private String createBy;

    @Column()
    private String modifyBy;

    public UserRole() {
    }

    @Column()
    private String isDefault = BaseSetting.ISDEFAULT.isNotDefault_SYS.toString();

    public UserRole(UserInfo userInfo, Role role) {
        this.userInfo = userInfo;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.util.Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(java.util.Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAccount(){
        if (userInfo != null) {
            return getUserInfo().getAccount();
        }
        return null;
    }
    public Long getUserId(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getId();
    }
    public String getRoleName(){
        if (role == null) {
            return null;
        }
        return role.getRoleName();
    }
    public Long getRoleId(){
        if (role == null) {
            return null;
        }
        return role.getRoleId();
    }
    public Date getLastLogin(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getLastLogin();
    }
    public String getUserStatus(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getStatus();
    }
    public String getPhone(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getPhone();
    }
    public String getEmail(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getEmail();
    }
    public String getSex(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getSex();
    }
    public String getUserName(){
        if (userInfo == null) {
            return null;
        }
        return userInfo.getUserName();
    }
}