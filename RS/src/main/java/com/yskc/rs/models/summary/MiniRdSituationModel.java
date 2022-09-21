package com.yskc.rs.models.summary;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.summary
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-29 16:05
 * @Description:
 */
public class MiniRdSituationModel {

    private Integer id;

    private Integer employee;

    private BigDecimal totalWork;

    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public BigDecimal getTotalWork() {
        return totalWork;
    }

    public void setTotalWork(BigDecimal totalWork) {
        this.totalWork = totalWork;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
