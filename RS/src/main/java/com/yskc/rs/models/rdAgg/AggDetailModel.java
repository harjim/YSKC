package com.yskc.rs.models.rdAgg;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/rs/models
 * @Author: hm
 * @CreateTime: 2022/8/18
 * @Description: 研发费用总表合计查询模板
 */
public class AggDetailModel {
    /*
     * 项目id
     * */
    private Integer projectId;
    /*
     * 项目名称
     * */
    private String rdPname;
    /*
     * 项目Rd
     * */
    private String rdTitle;
    /*
     * 项目每月费用
     */
    private BigDecimal rdFunds;
    /*
     * 项目时间
     */
    private Date rdMonth;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRdPname() {
        return rdPname;
    }

    public void setRdPname(String rdPname) {
        this.rdPname = rdPname;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Date getRdMonth() {
        return rdMonth;
    }

    public void setRdMonth(Date rdMonth) {
        this.rdMonth = rdMonth;
    }

}
