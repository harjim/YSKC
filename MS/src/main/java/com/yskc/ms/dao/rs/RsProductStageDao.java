package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ProductAddStageModel;
import com.yskc.ms.entity.rs.RsProductStageEntity;
import com.yskc.ms.models.rs.RsProductStageModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/13 14:15
 * description:
 */
@Repository
public interface RsProductStageDao extends BaseMapper<RsProductStageEntity> {
    /**
     * 批量插入阶段
     * @param insertStages
     * @return
     */

    @Options(useGeneratedKeys = true)
    Integer insertList(List<RsProductStageEntity> insertStages);

    /**
     * 删除项目阶段
     * @param directionIds
     * @return
     */
    Integer delByDirectionIds(@Param("directionIds") List<Integer> directionIds);

    /**
     * 获取项目阶段
     * @param directionId
     * @return
     */
    List<RsProductStageModel> getByDirectionId(@Param("directionId") Integer directionId);

    /**
     * 根据方向ids获取阶段
     * @param directionIds
     * @return
     */
    List<RsProductStageModel> getByDirections(@Param("directionIds") List<Integer> directionIds);

    Integer updateList(@Param("models") List<ProductAddStageModel> models, @Param("date") Date date, @Param("userId") Integer userId);
}
