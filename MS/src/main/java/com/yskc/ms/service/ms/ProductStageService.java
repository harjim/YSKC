package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.product.AddStageModel;
import com.yskc.ms.models.product.StageModel;
import org.apache.xpath.operations.Bool;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/7/28 14:12
 * description:
 */
public interface ProductStageService {

    /**
     * 根据项目类型获取阶段列表
     * @param productId
     * @return
     */
    List<StageModel> getStageList(Integer productId);

    /**
     * 判断阶段下是否存在数据
     * @param keys
     * @param productId
     * @return
     */
    String checkStageData(List<String> keys, Integer productId) throws OwnerException;

    Boolean addProductStage(Integer userId,AddStageModel model) throws OwnerException;

    Boolean updateProductStage(Integer userId,AddStageModel model) throws OwnerException;
}
