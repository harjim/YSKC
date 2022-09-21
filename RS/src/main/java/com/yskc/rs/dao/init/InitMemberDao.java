package com.yskc.rs.dao.init;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.init.InitMemberEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.employee.ExportEmployeeModel;
import com.yskc.rs.models.init.member.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-23 16:40:58
 */
@Repository
public interface InitMemberDao extends BaseMapper<InitMemberEntity> {

    /**
     * 获取项目成员列表
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<InitMemberModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryProjectInitMemberModel query);

    /**
     * 获取人员列表
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<InitMemberModel> getEmployeeList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryInitMemberModel query);

    /**
     * 通过enumber获取
     *
     * @param companyId
     * @param projectId
     * @param enumbers
     * @param year      在业务未完成修改前可空
     * @return
     */
    List<InitMemberEntity> getByEnumbers(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("enumbers") List<String> enumbers, @Param("year") Integer year);

    /**
     * 批量新增人员
     *
     * @param initMemberList
     * @return
     */
    int addbatch(@Param("initMemberList") List<InitMemberEntity> initMemberList);

    /**
     * 获取申报项目关联的成员信息
     *
     * @param companyId
     * @param reportId
     * @return
     */
//    List<ProjectMember> getProjectMembers(@Param("companyId") Integer companyId, @Param("reportId") Integer reportId);

    /**
     * 批量设置项目角色
     *
     * @param role
     * @param ids
     * @return
     */
    Integer updateInitMemberRole(@Param("role") String role, @Param("ids") List<Integer> ids);

    List<InitMemberEntity> queryIMemberByProjectIds(@Param("projectIds") List<Integer> projectIds);

    List<InitMemberEntity> queryByProjectIdAndEnumber(@Param("projectId") Integer projectId,
                                                      @Param("masterENumber") String masterENumber,
                                                      @Param("year") Integer year);

    /**
     * 清除项目的负责人
     *
     * @param projectId
     * @param now
     * @param msUserId
     * @param userId
     * @param year      按年清除
     * @return
     */
    int cleanProjectMaster(@Param("projectId") Integer projectId, @Param("now") Date now,
                           @Param("msUserId") Integer msUserId, @Param("userId") Integer userId, @Param("year") Integer year);

    /**
     * 更新项目负责人
     *
     * @param now
     * @param msUserId
     * @param userId
     * @param id
     * @return
     */
    int updateProjectMaster(@Param("now") Date now,
                            @Param("msUserId") Integer msUserId,
                            @Param("userId") Integer userId,
                            @Param("id") Integer id,
                            @Param("year") Integer year);

    /**
     * 更新项目人员加入日期
     *
     * @param now
     * @param userId
     * @param msUserId
     * @param ids
     * @param entryDate
     * @return
     */
    int updateEntryDate(@Param("now") Date now, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId,
                        @Param("ids") List<Integer> ids, @Param("entryDate") Date entryDate);

    /**
     * 获取项目人员下拉
     *
     * @param companyId
     * @param projectId
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getMemberSelect(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                                              @Param("ename") String ename, @Param("year") Integer year);

    /**
     * 获取项目组成员(名称，limit20)
     *
     * @param projectId
     * @return
     */
    List<InitMemberModel> getMemberEname(@Param("projectId") Integer projectId, @Param("year") Integer year);

    List<InitMemberModel> getMemberEnames(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);

    /**
     * 根据项目id获取项目成员信息
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<InitMemberListModel> getStaffInfoList(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId, @Param("year") Integer year, @Param("memberIds") List<Integer> memberIds);

    /**
     * 根据项目id获取项目成员信息
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<InitMemberListModel> getStaffsInfo(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId, @Param("year") Integer year, @Param("memberIds") Integer[] memberIds);

    /**
     * 获取项目成员数据
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ExportEmployeeModel> getExportDatas(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 通过id 获取enumber
     *
     * @param ids
     * @return
     */
    List<InitMemberEntity> getEnumbersByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取存在的数据
     *
     * @param companyId
     * @param keysAndIds
     * @param year
     * @return
     */
    List<InitMemberEntity> getExist(@Param("companyId") Integer companyId, @Param("keysAndIds") KeysAndIdsModel keysAndIds,
                                    @Param("year") Integer year);

    /**
     * 获取可添加的项目成员列表
     *
     * @param page
     * @param companyId
     * @param query
     * @param existIds
     * @param year
     * @return
     */
    List<InitMemberModel> getAllStaff(@Param("page") Pagination page,
                                      @Param("companyId") Integer companyId,
                                      @Param("query") QueryInitMemberModel query,
                                      @Param("existIds") List<Integer> existIds,
                                      @Param("year") Integer year);

    /**
     * 获取项目研究人员和技术人员
     *
     * @param projectId
     * @return
     */
    List<InitMemberModel> getInitMemberByType(@Param("projectId") Integer projectId);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<InitMemberEntity> list);


    /**
     * 批量更新项目负责人
     *
     * @param updateIds
     * @param date
     * @param userId
     * @param msUserId
     * @return
     */
    Boolean updateMasters(@Param("updateIds") List<Integer> updateIds, @Param("date") Date date, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 根据年获取项目成员列表
     *
     * @param sourceYear
     * @param projectId
     * @return
     */
    List<InitMemberEntity> getMembers(@Param("sourceYear") Integer sourceYear, @Param("projectId") Integer projectId);

    /**
     * 获取可添加的项目成员
     *
     * @param sourceYear
     * @param projectId
     * @param companyId
     * @param targetYear
     * @return
     */
    List<InitMemberEntity> getAddMembers(@Param("sourceYear") Integer sourceYear, @Param("projectId") Integer projectId,
                                         @Param("companyId") Integer companyId, @Param("targetYear") Integer targetYear);

    /**
     * 获取归集的人员
     *
     * @param enumbers
     * @param monthLastDay
     * @param year
     * @param companyId
     * @return
     */
    List<InitMemberModel> getAggMembers(@Param("enumbers") List<String> enumbers, @Param("monthLastDay") Date monthLastDay,
                                        @Param("month") Date month, @Param("year") int year, @Param("companyId") Integer companyId);

    /**
     * 获取有项目成员的年份
     *
     * @param year
     * @param projectId
     * @return
     */
    List<Integer> getYears(@Param("year") Integer year, @Param("projectId") Integer projectId);

    /**
     * 删除负责人
     *
     * @param projectIds
     * @return
     */
    int deleteMaster(@Param("projectIds") List<Integer> projectIds);

    /**
     * 清除项目负责人（按年）
     *
     * @param projectIds
     * @param year
     * @return
     */
    Boolean cleanMasters(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year,
                         @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date);

    /**
     * 删除项目负责人
     *
     * @param dels
     * @param year
     * @return
     */
    Integer delMasterByProject(@Param("dels") List<ProjectEntity> dels, @Param("year") Integer year);

    /**
     * 按年获取项目负责人
     *
     * @param projectId
     * @param year
     * @return
     */
    InitMemberEntity getMasterByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 按年获取项目负责人
     *
     * @param projectId
     * @param year
     * @return
     */
    String getMasterNameByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 根据项目id和年份获取当前员工人数
     *
     * @param projectIds
     * @param year
     * @return
     */
    @MapKey("projectId")
    Map<Integer, Map<String, Long>> getMemberByProAndYear(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);

    /**
     * count年份
     *
     * @param beginYear
     * @param endYear
     * @param projectId
     * @return
     */
    Integer getExistYear(@Param("beginYear") int beginYear, @Param("endYear") int endYear, @Param("projectId") int projectId);

    /**
     * 通过类型获取人员
     *
     * @param companyId
     * @param year
     * @param etype
     * @param month
     * @return
     */
    List<ProjectEmployeeModel> getByEtypes(@Param("companyId") Integer companyId,@Param("year") Integer year,
                                           @Param("etype") Integer etype,@Param("month")Date month);
}
