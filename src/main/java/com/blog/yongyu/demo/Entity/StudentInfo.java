package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "studentInfo", schema="dbo", catalog = "et")
public class StudentInfo implements Serializable {

    @Id
    @Column(nullable = false)
    private String sno;
    @Column(nullable = false)
    private String sname;
    @Column
    private String sex;
    @Column
    private String grage;//年级
    @Column()
    private String college;
    @Column()
    private String major;
    @Column
    private String phone;
    @Column
    private String Email;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @Column
    private String creatorName;

    @Column
    private String menderName;//修改者名字

    public StudentInfo() {
    }

    public String getGrage() {
        return grage;
    }

    public void setGrage(String grage) {
        this.grage = grage;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getMenderName() {
        return menderName;
    }

    public void setMenderName(String menderName) {
        this.menderName = menderName;
    }

    public StudentInfo(String sname, String college, String major) {
        this.sname = sname;
        this.college = college;
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", sex='" + sex + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", phone='" + phone + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
