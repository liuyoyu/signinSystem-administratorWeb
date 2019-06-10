/**
 * created by
 * Date:2019/4/7
 **/
package com.signInStart.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menu", schema = "dbo", catalog = "et")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //菜单编号

    @Column()
    private Long parentMenuId = 0L ; // 根节点的父节点默认为0

    @Column()
    private String menuName;    //菜单名称

    @Column()
    private String menuValue; //菜单代码

    @Column()
    private String menuURL;     //url

    @Column()
    private String menuStatus;      //菜单状态

    @Column()
    private String icon;    //图标

    @Column()
    private Integer sequence;   //菜单序号

    @Transient
    private String parentName;  //父菜单名称

    @Column()
    private Boolean hidden = false; //菜单是否隐藏


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "menu")
    @JsonIgnore
    private List<RoleMenu> roleMenus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "menu")
    @JsonIgnore
    private List<MenuUserType> menuUserTypes;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    @Column()
    private String createBy;

    @Column()
    private String modifyBy;

    @Transient
    private List<Menu> ChildrenMenu;

    public Menu() {
        roleMenus = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuURL() {
        return menuURL;
    }

    public void setMenuURL(String menuURL) {
        this.menuURL = menuURL;
    }

    public String getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(String menuStatus) {
        this.menuStatus = menuStatus;
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

    public List<RoleMenu> getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(List<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<Menu> getChildrenMenu() {
        return ChildrenMenu;
    }

    public void setChildrenMenu(List<Menu> childrenMenu) {
        ChildrenMenu = childrenMenu;
    }

    public List<String> getMenuMasterName(){
        if (roleMenus == null || roleMenus.size() < 1) {
            return null;
        }
//        List<String> res = roleMenus.stream().map(RoleMenu::getRoleName).collect(Collectors.toList());
        List<String> res = new ArrayList<>();
        for (RoleMenu rm : roleMenus) {
            res.add(rm.getRoleName());
        }
        return res;
    }

    public List<JSONObject> getRoles() {
        if (roleMenus == null) {
            return null;
        }
        List<JSONObject> list = new ArrayList<>();
        for (RoleMenu rm : roleMenus) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userType", rm.getUserType());
            jsonObject.put("roleName", rm.getRoleName());
            list.add(jsonObject);
        }
        return list;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public void setMenuValue(String menuValue) {
        this.menuValue = menuValue;
    }

    public List<MenuUserType> getMenuUserTypes() {
        return menuUserTypes;
    }

    public void setMenuUserTypes(List<MenuUserType> menuUserTypes) {
        this.menuUserTypes = menuUserTypes;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}