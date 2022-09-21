package com.yskc.docservice.models.rs.projectrdemployee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-18 11:30
 * @Description: 月份相同的事物(ecode, enumber)集合
 */
public class MonthThingSetModel {
    private Date month;
    private Set<String> keys;


    public MonthThingSetModel(Date month, Set<String> keys) {
        this.month = month;
        this.keys = keys;
    }

    public MonthThingSetModel(Date month) {
        this.month = month;
        this.keys = new HashSet<>();
    }

    public MonthThingSetModel() {
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }
}
