package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.ReportEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.PlanInfo;

import java.util.List;

/**
 * @Author: zhangdingfu
 * @date: 2019-07-25
 */
public interface ReportService {

    /**
     * 设置计划数
     *
     * @param entity
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean setCnt(ReportEntity entity, UserInfo userInfo) throws OwnerException;

    PlanInfo getPlanInfo(Integer companyId, Integer ryear);

    List<String> getDeptIds(Integer companyId,Integer year);

    /**
     * 保存技术中心简介
     *
     * @param report
     * @param userInfo
     * @return
     */
    Boolean saveTechIntro(ReportEntity report, UserInfo userInfo) throws OwnerException;

    /**
     * 获取年技术中心简介
     *
     * @param companyId
     * @param year
     * @return
     */
    String getTechIntro(Integer companyId, Integer year);

    /**
     * 保存人员总数
     *
     * @param report
     * @param userInfo
     * @return
     */
    Boolean saveEmployeeAmount(ReportEntity report, UserInfo userInfo) throws OwnerException;
}
