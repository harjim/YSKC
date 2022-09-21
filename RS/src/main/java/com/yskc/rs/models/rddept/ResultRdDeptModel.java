package com.yskc.rs.models.rddept;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rddept
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-10 11:29
 * @Description: 返回用研发组织model
 */
public class ResultRdDeptModel implements Serializable {

    private List<RdDeptTree> data;

    private List<Integer> prevYears;

    public List<RdDeptTree> getData() {
        return data;
    }

    public void setData(List<RdDeptTree> data) {
        this.data = data;
    }

    public List<Integer> getPrevYears() {
        return prevYears;
    }

    public void setPrevYears(List<Integer> prevYears) {
        this.prevYears = prevYears;
    }
}
