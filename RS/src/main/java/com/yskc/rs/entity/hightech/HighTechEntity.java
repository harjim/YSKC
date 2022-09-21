package com.yskc.rs.entity.hightech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.HighTechModel;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 16:47
 * @Description: 高新技术表
 */
@TableName("highTech")
public class HighTechEntity extends BaseEntity {
    @TableId
    private Integer id;
    private Integer companyId;
    private String tecIndustry;
    private String hcode;
    private String hname;
    private Integer startYear;
    private Integer endYear;
    private Integer codeNum;
    private String description;

    public HighTechEntity() {
    }



    public static HighTechEntity build(Date date, HighTechModel model, UserInfo userInfo) {
        HighTechEntity highTech=new HighTechEntity();
        BeanUtils.copyProperties(model,highTech);
        highTech.setCompanyId(userInfo.getCompanyId());
        highTech.setCreateTime(date);
        highTech.setLastUpdateTime(date);
        highTech.setCreatorId(userInfo.getUserId());
        highTech.setLastUpdatorId(userInfo.getUserId());
        highTech.setMsCreatorId(userInfo.getMsUserId());
        highTech.setMsLastUpdatorId(userInfo.getMsUserId());
        highTech.setEndYear(model.getStartYear()+2);
        highTech.setHcode(MessageFormat.format("{0,number,#}PS{1}",model.getStartYear(),model.getCodeNum()>9?model.getCodeNum():"0"+model.getCodeNum()));
        return highTech;
    }

    public Integer getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(Integer codeNum) {
        this.codeNum = codeNum;
    }

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

    public String getTecIndustry() {
        return tecIndustry;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public String getHcode() {
        return hcode;
    }

    public void setHcode(String hcode) {
        this.hcode = hcode;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
