package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.ProductStageEntity;
import com.yskc.rs.models.tech.ProductStageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/16 10:37
 * description:
 */
@Repository
public interface ProductStageDao extends BaseMapper<ProductStageEntity> {

    List<ProductStageModel> getByProductIds(@Param("productId") Integer productId);

    /**
     * 获取方向阶段
     * @param directionId
     * @return
     */
    List<ProductStageModel> getByDirectionId(@Param("directionId") Integer directionId);
}
