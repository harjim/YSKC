package com.yskc.ms.models.dept;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.dept
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-19 16:23
 * @Description: 部门客户model
 */
public class DeptCustomerModel {

    private Integer deptId;

    private Integer year;

    private Integer customerId;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
