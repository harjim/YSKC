package com.yskc.docservice.entity.rs;


import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.entity.rs.tech.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 14:20
 * @Description: 委外项目
 */
@TableName("p_outsourcing")
public class ProjectOutsourcing extends BaseEntity {
    private Integer id;
    private Integer companyId;
    private Integer projectId;
    private Date month;
    /**
     * 0 国内委外，1 国外委外
     */
    private Integer type;
    private BigDecimal rdFunds;

    public static ProjectOutsourcing build(Date month,Integer projectId,BigDecimal rdFunds, Integer companyId, Integer type, Integer userId, Integer msUserId, Date now) {
        ProjectOutsourcing result = build(projectId,month,type);
        result.rdFunds = rdFunds;
        result.companyId = companyId;
        result.create(userId,msUserId,now);
        return result;
    }

    /**
     * 创建唯一索引实例，projectId + month + type 唯一
     * @param projectId
     * @param currentMonth
     * @param type
     * @return
     */
    public static ProjectOutsourcing build(Integer projectId, Date currentMonth, Integer type) {
        ProjectOutsourcing result = new ProjectOutsourcing();
        result.month = currentMonth;
        result.projectId = projectId;
        result.type = type;
        return result;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }
}
