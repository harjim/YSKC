package com.yskc.rs.service;

import com.yskc.common.model.PageModel;
import com.yskc.rs.models.hightech.*;

import java.util.List;
import java.util.Map;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightechprogress.HighTechProjectModel;
import com.yskc.rs.models.hightechprogress.HighTechTotalModel;
import com.yskc.rs.models.hightechprogress.QueryHighTechFileModel;
import com.yskc.rs.models.hightechprogress.TechAuditBatchModel;
import com.yskc.rs.models.project.DeliverLogModel;

/**
 * @Author: wjy
 * @DateTime: 2022/5/18 9:07
 * @Description:
 */
public interface HighTechProgressService {

    /**
     * 根据年份查管理的公司高新进度
     * @param companyIds
     * @param query
     * @return
     */
    PageModel<List<HighTechProgressModel>> getList(List<Integer> companyIds, QueryHighTechProgModel query);

    /**
     * 根据年份查公司高新进度统计明细
     * @param companyIds
     * @param year
     * @return
     */
    List<HighTechTotalModel> getTotalList(List<Integer> companyIds, Integer year);
    /**
     * 提交高新明细审核
     * @param query
     * @return
     */
    Boolean submitAudit(QueryHighTechAuditModel query, UserInfo info) throws OwnerException;
    /**
     * 高新明细分配人员
     * @param model
     * @param userInfo
     * @return
     */
    Boolean allot(HighTechAllotModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 查询日志
     * @param model
     * @return
     */
    List<DeliverLogModel> getLogList(QueryHighTechAuditModel model) throws OwnerException;

    /**
     * 公司分类查询高新进度明细
     * @param model
     * @param userInfo
     * @return
     */
    Map<String, List<HighTechProjectModel>> getProjectList(QueryHighTechFileModel model, UserInfo userInfo);

    /**
     * 高新进度技术文档批量审核
     * @param model
     * @param userInfo
     * @return
     */
    Boolean submitsAudit(TechAuditBatchModel model, UserInfo userInfo) throws OwnerException;
}
