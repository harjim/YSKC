package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.QualityType;
import com.yskc.ms.models.qualityscore.QualityScoreInfoModel;
import com.yskc.ms.models.qualityscore.QueryQualityScoreModel;
import com.yskc.ms.models.qualityscore.ScoreTypeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/6/2 9:36
 * @Description:
 * @author: hsx
 */
@Repository
public interface QualityTypeDao extends BaseMapper<QualityType> {

    /**
     * 根据类型获取配置信息
     *
     * @param model
     * @return
     */
    QualityScoreInfoModel getConfig(@Param("model") QueryQualityScoreModel model);

    /**
     * 根据类型获取weight权值
     *
     * @param type
     * @return
     */
    QualityScoreInfoModel getWeightByType(@Param("type") Integer type);

    /**
     * 获取所有权重
     *
     * @return
     */
    List<ScoreTypeModel> getAllWeight();

    /**
     * 保存权值配置
     *
     * @param list
     * @param userId
     * @param now
     * @return
     */
    int saveWeight(@Param("list") List<ScoreTypeModel> list, @Param("userId") Integer userId, @Param("now") Date now);
}
