package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.entity.rs.tech.BaseEntity;


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
