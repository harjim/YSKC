package com.yskc.rs.service;

import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.ProjectInfoSummaryModel;
import com.yskc.rs.models.projectSummary.PolicySummaryModel;
import com.yskc.rs.models.projectSummary.SituationSummaryModel;
import com.yskc.rs.models.projectSummary.StandardModel;
import com.yskc.rs.models.trialprod.StageTrialModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 13:38
 * @Description:
 */
public interface SituationSummaryService {
    /**
     * 查询项目情况总结
     */
    SituationSummaryModel querySituation(Integer year, Integer companyId);

    /**
     * 保存项目情况总结
     *
     * @param model
     * @param userInfo
     * @return
     */
    Integer saveSituation(SituationSummaryModel model, UserInfo userInfo);

    /**
     * 获取标准化实施情况
     *
     * @param year
     * @param companyId
     * @return
     */
    StandardModel queryStandard(Integer year, Integer companyId);

    /**
     * 保存标准化实施情况
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveStandardInfo(StandardModel model, UserInfo userInfo);

    /**
     * 查询政策及要求情况汇总
     *
     * @return
     */
    PolicySummaryModel queryPolicy();

    /**
     * 保存政策及要求情况
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean savePolicy(PolicySummaryModel model, UserInfo userInfo);

    /**
     * 获取项目信息汇总
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectInfoSummaryModel> getSummaryInfo(Integer year, Integer companyId);

    /**
     * 项目决算信息
     *
     * @param projectId
     * @return
     */
    Map<String, Object> getBudgetCost(Integer projectId);

    /**
     * 项目预算信息
     *
     * @param projectId
     * @return
     */
    Map<String, Map<String,Object>> getBudgetInfo(Integer projectId,Integer year);

    /**
     * 获取人员研发工时
     *
     * @param year
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getStaffHour(Integer year, Integer companyId) throws Exception;

    /**
     * 获取仪器设备工时
     *
     * @param year
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getEquipmentHour(Integer year, Integer companyId) throws Exception;

    /**
     * 获取原材料用料
     *
     * @param year
     * @param companyId
     * @param type
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getMaterialRaw(Integer year, Integer type, Integer companyId) throws Exception;

    /**
     * 获取工艺装备用料
     *
     * @param year
     * @param companyId
     * @param type
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getMaterialAuxiliary(Integer year, Integer type, Integer companyId) throws Exception;

    /**
     * 获取辅料
     *
     * @param year
     * @param companyId
     * @param type
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getMaterialReserve(Integer year, Integer type, Integer companyId) throws Exception;

    /**
     * 获取排期表
     *
     * @param year
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getYieldAmount(Integer year, Integer companyId) throws Exception;

    /**
     * 获取项目归集信息
     *
     * @param year
     * @param companyId
     * @return
     */
    Map<Integer, Map<Object, BigDecimal>> getProjectSummary(Integer year, Integer companyId);

    /**
     * 获取排期表详情
     *
     * @param model
     * @return
     */
    PageModel<List<StageTrialModel>> getYieldInfo(QueryMaterialTrackModel model);

    /**
     * 获取材料/备品/辅料数据
     *
     * @param model
     * @return
     */
    PageModel<List<MaterialPlanModel>> getMaterials(QueryMaterialTrackModel model);

    /**
     * 获取研发材料归集费用
     *
     * @param type
     * @param year
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getMaterialSummary(int type, int year, Integer companyId);
}
