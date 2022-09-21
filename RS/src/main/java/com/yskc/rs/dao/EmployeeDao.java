package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.employee.EmployeeSelectTableModel;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.init.member.QueryInitMemberModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-09 10:13:52
 */
@Repository
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {

    /**
     * 获取花名册信息
     *
     * @param companyId
     * @param page
     * @param termModel
     * @return
     */
    List<EmployeeModel> queryEmployeeList(@Param("companyId") Integer companyId, @Param("page") Pagination page, @Param("termModel") EmployeeTermModel termModel);

    /**
     * 查询工号的唯一性
     *
     * @param companyId
     * @param enumber
     * @return
     */
    EmployeeModel getEmployeeByenumber(@Param("companyId") int companyId, @Param("enumber") String enumber);

    /**
     * 获取所有员工信息
     *
     * @param companyId
     * @param enumbers
     * @param year
     * @return
     */
    List<EmployeeModel> getListByeEnumbers(@Param("companyId") Integer companyId, @Param("enumbers") List<String> enumbers, @Param("year") Integer year);

    /**
     * 获取所有员工信息
     *
     * @param companyId
     * @return
     */
    List<EmployeeModel> getAllList(@Param("companyId") Integer companyId);

    /**
     * 导出报表
     *
     * @param companyId
     * @param termModel
     * @return
     */
    List<EmployeeExcel> getData(@Param("companyId") Integer companyId, @Param("termModel") EmployeeTermModel termModel);

    /**
     * 根据员工编码获取员工信息
     *
     * @param companyId
     * @param enumber
     * @return
     */
    EmployeeEntity getByNumber(@Param("companyId") int companyId, @Param("enumber") String enumber);

    /**
     * 根据员工考勤获取员工列表
     *
     * @param companyId
     * @param month
     * @param ename
     * @param page
     * @return
     */
    List<EmployeeModel> getEmployeeListByMonth(@Param("companyId") int companyId, @Param("month") Date month, @Param("ename") String ename, @Param("page") Pagination page);

    /**
     * 根据员工编号列表获取员工信息
     *
     * @param companyId
     * @param enumbers
     * @return
     */
    List<EmployeeEntity> getByNumbers(@Param("companyId") int companyId, @Param("enumbers") List<String> enumbers);


    /**
     * 批量插入
     *
     * @param employeeEntitys
     * @return
     */
    Integer addBatch(@Param("employeeEntitys") List<EmployeeEntity> employeeEntitys);

    /**
     * 批量修改
     *
     * @param employeeEntitys
     * @return
     */
    Integer updateBatch(@Param("employeeEntitys") List<EmployeeEntity> employeeEntitys);

    /**
     * 批量删除
     *
     * @param employeeIds
     * @return
     */
    //boolean delEmployeeBatch(@Param("employeeIds") List<Integer> employeeIds);

    /**
     * 人员下拉表格
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<EmployeeSelectTableModel> getSelectEmployeeList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryInitMemberModel query);


    /**
     * 根据条件获得人员信息
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeEntity> getEmployeeByTerm(@Param("companyId") Integer companyId, @Param("enames") List<String> ename);

    List<EmployeeEntity> selectByEname(@Param("ename") String ename);

    /**
     * 获取人员是否使用
     * 返回人员本身id
     *
     * @param tableName
     * @param companyId
     * @param ids
     * @return
     */
    List<Integer> getUsed(@Param("tableName") String tableName, @Param("companyId") int companyId, @Param("ids") List<Integer> ids);


    /**
     * 获取花名册信息
     *
     * @param companyId
     * @param page
     * @param termModel
     * @param firstDay
     * @return
     */
    List<EmployeeModel> selectEmployeeList(@Param("companyId") Integer companyId, Pagination page,
                                           @Param("termModel") EmployeeTermModel termModel, @Param("firstDay") Date firstDay);


    List<EmployeeModel> getEmployees(@Param("companyId") Integer companyId, Pagination page, @Param("termModel") EmployeeTermModel termModel);


    List<EmployeeModel> selectStEmployees(@Param("companyId") Integer companyId, Pagination page,
                                          @Param("termModel") EmployeeTermModel termModel,@Param("firstDay")Date firstDay);

    /**
     * 获取人员下拉
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getEmployeeSelect(@Param("companyId") Integer companyId, @Param("ename") String ename);

    /**
     * 通过工号获取名字
     *
     * @param companyId
     * @param enumbers
     * @return
     */
    List<EmployeeEntity> getEnameByEnumbers(@Param("companyId") Integer companyId, @Param("enumbers") Set<String> enumbers);

    /**
     * 通过工号获取名字，部门（优先研发部门）
     *
     * @param companyId
     * @param enumbers
     * @param year
     * @return
     */
    List<ProjectEmployeeInfo> getEmployeeByEnumbers(@Param("companyId") Integer companyId,@Param("year") Integer year,
                                                    @Param("enumbers") Set<String> enumbers);

    /**
     * 更新disabledAtt
     *
     * @param ids
     * @param disabledAtt
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateDisabledAtt(@Param("ids") List<Integer> ids, @Param("disabledAtt") Boolean disabledAtt,
                          @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("now") Date now);

    List<EmployeeModel> getEmployeeList(@Param("companyId") Integer companyId);

    List<HashMap<String, String>> getEmployee(@Param("list") Set<String> list, @Param("companyId") Integer companyId);

    /**
     * 更新签名地址
     *
     * @param id
     * @param autographUrl
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateAutographUrl(@Param("id") Integer id, @Param("autographUrl") String autographUrl,
                           @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("now") Date now);

    /**
     * 更新角色类型
     *
     * @param ids
     * @param roleType
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateRoleType(@Param("ids") List<Integer> ids, @Param("roleType") Integer roleType,
                       @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("now") Date now);
}
