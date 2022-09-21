package com.yskc.ms.models.patent;

import com.yskc.ms.entity.rs.RsPatentApplyEntity;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2019-11-06 16:51
 * @Description: 联合专利model
 */
public class PatentUnionModel {

    private RsPatentEntity patent;

    private List<RsPatentApplyEntity> patentApplyList;

    private List<RsPatentCostEntity> isPatentCostList;

    private List<RsPatentCostEntity> patentCostList;

    public RsPatentEntity getPatent() {
        return patent;
    }

    public void setPatent(RsPatentEntity patent) {
        this.patent = patent;
    }

    public List<RsPatentApplyEntity> getPatentApplyList() {
        return patentApplyList;
    }

    public void setPatentApplyList(List<RsPatentApplyEntity> patentApplyList) {
        this.patentApplyList = patentApplyList;
    }

    public List<RsPatentCostEntity> getIsPatentCostList() {
        return isPatentCostList;
    }

    public void setIsPatentCostList(List<RsPatentCostEntity> isPatentCostList) {
        this.isPatentCostList = isPatentCostList;
    }

    public List<RsPatentCostEntity> getPatentCostList() {
        return patentCostList;
    }

    public void setPatentCostList(List<RsPatentCostEntity> patentCostList) {
        this.patentCostList = patentCostList;
    }
}
