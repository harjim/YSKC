package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.RdEmployeeEntity;
import com.yskc.rs.models.employee.*;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.init.member.ProjectEmployeeModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.projectattendance.DataEmployeeQuery;
import com.yskc.rs.models.projectrdemployee.QueryProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdemployee.RdEmployeeAggModel;
import com.yskc.rs.models.rdemployeehour.QueryRdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourProjectModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-18 14:21:04
 */
@Repository
public interface RdEmployeeDao extends BaseMapper<RdEmployeeEntity> {

    /**
     * 获取研发花名册信息
     *
     * @param companyId
     * @param page
     * @param termModel
     * @return
     */
    List<RdEmployeeModel> getRdEmployeeData(@Param("companyId") Integer companyId, Pagination page, @Param("termModel") EmployeeTermModel termModel);

    /**
     * 导出研发花名册信息
     * @param companyId
     * @param termModel
     * @return
     */
    List<RdEmployeeExportModel> getRdEmployeeData(@Param("companyId") Integer companyId, @Param("termModel") EmployeeTermModel termModel);

    Integer addRdEmployeeBatch(@Param("entityLists") List<RdEmployeeEntity> entityLists);

    /**
     * 获取是否使用
     *
     * @param companyId
     * @param ids
     * @param year
     * @return
     */
    List<Integer> getData(@Param("companyId") int companyId, @Param("ids") List<Integer> ids, @Param("year") Integer year);

    /**
     * 批量修改人员类型
     *
     * @param etype
     * @param ids
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    Integer setRdEmployeeEtype(@Param("etype") Integer etype, @Param("ids") List<Integer> ids,
                               @Param("userId") Integer userId, @Param("msUserId") Integer msUserId,
                               @Param("now") Date now);

    /**
     * 批量修改研发部门
     *
     * @param rdDeptId
     * @param ids
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    Integer setRdEmployeeDeptId(@Param("rdDeptId") Integer rdDeptId, @Param("ids") List<Integer> ids,
                                @Param("userId") Integer userId, @Param("msUserId") Integer msUserId,
                                @Param("now") Date now);

    /**
     * 导出研发花名册
     *
     * @param companyId
     * @param termModel
     * @return
     */
    List<EmployeeExcel> deriveRdEmployee(@Param("companyId") Integer companyId, @Param("termModel") EmployeeTermModel termModel);

    /**
     * 通过年份 + 工号查询
     *
     * @param companyId
     * @param year
     * @param numberList
     * @return
     */
    List<String> getByEnumbers(@Param("companyId") Integer companyId, @Param("year") Integer year,
                               @Param("numberList") List<String> numberList);

    List<RdEmployeeEntity> getListByEnumbers(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                             @Param("numberList") List<String> numberList);

    /**
     * 获取部门职位
     * @param companyId
     * @param year
     * @param numberList
     * @return
     */
    List<RdEmployeeModel> getDeptByEnumbers(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                             @Param("numberList") List<String> numberList);

    List<RdEmployeeModel> queryByCompanyIdAndYear(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取研发人员下拉列表
     *
     * @param companyId
     * @param year
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getEmployeeSelect(@Param("companyId") Integer companyId,
                                                @Param("year") Integer year, @Param("ename") String ename);

    /**
     * 获取研发人员列表
     *
     * @param companyId
     * @param projectIds
     * @return
     */
    List<ProjectEmployeeModel> getEmployeeList(@Param("companyId") Integer companyId,
                                               @Param("projectIds") List<Integer> projectIds);

    /**
     * 获取普通人员下拉
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getBaseEmployeeSelect(@Param("companyId") Integer companyId, @Param("ename") String ename);

    /**
     * 根据工号获取人员列表
     *
     * @param companyId
     * @param enumbers
     * @return
     */
    List<EmployeeAutographModel> getEmployeeByEnumber(@Param("companyId") Integer companyId, @Param("enumbers") List<String> enumbers);

    /**
     * 获取项目人员列表
     *
     * @param page
     * @param query
     * @return
     */
    List<ProjectEmployeeInfo> getProjectEmployeeInfos(@Param("page") Pagination page,
                                                      @Param("query") DataEmployeeQuery query);

    /**
     * 获取会议签到项目人员列表
     *
     * @param page
     * @param query
     * @param companyId
     * @param year
     * @return
     */
    List<ProjectEmployeeInfo> getEmployeeInfos(@Param("page") Pagination page,@Param("companyId") Integer companyId,
                                                      @Param("query") QueryReviewModel query,@Param("year")Integer year);

    /**
     * 添加导入研发花名册列表
     *
     * @param rdEmployeeEntities
     * @return
     */
    Integer saveRdEmployeeLists(@Param("RdEmployeeEntitys") List<RdEmployeeEntity> rdEmployeeEntities);

    Boolean updateList(@Param("entities") List<RdEmployeeEntity> entities);

    /**
     * 批量设置研发人员职位
     *
     * @param position
     * @param ids
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    Integer setRdEmployeePosition(@Param("position") String position, @Param("ids") List<Integer> ids, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("now") Date now);

    /**
     * 获取项目rdTitle，并获取项目周期
     *
     * @param enumbers
     * @param companyId
     * @param year
     * @param needTerm  需要区间
     * @return
     */
    List<FullYearProjectModel> getRdTitles(@Param("enumbers") List<String> enumbers, @Param("companyId") Integer companyId,
                                           @Param("year") Integer year, @Param("needTerm") Boolean needTerm);

    /**
     * 获取研发花名册
     *
     * @param companyId
     * @param page
     * @param query
     * @return
     */
    List<RdEmployeeAggModel> getRdEmployeeRdsList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                  @Param("query") QueryProjectRdEmployeeModel query);

    /**
     * 获取研发人员和评审委员
     *
     * @param companyId
     * @param year
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getUnionEmployee(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("ename") String ename);

    /**
     * 获取研发人员数量
     *
     * @param companyId
     * @param year
     * @return
     */
    Integer countNum(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 按年统计enumber数
     *
     * @param companyId
     * @param year
     * @return
     */
    Integer countYearEnumber(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取研发人员工时
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<RdEmployeeHourModel> getRdEmployeeHours(@Param("page") Pagination page,
                                                 @Param("query") QueryRdEmployeeHourModel query,
                                                 @Param("companyId") Integer companyId);

    /**
     * 获取关联项目及加入日期,总工时
     *
     * @param enumbers
     * @param year
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<RdEmployeeHourProjectModel> getRdsAndEntryDate(@Param("enumbers") Set<String> enumbers,
                                                        @Param("year") Integer year, @Param("companyId") Integer companyId,
                                                        @Param("begin") Date begin, @Param("end") Date end);
}
