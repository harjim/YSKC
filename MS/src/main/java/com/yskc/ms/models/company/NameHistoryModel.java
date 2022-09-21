package com.yskc.ms.models.company;

import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/models/company
 * @Author: hm
 * @CreateTime: 2022/8/25
 * @Description: 公司历史名称model
 */
public class NameHistoryModel {

    /*
     * ID
     */
    private Integer id;

    private Integer customerId;

    /*
     * 变更名称
     */
    private String companyName;

    /*
     * 变更名称
     */
    private Integer companyId;

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
