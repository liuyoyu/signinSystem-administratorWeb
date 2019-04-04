/**
 * created by liuyongyu 2019/03/12
 */
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

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

    /**
     * 初始化密码
     */
    public void setInitPassword(){
        if (this.pwd == null) {
            setPwd(DigestUtils.md5DigestAsHex("8888".getBytes()));
        }
    }
}
