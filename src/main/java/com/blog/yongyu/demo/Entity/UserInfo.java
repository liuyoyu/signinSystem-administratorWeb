/**
 * created by liuyongyu 2019/03/12
 */
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "userInfo", schema = "dbo", catalog = "et")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String pwd;//MD5加密

    @Column()
    private String userName;

    @Column()
    private String sex;

    @OneToMany(mappedBy = "userInfo",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore//不进行序列化
    private List<UserRole> userRoles;

    public UserInfo() {
        userRoles = new ArrayList<>();
    }

    public UserInfo(Date createTime, String account, String pwd, String userName, String sex, List<UserRole> userRoles) {
        this.createTime = createTime;
        this.account = account;
        this.pwd = pwd;
        this.userName = userName;
        this.sex = sex;
        this.userRoles = userRoles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
