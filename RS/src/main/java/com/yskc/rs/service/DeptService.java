package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.dept.DeptModel;
import com.yskc.rs.models.dept.DeptTree;
import com.yskc.rs.models.dept.DeptUserInfo;
import com.yskc.rs.models.excel.DeptExcel;

import java.util.List;

/**
 * 组织架构
 *
 * @author Administrator
 */
public interface DeptService {
    /**
     * 获取组织架构信息
     *
     * @param companyId
     * @param deptName
     * @return
     */
    List<DeptModel> queryDept(int companyId, String deptName);

    /**
     * 查询所有部门
     *
     * @param userInfo
     * @return
     */
    List<DeptTree> queryAll(UserInfo userInfo);

    /**
     * 获取部门人员集合
     *
     * @param deptId
     * @param realName
     * @param page
     * @param rows
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月5日 上午10:08:06
     */
    PageModel<List<DeptUserInfo>> getUser(Integer deptId, String realName, Integer page, Integer rows);

    /**
     * 删除用户
     *
     * @param id
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月5日 下午2:29:39
     */
    Boolean delUser(Integer id);

    /**
     * 删除部门
     *
     * @param id
     * @param companyId
     * @return
     * @throws OwnerException
     */
    boolean delDept(Integer companyId, Integer id) throws OwnerException;

    /**
     * 修改部门
     *
     * @param userInfo
     * @param deptmodel
     * @return
     * @throws OwnerException
     */

    boolean updateDept(UserInfo userInfo, DeptModel deptmodel) throws OwnerException;

    /**
     * 新增下级部门
     *
     * @param userInfo
     * @param deptmodel
     * @return
     * @throws OwnerException
     */
    boolean addDept(UserInfo userInfo, DeptModel deptmodel) throws OwnerException;

    String importDept(UserInfo info, List<DeptExcel> data) throws OwnerException;
}
