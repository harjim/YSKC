package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.DistrictSummary;
import com.xxl.job.executor.entity.ms.Project;
import com.xxl.job.executor.entity.ms.ProjectProgressDetail;
import com.xxl.job.executor.models.ProjectInfo.ProjectCustomerModel;
import com.xxl.job.executor.models.h3.ProjectMemberModel;
import com.xxl.job.executor.models.projectsummary.ProjectSummaryModel;
import com.xxl.job.executor.models.techproject.CustomerIdYearModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ProjectDao extends BaseMapper<Project> {
    void batchUpdateMembers(@Param("projectId") int projectId, @Param("mTypes") List<Integer> mTypes, @Param("memberList") List<ProjectMemberModel> memberList);

    /**
     * 获取公司id
     *
     * @return
     */
    //List<Integer> getCompanyIds();

    /**
     * 获取所有数据
     *
     * @param companyIds
     * @return
     */
    List<ProjectSummaryModel> getByCompanyIds(@Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取companyId
     *
     * @param deptIds
     * @return
     */
    List<DistrictSummary> getCustomerByDeptIds(@Param("deptIds") List<Integer> deptIds);

    /**
     * 获取最新创建的项目公司id
     *
     * @param lastTime
     * @return
     */
    List<Integer> getLastCreateCompanyIds(@Param("lastTime") Date lastTime);

    /**
     * 通过companyId + year 获取存在项目
     *
     * @param list
     * @return
     */
    List<ProjectProgressDetail> getRdByCompanyYear(@Param("list") List<ProjectProgressDetail> list);

    /**
     * 批量插入
     *
     * @param projects
     * @return
     */
    @Options(useGeneratedKeys = true)
    int insertProject(List<Project> projects);

    /**
     * 获取项目人员+
     *
     * @param projectIds
     * @return
     */
    List<ProjectMemberModel> getProjectMembers(@Param("projectIds") Set<Integer> projectIds);

    /**
     * 插入项目成员
     *
     * @param projectMembers
     * @return
     */
    int insertMembers(@Param("projectMembers") List<ProjectMemberModel> projectMembers);

    /**
     * 获取客户id，项目年份
     *
     * @param ids
     * @return
     */
    List<CustomerIdYearModel> getCustomerByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取加计扣除项目列表
     * @return
     */
    List<ProjectCustomerModel> queryCompany();

    /**
     * 获取公司id + 项目年份
     * @param list
     * @return
     */
    List<ProjectCustomerModel> getCompanyIdYear(@Param("list") List<ProjectCustomerModel> list);

    void insertPatentDemand(@Param("customerId")Integer customerId, @Param("year")Integer year, @Param("ownerId")Integer owerId, @Param("engineerId")Integer engineerId, @Param("remark")String remark);
}
