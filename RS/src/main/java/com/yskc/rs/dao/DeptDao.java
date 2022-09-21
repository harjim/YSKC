package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.models.CommonOrgModel;
import com.yskc.rs.models.dept.DeptModel;
import com.yskc.rs.models.dept.DeptTree;
import com.yskc.rs.models.dept.DeptUserInfo;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface DeptDao extends BaseMapper<Dept> {
    /**
     * 获取部门信息
     *
     * @param companyId
     * @param deptName
     * @return
     */
    List<DeptModel> queryDept(@Param("companyId") int companyId, @Param("deptName") String deptName);

    /**
     * 根据id获取部门信息
     *
     * @param companyId
     * @param tid
     * @return
     */
    DeptModel getNode(@Param("companyId") int companyId, @Param("tid") int tid);

    /**
     * 根据parentId查询子集
     *
     * @param companyId
     * @param tid
     * @param deptName
     * @return
     */
    List<DeptModel> queryNode(@Param("companyId") int companyId, @Param("tid") int tid, @Param("deptName") String deptName);

    /**
     * 查询所有部门
     *
     * @param companyId
     * @return
     */
    List<DeptTree> queryAll(@Param("companyId") Integer companyId);

    /**
     * 获取部门人员集合
     *
     * @param page
     * @param deptId
     * @param realName
     * @return
     */
    List<DeptUserInfo> getUser(@Param("page") Pagination page, @Param("deptId") Integer deptId, @Param("realName") String realName);

    /**
     * 获取部门信息
     *
     * @param companyId
     * @param deptName
     * @return
     */
    Dept selectDeptByName(@Param("companyId") int companyId, @Param("deptName") String deptName);

    /**
     * 获取公司的顶级部门
     *
     * @param companyId
     * @return
     */
    Dept selectParentDept(@Param("companyId") int companyId);

    /**
     * 部门名称查询部门
     *
     * @param companyId
     * @param deptNames
     * @return
     */
    List<Dept> getDeptByTerm(@Param("companyId") int companyId, @Param("deptNames") List<String> deptNames);

    /**
     * 根据部门名称列表获取部门
     *
     * @param companyId
     * @param deptNames
     * @return
     */
    List<Dept> selectByNames(@Param("companyId") int companyId, @Param("deptNames") List<String> deptNames);

    /**
     * 批量插入
     *
     * @param deptList
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addBatch(List<Dept> deptList);

    /**
     * 通过部门id查询部门
     *
     * @param companyId
     * @param ids
     * @return
     */
    List<String> queryDeptByIds(@Param("companyId") int companyId, @Param("ids") String[] ids);

    /**
     * 获取部门是否使用
     * 返回部门本身id
     *
     * @param deptId
     * @return
     */
    Integer getUsed(@Param("deptId") Integer deptId);


    /**
     * 获取所有子节点
     *
     * @param companyId
     * @param fullPath
     * @param id
     * @return
     */
    List<Dept> queryChildrenByTerm(@Param("companyId") Integer companyId, @Param("fullPath") String fullPath,@Param("id") Integer id);

    List<DeptTree> getChildByDeptList(@Param("deptList")List<Dept> deptList,@Param("companyId") Integer companyId);
    List<Dept> getDeptByDeptList(@Param("list")List<Integer> list,@Param("companyId") Integer companyId);
    /**
     * 批量更新
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<Dept> updateList);

    /**
     * 获取同级相同的部门
     *
     * @param companyId
     * @param parentId
     * @param deptName
     * @return
     */
    Dept getSameLevelDept(@Param("companyId") Integer companyId, @Param("parentId") int parentId, @Param("deptName") String deptName);

    /**
     * 查询是否存在子节点
     * @param companyId
     * @param parentId
     * @return
     */
    Integer getByParentId(@Param("companyId")Integer companyId, @Param("parentId")Integer parentId);

    List<Dept> queryByCompanyId(@Param("companyId")Integer companyId);

    /**
     * 获取公司部门组织
     * @param companyId
     * @return
     */
    List<CommonOrgModel> getCompanyOrg(@Param("companyId") Integer companyId);
}
