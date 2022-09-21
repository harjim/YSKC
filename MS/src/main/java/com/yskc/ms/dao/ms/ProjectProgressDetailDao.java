package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.ProjectProgressDetail;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.project.*;
import com.yskc.ms.models.projectsummary.ProjectSummaryTotal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/15 13:37
 */
@Repository
public interface ProjectProgressDetailDao extends BaseMapper<ProjectProgressDetail> {

    List<ProjectStageModel> getDetailList(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取项目进度列表
     *
     * @param page
     * @param query
     * @return
     */
    List<ProjectProgressModel> getList(@Param("page") Pagination page, @Param("query") QueryProjectProgressModel query, @Param("dataPerm") DataPermModel dataPerm);


    /**
     * 获取项目进度列表
     *
     * @param query
     * @return
     */
    List<ProjectProgressModel> getList(@Param("query") QueryProjectProgressModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 根据公司和年获取人员和设备统计信息
     * @param companyId
     * @param year
     * @return
     */
    ProjectProgressModel countRdInfo(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 归集汇总统计
     *
     * @param query
     * @param dataPerm
     * @return
     */
    ProjectSummaryTotal getTotal(@Param("query") QueryProjectProgressModel query, @Param("dataPerm") DataPermModel dataPerm);
}
