/**
 * created by
 * Date:2019/4/4
 **/
package com.blog.yongyu.demo.Entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "shortMessage", schema = "dbo", catalog = "et")
public class ShortMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String  account;

    @Column()
    private String email;

    @Column()
    private String code;

    @Column()
    private Date sendTime;

    @Column()
    @Type(type = "text")
    private String purpose;

    public ShortMessage() {
    }

    public ShortMessage(String account, String email, String code, Date sendTime, String purpose) {
        this.account = account;
        this.email = email;
        this.code = code;
        this.sendTime = sendTime;
        this.purpose = purpose;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}