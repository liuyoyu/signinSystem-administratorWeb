/**
 * created by
 * Date:2019/4/3
 **/
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "userRole", schema = "dbo", catalog = "et")
public class UserRole implements Serializable {
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
    private Date creatTime;

    public UserRole() {
    }

    @Column()
    private String isDefault = ISDEFAULT.isNotDefault.toString();

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

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}