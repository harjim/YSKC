package com.yskc.rs.models.finance;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/4/28 14:45
 * @Description:
 * @author: hsx
 */
public class AuditStatusModel implements Serializable {

    private Date month;

    private Integer rdType;

    private Integer status;

    private String suggestion;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
