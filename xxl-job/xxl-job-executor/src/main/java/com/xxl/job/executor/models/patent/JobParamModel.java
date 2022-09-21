package com.xxl.job.executor.models.patent;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-19 08:23
 * @Description: job参数model
 */
public class JobParamModel {
    private Integer type;
    private Integer pageNo;
    private String queryArr;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getQueryArr() {
        return queryArr;
    }

    public void setQueryArr(String queryArr) {
        this.queryArr = queryArr;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
