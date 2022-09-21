package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.TrialProdEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.trialprod.*;

import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TrialProdService {
    /**
     * 新增试制记录
     * @param userInfo
     * @param entity
     * @return
     */
    boolean addTrial(UserInfo userInfo, TrialProdEntity entity);

    /**
     * 修改试制记录
     * @param userInfo
     * @param entity
     * @return
     */
    boolean editTrial(UserInfo userInfo, TrialProdEntity entity);

    /**
     * 删除试制记录
     * @param entity
     * @return
     */
    boolean delTrial(TrialProdEntity entity,UserInfo userInfo) throws OwnerException;

    PageModel<List<TrialProdModel>> queryTrial(Integer companyId, QueryTrialModel queryTrialModel);

    /**
     * 获取试制工时统计
     * @param companyId
     * @param projectId
     * @return
     */
       List<StageTrialShowModel> getSummaryList(Integer companyId, Integer projectId,Integer pDocFileId);

    /**
     * 获取未使用的试制
     * @param companyId
     * @param projectId
     * @return
     */
    List<StageTrialModel> getDocFileTrials(Integer companyId, Integer projectId,String stage);

    /**
     * 获取文档试制列表
     * @param companyId
     * @param docFileId
     * @return
     */
    Map<String,Object> queryDocFileTrial(Integer companyId, Integer docFileId, Integer projectId, Date month) throws OwnerException;



    /**
     * 查询项目试制计划
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<TrialProdPlanModel>> getTrialPlan(Integer companyId, QueryTrialPlanModel query);

    /**
     * 导出试制计划
     * @param userInfo
     * @param out
     * @throws OwnerException
     * @throws ParseException
     */
    void exportTrialProdPlan(UserInfo userInfo, OutputStream out,QueryTrialPlanModel queryTrialPlanModel) throws OwnerException, ParseException;

    /**
     * 获取试制材料计划表数据
     * @param model
     * @return
     */
    PageModel<List<MaterialPlanModel>> getTrialData(QueryMaterialTrackModel model);
}
