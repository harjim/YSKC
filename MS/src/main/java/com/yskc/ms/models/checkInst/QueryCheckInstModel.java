package com.yskc.ms.models.checkInst;

import com.yskc.ms.models.params.PageParams;

/**
 * @program: ms
 * @description: 查询查新机构
 * @author: cyj
 * @create: 2022-08-09 10:29
 **/
public class QueryCheckInstModel extends PageParams {
    private String instName;
    private String linkMan;
    private String creatorName;

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
