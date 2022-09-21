package com.yskc.rs.models.aggregation;

import cn.hutool.core.date.DateUtil;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-04 09:17
 * @Description: 日期区间model
 */
public class DateRangeModel {

    private Date begin;

    private Date end;

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public DateRangeModel() {
    }

    public DateRangeModel(Date month) {
        begin = DateUtil.beginOfMonth(month);
        end = DateUtil.endOfMonth(begin);
    }

    public static List<DateRangeModel> getDateRange(List<Date> months) {
        if (CollectionUtils.isEmpty(months)) {
            return null;
        }
        return months.stream().map(a -> new DateRangeModel(a)).collect(Collectors.toList());
    }
}
