package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.QualityScore;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.qualityscore.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/5/13 9:44
 * @Description:
 * @author: hsx
 */
@Repository
public interface QualityScoreDao extends BaseMapper<QualityScore> {

    Integer addScoreList(@Param("list")List<QualityScore> list);


    /**
     * 获取导出列表数据
     * @param model
     * @param dataPerm
     * @return
     */
    List<ExportScoreModel> getExportList(QueryScoreCollectModel model, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取审核序时账数据
     * @param page
     * @param model
     * @param dataPerm
     * @return
     */
    List<ScoreCollectModel> getCollectList(@Param("page") Pagination page, @Param("model") QueryScoreCollectModel model,
            @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 修改是否最终评分
     * @param qualityScore
     * @return
     */
    int editScore(@Param("qualityScore") QualityScore qualityScore);

    /**
     * 获取表头统计数据model
     * @param model
     * @param dataPerm
     * @return
     */
    ScoreHeaderModel getTotal(@Param("model") QueryScoreCollectModel model,@Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取下拉数据
     * @param page
     * @param model
     * @param month
     * @param dataPerm
     * @return
     */
    List<ScoreModel> getScoreList(@Param("page") Pagination page, @Param("model") QueryScoreModel model,
                                  @Param("month") Date month,@Param("dataPerm") DataPermModel dataPerm);

    /**
     * 批量修改评分记录完成情况
     * @param model
     * @param id
     * @param date
     * @return
     */
    int editCompletion(@Param("model") CompletionEditModel model, @Param("ids") Integer id, @Param("date") Date date);

    /**
     * 根据Id获取type
     * @param qualityId
     * @return
     */
    Integer getTypeById(@Param("id") Integer qualityId);
}
