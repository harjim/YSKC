package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProductStageEntity;
import com.yskc.ms.models.product.StageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/28 14:13
 * description:
 */
@Repository
public interface ProductStageDao extends BaseMapper<ProductStageEntity> {

    List<StageModel> getByProductId(@Param("productId") Integer productId);

    Integer insertList(@Param("entityList") List<ProductStageEntity> entityList);

    /**
     * 删除产品类型的阶段
     * @param productId
     * @return
     */
    Integer delByProductId(@Param("productId") Integer productId);

    /**
     *
     * @param stageModels
     * @return
     */
    Integer updateList(@Param("stageModels") List<StageModel> stageModels, @Param("date") Date date, @Param("userId") Integer userId);

    Integer delByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据产品id和阶段获取列表
     * @param productIds
     * @param stageKey
     * @return
     */
    List<ProductStageEntity> getListByStage(@Param("productIds") List<Integer> productIds, @Param("stageKey") String stageKey);


    List<ProductStageEntity> selectByProductIds(@Param("productIds")List<Integer> productIds);

    List<ProductStageEntity> checkDelStage(@Param("productId")Integer productId,@Param("stageKeys")List<String> stageKeys);
}
