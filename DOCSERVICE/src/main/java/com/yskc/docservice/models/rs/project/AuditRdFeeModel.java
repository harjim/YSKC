package com.yskc.docservice.models.rs.project;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/5/5 9:30
 * @Description:
 * @author: hsx
 */
public class AuditRdFeeModel implements Serializable {

    private Integer status;

    private Date month;

    private String rdTitle;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }
}
