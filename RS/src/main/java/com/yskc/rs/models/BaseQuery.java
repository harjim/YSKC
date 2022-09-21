package com.yskc.rs.models;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.utils.DateUtil;

import java.util.Date;

/**
 * 分页查询基类
 *
 * @author huronghua
 */
public class BaseQuery {
    private int pageNo;
    private int pageSize;
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

    public Pagination getPagination() {
        return new Pagination(pageNo, pageSize);
    }

    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
