package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.QualityScoreMonth;
import com.yskc.ms.models.qualityscore.QualityScoreMonthModel;
import com.yskc.ms.models.qualityscore.QueryQualityScoreModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2022/6/2 11:19
 * @Description:過程文件月份組
 * @author: hsx
 */
@Repository
public interface QualityScoreMonthDao extends BaseMapper<QualityScoreMonth> {

    /**
     * 根据月份组获取对应次数和工程师Id
     * @param model
     * @return
     */
    List<QualityScoreMonthModel> getList(@Param("model") QueryQualityScoreModel model);
}
