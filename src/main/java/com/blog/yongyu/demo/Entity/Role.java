package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role", schema="dbo", catalog = "et")
public class Role implements Serializable {
    public enum ROLE{
        NormalUser,
        Admin
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @Column
    @Type(type = "text")
    private String detail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<UserRole> userRoles;

    public Role() {
        roleName = "NormalUser";
        Long time = System.currentTimeMillis();
        createTime = new Date(time);
        modifyTime = new Date(time);
        detail = "";
        userRoles = new ArrayList<>();
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
