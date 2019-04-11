package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "course", schema = "dbo", catalog = "et")
public class Course implements Serializable {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column()
    private String name; //课程名称

    @Column()
    private String location; //上课地点

    @Column()
    private String startWeek; //起始周

    @Column()
    private String endWeek; //终止周

    @Column()
    private String startSection; //起始节

    @Column()
    private String endSection; //终止节

    @Column()
    private String semester; //学期

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(String startWeek) {
        this.startWeek = startWeek;
    }

    public String getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(String endWeek) {
        this.endWeek = endWeek;
    }

    public String getStartSection() {
        return startSection;
    }

    public void setStartSection(String startSection) {
        this.startSection = startSection;
    }

    public String getEndSection() {
        return endSection;
    }

    public void setEndSection(String endSection) {
        this.endSection = endSection;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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
}