package com.yskc.rs.models.inspection;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * 费用查询
 *
 * @author huronghua
 */
public class QueryInspectionModel extends PageParams {
    private String[] type;
    private Date accDate;
    private String accNumber;
    private String summary;
    @Deprecated
    private String deptPath;
    @Deprecated
    private String rdDeptPath;
    private String deptName;

    private Date startMonth;
    private Date endMonth;
    private Integer qtype;

    public Date getStartMonth() {
        if (startMonth != null) {
            return DateUtil.getMonthFirstDay(startMonth);
        }
        return startMonth;
    }

    public void setStartMonth(Date startMonth) {
        this.startMonth = startMonth;
    }

    public Date getEndMonth() {
        if (endMonth != null) {
            return DateUtil.getMonthLastDay(endMonth);
        }
        return endMonth;
    }
    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public Date getAccDate() {
        return accDate;
    }

    public void setAccDate(Date accDate) {
        this.accDate = accDate;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getQtype() {
        return qtype;
    }

    public void setQtype(Integer qtype) {
        this.qtype = qtype;
    }
}
