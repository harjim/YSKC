package com.yskc.ms.models.rs;

import com.yskc.ms.models.rs.RsProductStageListModel;
import com.yskc.ms.models.rs.RsProductStageModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/10/14 9:10
 * description:申报项目阶段设置model
 */
public class RsProductSettingModel implements Serializable {

    private List<Integer> productIds;//要设置阶段的申报id集合

    private List<RsProductStageModel> stageModels;//配置的阶段集合

    private Map<String,List<RsProductStageListModel>> stageListModels;//key：阶段key; value：阶段对应的文件类型集合

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public List<RsProductStageModel> getStageModels() {
        return stageModels;
    }

    public void setStageModels(List<RsProductStageModel> stageModels) {
        this.stageModels = stageModels;
    }

    public Map<String, List<RsProductStageListModel>> getStageListModels() {
        return stageListModels;
    }

    public void setStageListModels(Map<String, List<RsProductStageListModel>> stageListModels) {
        this.stageListModels = stageListModels;
    }
}
