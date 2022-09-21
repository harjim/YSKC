package com.yskc.ms.models.rs.summary;

import com.yskc.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.summary
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-01 13:41
 * @Description: 年份key
 */
public class YearKeysModel {

    private Integer year;

    private List<String> keys;

    private Date begin;

    private Date end;
    public YearKeysModel(Integer year) {
        this.year = year;
        this.begin = DateUtil.getYearFirstDay(year);
        this.end = DateUtil.getYearLastDay(year);
        keys = new ArrayList<>();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public void addKey(String key) {
        this.keys.add(key);
    }

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
}
