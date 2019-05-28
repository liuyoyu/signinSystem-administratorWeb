package com.signInStart.Entity;
/**
 * @Author liuyoyu
 * @Description //TODO  角色信息表；
 * //TODO 由角色来控制系统的权限，role表中存储各个角色的信息，其中userType是role所属的角色类型，是最终权限控制的归属
 * //TODO 本来userType是应该独立一张表的，但由于时间紧迫，就不建立了
 * @Date 16:38 2019/5/26
 * @Param
 * @return
 **/
import com.signInStart.Entity.BaseClass.BaseSetting;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role", schema="dbo", catalog = "et")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;

//    @Column()
//    private Long parentRole = 0L; //0表示无父角色     //废弃

    //角色所属类型：普通用户、管理员、超级管理员; 角色类型决定用户具有哪些权限；
    @Column()
    private String userType = BaseSetting.ROLE.User_SYS.toString();

//    //角色所属分组
//    @Column()
//    private String groupBy;

    @Column()
    private String status;

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

    @Column()
    @Type(type = "text")
    private String detail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<UserRole> userRoles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.REMOVE)
    @JsonIgnore()
    private List<RoleMenu> roleMenus;

    public Role() {
        roleName ="普通用户";
        userType = BaseSetting.ROLE.User_SYS.toString();
        detail = "";
        userRoles = new ArrayList<>();
        roleMenus = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
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

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<RoleMenu> getRoleMenus() {
        return roleMenus;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        userType = userType;
    }

    public void setRoleMenus(List<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }

}
