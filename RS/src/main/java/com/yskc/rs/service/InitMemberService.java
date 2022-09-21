package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.InitMemberExcel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.InitUsedRoleModel;
import com.yskc.rs.models.init.member.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:17
 * @Description: 初始化项目人员列表业务层接口
 */
public interface InitMemberService {

    /**
     * 获取项目人员列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InitMemberModel>> getList(Integer companyId, QueryProjectInitMemberModel query);

    /**
     * 获取非当前项目人员列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InitMemberModel>> getEmployeeList(Integer companyId, QueryInitMemberModel query);

    /**
     * 批量新增
     *
     * @param userInfo
     * @param batchInitMemberModel
     * @return
     * @throws OwnerException
     */
    boolean addList(UserInfo userInfo, BatchInitMemberModel batchInitMemberModel) throws OwnerException;

    /**
     * 删除人员清单-人员
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean del(InitMemberModel model,UserInfo info)throws OwnerException;

    /**
     * 批量设置项目角色
     *
     * @param model
     * @return
     */
    boolean updateInitMemberRole(InitUsedRoleModel model);

    /**
     * 导入项目人员
     *
     * @param userInfo
     * @param data
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    Boolean importMember(UserInfo userInfo, List<InitMemberExcel> data, Integer year, Integer projectId) throws OwnerException;

    /**
     * 批量删除项目人员
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean delInitMembers(InitUsedRoleModel model,UserInfo info)throws OwnerException;

    /**
     * 设置项目负责人
     *
     * @param userInfo
     * @param memberModel
     * @return
     * @throws OwnerException
     */
    boolean setMaster(UserInfo userInfo, InitMemberModel memberModel) throws OwnerException;

    /**
     * 设置项目人员加入日期
     * @param userInfo
     * @param model
     * @throws OwnerException
     * @return
     */
    boolean setEntryDate(UserInfo userInfo, InitUsedRoleModel model)throws OwnerException;

    /**
     * 检查项目成员是否已经归集研发费用
     * @param model
     * @return
     */
    String checkRdUsed(InitUsedRoleModel model);

    /**
     * 根据项目id获取项目成员信息列表
     * @param projectId
     * @param companyId
     * @param memberIds
     * @param addData
     * @return
     */
    List<InitMemberListModel> getStaffInfoList(Integer projectId,Integer companyId,Integer[] memberIds,Boolean addData,Integer pDocFileId);

    /**
     * 获取可添加的项目成员
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InitMemberModel>> getAllStaff(Integer companyId, QueryInitMemberModel query);

    /**
     * 引入项目成员
     * @param userInfo
     * @param model
     * @return
     */
    boolean importMembers(UserInfo userInfo, ImportProjectInfoModel model) throws OwnerException;

    /**
     * 获取可引入项目成员的年份列表
     * @param year
     * @param projectId
     * @return
     */
    List<Integer> getMembersYear(Integer year, Integer projectId);

    /**
     * 根据项目id和年份获取当前员工人数
     *
     * @param projectIds
     * @param year
     * @return
     */
    Map<Integer, Map<String,Long>> getMemberByProAndYear(List<Integer> projectIds, Integer year);
}
