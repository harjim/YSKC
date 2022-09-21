package com.yskc.rs.models.rdaccountdetail;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.BaseQuery;
import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdaccountdetail
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 11:28
 * @Description: 费用明细查询接口
 */
public class QueryRdAccountDetailModel extends PageParams implements Serializable {

    private Date rdDate;

    private String accNumber;

    private int accType;

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

    public Date getRdDate() {
        return rdDate;
    }

    public void setRdDate(Date rdDate) {
        this.rdDate = rdDate;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public int getAccType() {
        return accType;
    }

    public void setAccType(int accType) {
        this.accType = accType;
    }
}
