package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.RdEmployeePlanEntity;
import com.yskc.rs.models.employeePlan.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/11/16 17:18
 * description:
 */
@Repository
public interface RdEmployeePlanDao extends BaseMapper<RdEmployeePlanEntity> {
    /**
     * 批量插入或更新更新
     *
     * @param list
     * @return
     */
    Integer insertOrUpdate(@Param("list") List<RdEmployeePlanEntity> list);

    /**
     * 查询项目成员
     *
     * @param query
     * @param companyId
     * @param page
     * @return
     */
    List<PlanTimeModel> getList(@Param("query") QueryPlanModel query, @Param("companyId") Integer companyId, @Param("page") Pagination page);

    /**
     * 获取项目成员计划工时
     *
     * @param enumbers
     * @param companyId
     * @param projectId
     * @param begin
     * @param end
     * @return
     */
    List<RdEmployeePlanModel> getByEnumbers(@Param("enumbers") List<String> enumbers,
                                            @Param("companyId") Integer companyId,
                                            @Param("projectId") Integer projectId,
                                            @Param("begin") Date begin,
                                            @Param("end") Date end);

    /**
     * 通过月份获取数据
     *
     * @param companyId
     * @param months
     * @param year
     * @return
     */
    List<ExportRdEmployeePlanModel> getByMonths(@Param("companyId") Integer companyId, @Param("months") Date[] months,
                                                @Param("year") Integer year);

    /**
     * 获取人员计划工时汇总
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<RdEmployeePlanTotalModel> getTotalList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                @Param("query") QueryPlanModel query);

    List<Map<String, Object>> getProjectPlanTime(@Param("list") List<RdEmployeePlanTotalModel> list,@Param("companyId") Integer companyId );

    /**
     * 获取研发计划
     *
     * @param companyId
     * @param year
     * @param month
     * @param etypes
     * @return
     */
    List<RdEmployeePlanModel> getRdPlans(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                         @Param("month") Date month, @Param("etypes") List<Integer> etypes);
}
