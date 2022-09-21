package com.yskc.rs.models.energy;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.BaseQuery;
import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.energy
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-03 18:43
 * @Description: 能源损耗查询
 */
public class QueryEnergy extends PageParams implements Serializable {

    private Integer type;

    private String ename;

    @Deprecated
    private String deptPath;

    @Deprecated
    private String rdDeptPath;

    private String deptName;


    private Date startMonth;
    private Date endMonth;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
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
}
