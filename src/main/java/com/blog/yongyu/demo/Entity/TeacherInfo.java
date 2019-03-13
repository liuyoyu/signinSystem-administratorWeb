package com.blog.yongyu.demo.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teacherInfo", schema="dbo", catalog = "et")
public class TeacherInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String tname;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String academicTitle;

    @Column
    private String phone;

    @Column
    private String Email;

    public TeacherInfo() {
    }

    public TeacherInfo(String tname, String college, String academicTitle, String phone, String email) {
        this.tname = tname;
        this.college = college;
        this.academicTitle = academicTitle;
        this.phone = phone;
        Email = email;
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

    @Override
    public String toString() {
        return "TeacherInfo{" +
                "id='" + id + '\'' +
                ", tname='" + tname + '\'' +
                ", college='" + college + '\'' +
                ", academicTitle='" + academicTitle + '\'' +
                ", phone='" + phone + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
