package com.yskc.rs.models.company;

import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/rs/models/company
 * @Author: hm
 * @CreateTime: 2022/8/22
 * @Description: 公司名称变更model
 */
public class CompanyNameHistoryModel {

    /*
     * ID
     */
    private Integer id;

    /*
     * 变更名称
     */
    private String companyName;

    /*
     * 生效时间
     */
    private Date startDate;

    /*
     * 创建时间
     */
    private Date createTime;

    /*
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
