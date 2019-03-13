/**
 * created by liuyongyu 2019/03/12
 */
package com.blog.yongyu.demo.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserRole", schema="dbo", catalog = "et")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String userRoleUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;

    public UserRole() {
    }

    public UserRole(String userRoleUserId, UserInfo userInfo, Role role) {
        this.userRoleUserId = userRoleUserId;
        this.userInfo = userInfo;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUserRoleUserId() {
        return userRoleUserId;
    }

    public void setUserRoleUserId(String userRoleUserId) {
        this.userRoleUserId = userRoleUserId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", userInfo=" + userInfo +
                ", role=" + role +
                '}';
    }
}
