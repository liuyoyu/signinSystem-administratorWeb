/**
 * created by
 * Date:2019/4/10
 **/
package com.blog.yongyu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dictionary", schema = "dbo", catalog = "et")
public class Dictionary implements Serializable {
    public enum DATATYPE{
        System,
        Standard
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String DataType;

    @Column()
    private String DataKey;

    @Column()
    private String DataValue;

    @Column()
    @Type(type = "text")
    private String DataDesc;


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

    @OneToMany(mappedBy = "dictionary",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    List<DictionaryContent> dictionaryContents = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getDataDesc() {
        return DataDesc;
    }

    public void setDataDesc(String dataDesc) {
        DataDesc = dataDesc;
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

//    public List<DictionaryContent> getDictionaryContents() {
//        return dictionaryContents;
//    }
//
//    public void setDictionaryContents(List<DictionaryContent> dictionaryContents) {
//        this.dictionaryContents = dictionaryContents;
//    }

    public String getDataKey() {
        return DataKey;
    }

    public void setDataKey(String dataKey) {
        DataKey = dataKey;
    }

    public List<DictionaryContent> getDictionaryContents() {
        return dictionaryContents;
    }

    public void setDictionaryContents(List<DictionaryContent> dictionaryContents) {
        this.dictionaryContents = dictionaryContents;
    }

    public String getDataValue() {
        return DataValue;
    }

    public void setDataValue(String dataValue) {
        DataValue = dataValue;
    }
}