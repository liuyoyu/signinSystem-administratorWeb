/**
 * created by
 * Date:2019/4/10
 **/
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "dicContent", schema = "dbo", catalog = "et")
public class DictionaryContent implements Serializable {
    private enum STATUS{
        Normal,
        Disabled
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String contentValue;

    @Column()
    private String contentKey;

    @Column()
    private String status=STATUS.Normal.toString();

    @Column()
    private String describe;

    @Column()
    private Integer sequence; //序号

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    @Column()
    private String createBy;

    @Column()
    private String modifyBy;

    @ManyToOne
    @JoinColumn(name = "dictionaryID")
    Dictionary dictionary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}