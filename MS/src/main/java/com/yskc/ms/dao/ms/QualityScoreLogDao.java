package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.QualityScoreLog;
import com.yskc.ms.models.qualityscore.QualityScoreLogModel;
import com.yskc.ms.models.qualityscore.QualityScoreModel;
import com.yskc.ms.models.qualityscore.QueryQualityScoreModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2022/6/10 10:33
 * @Description:
 * @author: hsx
 */
@Repository
public interface QualityScoreLogDao extends BaseMapper<QualityScoreLog> {
    /**
     * 批量添加评分历史记录
     * @param list
     * @return
     */
    Integer addList(@Param("list")List<QualityScoreLog> list);

    /**
     * 获取评分历史记录
     * @param model
     * @return
     */
    List<QualityScoreLogModel> getList(@Param("model") QueryQualityScoreModel model);

    /**
     * 修改评分记录
     * @param qualityScoreLog
     * @return
     */
    int editScore(@Param("qualityScoreLog") QualityScoreLog qualityScoreLog);

    /**
     * 获取所有的评分记录
     * @param pasModel
     * @param year
     * @return
     */
    List<QualityScoreModel> getScoreList(@Param("model") QualityScoreModel pasModel, @Param("year") Integer year);
}
