package com.yskc.rs.models.voucher;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/16 9:20
 * description:
 */
public class RelatedVoucherModel implements Serializable {

    private List<Integer> ids;//要关联的凭证id

    private Integer rdType;//费用类型

    private List<Integer> projectIds;//关联的项目

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }
}
