/**
 * created by liuyongyu 2019/03/12
 */
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "UserRole", schema="dbo", catalog = "et")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String userRoleUserId;

    @OneToMany(mappedBy = "userRole",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserInfo> userInfos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;

    public UserRole() {
    }

    public UserRole(String userRoleUserId, List<UserInfo> userInfo, Role role) {
        this.userRoleUserId = userRoleUserId;
        this.userInfos = userInfo;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserInfo> getUserInfo() {
        return userInfos;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfos = userInfo;
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
                ", userInfo=" + userInfos +
                ", role=" + role +
                '}';
    }
}
