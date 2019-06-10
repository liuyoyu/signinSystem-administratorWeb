/**
 * created by liuyongyu 2019/03/12
 */
package com.signInStart.Entity;

import com.signInStart.Entity.BaseClass.BaseSetting;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.minidev.json.JSONObject;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "userInfo", schema = "dbo", catalog = "et")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String pwd;//MD5加密

    @Column()
    private String userName;

    @Column()
    private String sex;

    @Column()
    private String email;

    @Column()
    private String phone;

    @Column()
    private String status = BaseSetting.STATUS.Normal_SYS.toString();

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<UserRole> userRoles;

    public UserInfo() {
        userRoles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 初始化密码
     */
    public void setInitPassword(){
        setPwd(DigestUtils.md5DigestAsHex("8888".getBytes()));
    }

    /**
     * 设置新密码
     * @param newPwd
     */
    public void setNewPassword(String newPwd){
        if ("".equals(newPwd)) {
            setInitPassword();
        }
        setPwd(DigestUtils.md5DigestAsHex(newPwd.getBytes()));
    }



    /**
     * @Author liuyoyu
     * @Description //TODO  返回角色所拥有的角色：角色名和UserType
     * @Date 8:44 2019/5/26
     * @Param []
     * @return java.util.Map<java.lang.String,java.util.List>
     **/
    public List<JSONObject> getRole(){
        if (userRoles == null || userRoles.size() < 1) {
            return null;
        }
        List<JSONObject> list = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userType", userRole.getUserType());
            jsonObject.put("roleName", userRole.getRoleName());
            list.add(jsonObject);
        }
        return list;
    }
}
