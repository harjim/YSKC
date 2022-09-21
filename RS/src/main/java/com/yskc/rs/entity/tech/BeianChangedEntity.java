package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: rs
 * @description:
 * @author: wjy
 * @create: 2022-09-21 09:37
 **/
@TableName("t_beian_changed")
public class BeianChangedEntity extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer beianId;
    private Date changeLetterDate;
    private String changeContent;
    private String changeFilePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }

    public Date getChangeLetterDate() {
        return changeLetterDate;
    }

    public void setChangeLetterDate(Date changeLetterDate) {
        this.changeLetterDate = changeLetterDate;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public String getChangeFilePath() {
        return changeFilePath;
    }

    public void setChangeFilePath(String changeFilePath) {
        this.changeFilePath = changeFilePath;
    }
}
