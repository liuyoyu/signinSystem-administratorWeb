package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "teacherInfo", schema="dbo", catalog = "et")
public class TeacherInfo implements Serializable {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String tname;

    @Column(length = 10)
    private String sex;

    @Column()
    private String college;

    @Column()
    private String academicTitle;

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

    public TeacherInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
}
