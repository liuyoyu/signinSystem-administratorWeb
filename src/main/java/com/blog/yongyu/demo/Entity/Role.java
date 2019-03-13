package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role", schema="dbo", catalog = "et")
public class Role implements Serializable {
    @Id
    @Column(nullable = false)
    private String roleId;

    @Column(nullable = false)
    private String roleName;

    @Column
    @Type(type = "text")
    private String detail;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserRole> userRoles;

    public Role() {
        this.userRoles = new ArrayList<>();
    }

    public Role(String roleId, String roleName, String detail, List<UserRole> userRoles) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.detail = detail;
        this.userRoles = userRoles;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", detail='" + detail + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
