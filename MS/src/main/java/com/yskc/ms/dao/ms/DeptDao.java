package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.Dept;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.dept.DeptModel;
import com.yskc.ms.models.dept.DeptTree;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.user.QueryUserListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface DeptDao extends BaseMapper<Dept> {
    /**
     * 获取组织架构信息
     *
     * @param deptName
     * @return
     */
    List<DeptModel> queryDept(@Param("deptName") String deptName);

    /**
     * 查询所有部门
     *
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月4日 下午3:54:18
     */
    List<DeptTree> queryAll();

    /**
     * 获取部门人员集合
     *
     * @param page
     * @return
     */
    List<DeptUserInfo> getUser(@Param("page") Pagination page, @Param("query") QueryUserListModel query, @Param("perm") DataPermModel perm);

    /**
     * @param deptList
     * @return
     */
    int insertBatch(@Param("deptList") List<Dept> deptList);

    /**
     * 批量更新
     * @param updateBatchList
     * @return
     */
    int updateBatch(@Param("deptList") Collection<Dept> updateBatchList);

    int updateDDDept(@Param("deptList") Collection<Dept> updateBatchList);

    List<Integer> queryDeptIds();

    /**
     * 获取部门是否使用
     * 返回部门本身id
     *
     * @param deptId
     * @return
     */
    Integer getUsed(@Param("deptId") Integer deptId);

    /**
     * 查询是否有子节点
     *
     * @param parentId
     * @return
     */
    int getByParentId(@Param("parentId") Integer parentId);

    /**
     * @param ids
     * @return
     */
    List<DeptModel> getDataByDeptId(@Param("ids") List<Integer> ids);

    /**
     * count
     * @param parentId
     * @param deptName
     * @return
     */
    Dept getExist(@Param("parentId") int parentId,@Param("deptName") String deptName);

    /**
     * 全路径获取子节点
     * @param fullPath
     * @param id
     * @return
     */
    List<Dept> getChildren(@Param("fullPath") String fullPath,@Param("id") Integer id);

    /**
     * 根据部门ID查询该部门下的用户
     * @param page
     * @param realName
     * @param deptId
     * @return
     */
    List<DeptUserInfo> getUserDataByDeptId(@Param("page") Pagination page,@Param("realName") String realName,@Param("deptId") Integer deptId);

    /**
     * 获取不存在当前部门的用户
     * @param page
     * @param realName
     * @param deptId
     * @return
     */
    List<DeptUserInfo> getSelectUserData(@Param("page")Pagination page,@Param("realName") String realName,@Param("deptId") Integer deptId,@Param("fullPath")String fullPath);

    /**
     * 查询用户拥有的部门全路径集合
     * @param userId
     * @return
     */
    @Deprecated
    List<String> queryDeptByUserId(@Param("userId")Integer userId);

    /**
     * 根据用户获取所属部门信息
     * @param userId
     * @return
     */
    List<DeptModel> getDeptByUserId(@Param("userId")Integer userId);

    /**
     * 通过层获取
     * @param level
     * @return
     */
    List<Dept> getGeLevel(@Param("level") Integer level);

    /**
     * 获取部门信息
     * @param ids
     * @return
     */
    List<Dept> getByDeptIds(@Param("ids") List<Integer> ids);

    /**
     * 获取集团所有部门
     * @return
     */
    List<GroupRAndDManagementModel> getAllDept();
}
