package com.yskc.ms.models.patentMaster;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/7/7 15:05
 * description:查询专利负责人参数model
 */
public class QueryMasterModel extends PageParams {

    private String masterName;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
}
