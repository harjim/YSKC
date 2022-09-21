package com.yskc.ms.models.projectuncollected;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ms
 * @description: 未归集项目模型
 * @author: cyj
 * @create: 2021-12-11 11:56
 **/
public class ProjectUnCollectedModel implements Serializable {

    private Integer id;

    private String customerName;

    private String rdTitle;

    private String pname;

    private Integer year;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
