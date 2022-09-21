package com.yskc.ms.models.projectsummary;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ms
 * @description: 前端传递的companyId, month
 * @author: cyj
 * @create: 2022-04-26 10:13
 **/
public class QuerySummaryMonthModel  implements Serializable {
    private Integer companyId;
    private Date month;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
