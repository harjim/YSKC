package com.yskc.rs.models.tech;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.tech
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-23 14:37
 * @Description: 查询技改model
 */
public class QueryTechProjectModel implements Serializable {

    private Integer pyear;

    private String pname;

    private int pageNo;

    private int pageSize;

    public Integer getPyear() {
        return pyear;
    }

    public void setPyear(Integer pyear) {
        this.pyear = pyear;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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
