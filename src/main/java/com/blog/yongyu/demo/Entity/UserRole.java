/**
 * created by
 * Date:2019/4/3
 **/
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "userRole", schema = "dbo", catalog = "et")
public class UserRole implements Serializable {
    public enum STATUS{
        Normal,
        Disabled
    }
    public enum ISDEFAULT {
        isDefault,
        isNotDefault
    }
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
    private String isDefault = ISDEFAULT.isNotDefault.toString();

    public UserRole(UserInfo userInfo, Role role) {
        this.userInfo = userInfo;
        this.role = role;
        this.createDate = new Date();
        this.modifyDate = new Date();
        this.createBy = userInfo.getAccount();
        this.modifyBy = userInfo.getAccount();
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
}