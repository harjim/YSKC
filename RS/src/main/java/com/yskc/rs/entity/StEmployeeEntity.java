package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;

/**
 * @program: rs
 * @description: stEmployee
 * @author: cyj
 * @create: 2022-08-08 16:00
 **/
@TableName("stEmployee")
public class StEmployeeEntity extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer year;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String enumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }
}
