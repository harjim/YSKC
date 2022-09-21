package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.ProductStageListEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/16 10:38
 * description:
 */
@Repository
public interface ProductStageListDao extends BaseMapper<ProductStageListEntity> {
    /**
     * 获取项目资料列表
     * @param productId
     * @return
     */
    List<ProductStageListEntity> getByStageId(@Param("productId") Integer productId);

    /**
     * 根据方向所有资料
     * @param directionIds
     * @return
     */
    List<ProductStageListEntity> getByDirectionIds(@Param("directionIds") List<Integer> directionIds);
}
