package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.Project;
import com.yskc.ms.entity.ms.ProjectMember;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.customer.QueryCustomerModel;
import com.yskc.ms.models.dept.DeptCustomerModel;
import com.yskc.ms.models.params.ProjectParams;
import com.yskc.ms.models.project.ProjectInfoModel;
import com.yskc.ms.models.rs.QueryTechProjectModel;
import com.yskc.ms.models.rs.RsTechProjectModel;
import com.yskc.ms.models.tech.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao extends BaseMapper<Project> {

    List<ProjectInfoModel> getList(@Param("page") Pagination page, @Param("query") ProjectParams query, @Param("dataPerm") DataPermModel dataPerm);

    ProjectInfoModel getProject(@Param("projectId") Integer projectId);

    List<ProjectInfoModel> getProjectByCustomerIds(@Param("customerIds") List<Integer> customerIds, @Param("query") QueryCustomerModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取拥有的companyId
     *
     * @param dataPerm
     * @return
     */
    List<Integer> getOwnCompanyId(@Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<DeptCustomerModel> getAllCustomer();

    /**
     * count 客户
     *
     * @return
     */
    Integer countCustomer();

    /**
     * 通过项目id获取客户id
     *
     * @param ids
     * @return
     */
    List<ProjectMember> getCustomerIdsByIds(@Param("ids") List<Integer> ids);

    /**
     * 查询项目技改进度详情列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<ProjectTechProgressModel> getTechList(@Param("page") Pagination page, @Param("query") QueryTechModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 根据日志id获取项目
     *
     * @param lastLogId
     * @param projectId
     * @return
     */
    Project getByLogId(@Param("lastLogId") Integer lastLogId, @Param("projectId") Integer projectId);

    /**
     * 根据项目类型获取项目列表
     *
     * @param productId
     * @return
     */
    List<Project> getListByProduct(@Param("productId") Integer productId);

    /**
     * 获取技改项目列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<RsTechProjectModel> getTechProjects(@Param("page") Pagination page, @Param("query") QueryTechProjectModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 更新项目业务人员（部门， 谈单经理）
     *
     * @param project
     * @param projectIds
     * @return
     */
    int updateProjectOwerId(@Param("project") Project project, @Param("projectIds") List<Integer> projectIds);

    /**
     * 获取备案项目列表
     *
     * @param page
     * @param dataPerm
     * @param query
     * @return
     */
    List<BeianExportModel> getBeianProjects(@Param("page") Pagination page, @Param("dataPerm") DataPermModel dataPerm, @Param("query") QueryBeianModel query);
    /**
     * 获取备案项目列表导出
     *
     * @param dataPerm
     * @param query
     * @return
     */
    List<BeianExportModel> getBeianExport(@Param("dataPerm") DataPermModel dataPerm, @Param("query") QueryBeianModel query);

    /**
     * 获取创新项目id
     *
     * @param customerId
     * @param year
     * @return
     */
    List<Integer> getInnovationProjectIds(@Param("customerId") Integer customerId, @Param("year") Integer year);
}
