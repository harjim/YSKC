package com.yskc.docservice.models.rs.material;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectmaterial
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-06 17:31
 * @Description: 项目材料归集total
 */
public class ProjectRdMaterialTotalModel {

    private Integer projectId;

    private Date acqDate;

    private Integer rdType;

    private BigDecimal total;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
