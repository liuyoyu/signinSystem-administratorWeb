/**
 * created by liuyoyu
 * 机构类，用于各个学校或机构创建自己的签到课程
 * Date:2019/5/9
 **/
package com.blog.yongyu.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "origanization",schema = "dbo",catalog = "et")
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

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
}