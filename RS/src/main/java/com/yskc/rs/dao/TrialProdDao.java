package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.TrialProdEntity;
import com.yskc.rs.models.trialprod.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrialProdDao extends BaseMapper<TrialProdEntity> {
    List<TrialProdModel> queryTrial(Pagination page,@Param("companyId") Integer companyId, @Param("query")QueryTrialModel query);

    List<TrialProdSumarryModel> getSummaryList(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    List<TrialProdPlanModel> queryTrialPlan(Pagination page,@Param("companyId") Integer companyId, @Param("query") QueryTrialPlanModel query);

    List<TrialProdPlanModel> exportTrialPlanData(@Param("companyId") Integer companyId,@Param("query") QueryTrialPlanModel query);


    /**
     * 更新所有column
     * @param trial
     * @return
     */
    int updateAllColumn(@Param("trial") TrialProdEntity trial);

    List<StageTrialModel> queryListByStage(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("startDate")Date startDate,@Param("endDate")Date endDate);


    /**
     * 查询文档试制
     * @param companyId
     * @param docFileIds
     * @param projectId
     */
    List<StageTrialModel> queryDocFileTrial(@Param("companyId")Integer companyId,
                                            @Param("docFileIds") List<Integer> docFileIds,@Param("projectId")Integer projectId);


    List<TrialProdEntity> getRecordsByProject(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 查询文档试制
     * @param projectId
     * @param companyId
     */
    List<StageTrialModel> getTrialByProject(@Param("projectId") Integer projectId,
                                            @Param("companyId") Integer companyId);

    /**
     * 获取排期表详情
     * @param page
     * @param projectId
     * @param month
     * @return
     */
    List<StageTrialModel> getTrialInfo(@Param("page") Pagination page, @Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 根据月获取项目试制
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    List<StageTrialModel> getTrialByMonth(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 根据月获取项目试制
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    List<StageTrialModel> chooseGetTrial(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 根据月份获取试制数据
     * @param companyId
     * @param monthes
     * @param projectId
     * @return
     */
    List<StageTrialModel> getTrials(@Param("companyId") Integer companyId, @Param("monthes") List<Date> monthes, @Param("projectId") Integer projectId);
}
