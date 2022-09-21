package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.InnovationProject;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.TotalCostModel;
import com.yskc.ms.models.innovationproject.InnovationMemberModel;
import com.yskc.ms.models.innovationproject.InnovationProjectModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.rdfunds.QueryFundsModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 9:50
 * @Description:
 */
@Repository
public interface InnovationProjectDao extends BaseMapper<InnovationProject> {
    /**
     * 获取创新列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<InnovationProjectModel> getList(@Param("page") Pagination page, @Param("query") QueryProjectProgressModel query,
                                         @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 查询项目组成员
     *
     * @param ids
     * @return
     */
    List<InnovationMemberModel> queryMemberByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取统计数据
     *
     * @param query
     * @param dataPerm
     * @return
     */
    InnovationProjectModel getTotal(@Param("query") QueryProjectProgressModel query, @Param("dataPerm") DataPermModel dataPerm);


    /**
     * 获取存在的项目id
     *
     * @param ids
     * @return
     */
    Integer getExistProjectId(@Param("ids") List<Integer> ids);

    /**
     * 通过原项目获取创新项目id
     *
     * @param projectIds
     * @return
     */
    List<Integer> getInnovationIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取创新项目通过customerId + year
     *
     * @param customerId
     * @param year
     * @return
     */
    InnovationProject getInnovationProject(@Param("customerId") Integer customerId, @Param("year") Integer year);

    /**
     * 获取创新项目报表数据
     *
     * @param page
     * @param model
     * @param dataPerm
     * @return
     */
    List<GroupRAndDManagementModel> getTableData(@Param("page") Pagination page, @Param("model") QueryFundsModel model,
                                                 @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取并导出创新项目报表
     *
     * @param model
     * @param dataPerm
     * @return
     */
    List<GroupRAndDManagementModel> getTableData(@Param("model") QueryFundsModel model,
                                                 @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取创新项目报表去年研发费数据
     *
     * @param model
     * @param customerIds
     * @return
     */
    List<GroupRAndDManagementModel> getRDExpense(@Param("model") QueryFundsModel model, @Param("customerIds") List<Integer> customerIds);

    /**
     * 获取报表统计
     *
     * @param page
     * @param model
     * @param dataPerm
     * @return
     */
    TotalCostModel getTotalCast(@Param("page") Pagination page, @Param("model") QueryFundsModel model,
                                @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取并导出报表统计
     *
     * @param model
     * @param dataPerm
     * @return
     */
    TotalCostModel getTotalCast(@Param("model") QueryFundsModel model,
                                @Param("dataPerm") DataPermModel dataPerm);
}
