package com.blog.yongyu.demo.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studentInfo", schema="dbo", catalog = "et")
public class StudentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private String sno;
    @Column(nullable = false)
    private String sname;
    @Column
    private String sex;
    @Column(nullable = false)
    private String college;
    @Column(nullable = false)
    private String major;
    @Column
    private String phone;
    @Column
    private String Email;

    public StudentInfo() {
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
