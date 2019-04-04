//package com.blog.yongyu.demo.Entity;
//
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.sql.Date;
//
//@Entity
//@Table(name = "role", schema = "dbo", catalog = "et")
//public class Course implements Serializable {
//    @Id
//    @Column(nullable = false)
//    private Long id;
//
//    @Column
//    private String name; //课程名称
//
//    @Column()
//    private String location; //上课地点
//
//    @Column
//    private Date startDate; //开课日期
//
//    @Column
//    private Date endDate; //结课日期
//
//    @Column
//    private String day; //星期几上课
//
//    @Column
//    private String startSection; //起始节
//
//    @Column
//    private String endSection; //终止节
//
//    @Column
//    private String semester; //学期
//
//    @Column
//    private Date createDate; //创建日期
//
//    @Column
//    private Date modifyDate; //修改时间
//    @Column
//    private String Creator; //创建者
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    public String getDay() {
//        return day;
//    }
//
//    public void setDay(String day) {
//        this.day = day;
//    }
//
//    public String getStartSection() {
//        return startSection;
//    }
//
//    public void setStartSection(String startSection) {
//        this.startSection = startSection;
//    }
//
//    public String getEndSection() {
//        return endSection;
//    }
//
//    public void setEndSection(String endSection) {
//        this.endSection = endSection;
//    }
//
//    public String getSemester() {
//        return semester;
//    }
//
//    public void setSemester(String semester) {
//        this.semester = semester;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Date getModifyDate() {
//        return modifyDate;
//    }
//
//    public void setModifyDate(Date modifyDate) {
//        this.modifyDate = modifyDate;
//    }
//
//    public String getCreator() {
//        return Creator;
//    }
//
//    public void setCreator(String creator) {
//        Creator = creator;
//    }
//}