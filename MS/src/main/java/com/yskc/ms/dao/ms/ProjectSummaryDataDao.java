package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.ProjectSummaryData;
import com.yskc.ms.models.CustomerTotalModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.TotalCostModel;
import com.yskc.ms.models.projectAudit.ProjectSummaryDataModel;
import com.yskc.ms.models.projectsummary.ProjectSummaryModel;
import com.yskc.ms.models.projectsummary.QuerySummaryModel;
import com.yskc.ms.models.projectsummary.SummaryDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/10 11:29
 * description:
 */
@Repository
public interface ProjectSummaryDataDao extends BaseMapper<ProjectSummaryData> {
    /**
     * 获取高新项目数据
     * @param page
     * @param dataPerm
     * @param query
     * @return
     */
    List<ProjectSummaryModel> getHighTechData(@Param("page") Pagination page, @Param("dataPerm") DataPermModel dataPerm, @Param("query") QuerySummaryModel query);

    /**
     * 根据年份和customerId获取数据
     * @param list
     * @return
     */
    List<SummaryDataModel> getByCustomerYear(@Param("list") List<ProjectSummaryModel> list);

    /**
     * 通过年获取所有数据
     *
     * @param year
     * @return
     */
    List<ProjectSummaryData> getByYear(@Param("year") Integer year);

    CustomerTotalModel getTotal();

    /**
     * 获取审核数据统计
     * @param year
     * @param customerId
     * @return
     */
    ProjectSummaryDataModel getData(@Param("year") Integer year, @Param("customerId") Integer customerId);

    /**
     * 获取集团研发管理数据
     * @param month
     * @param year
     * @return
     */
    List<GroupRAndDManagementModel> getTableData(@Param("month") Date month, @Param("year") Integer year);

    /**
     * 获取集团研发管理去年研发费数据
     * @param month
     * @param year
     * @return
     */
    List<GroupRAndDManagementModel> getRDExpense(@Param("month") Date month, @Param("year") Integer year);

    /**
     * 获取集团研发管理统计
     * @param month
     * @param year
     * @return
     */
    TotalCostModel getTotalCast(@Param("month") Date month, @Param("year") Integer year);
}
