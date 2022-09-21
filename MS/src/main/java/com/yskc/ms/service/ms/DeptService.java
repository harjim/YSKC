package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptModel;
import com.yskc.ms.models.dept.DeptTree;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.dept.InsertUserDeptModel;
import com.yskc.ms.models.user.QueryUserListModel;
import org.apache.ibatis.annotations.Param;

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
     * @param deptName
     * @return
     */
    List<DeptModel> queryDept(@Param("deptName") String deptName);

    /**
     * 查询所有部门
     *
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月4日 下午3:54:54
     */
    List<DeptTree> queryAll();

    /**
     * 获取部门人员集合
     *
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月5日 上午10:08:06
     */
    PageModel<List<DeptUserInfo>> getUser(QueryUserListModel query, DataPermModel perm);

    /**
     * 删除用户
     *
     * @param id
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月5日 下午2:29:39
     */
    Boolean delUser(Integer currentUserId, Integer id) throws OwnerException;

    /**
     * 删除部门
     *
     * @param deptmodel
     * @return
     * @throws OwnerException
     */
    boolean delDept(DeptModel deptmodel) throws OwnerException;

    /**
     * 修改部门
     *
     * @param deptmodel
     * @return
     * @throws OwnerException
     */
    boolean updateDept(DeptModel deptmodel) throws OwnerException;

    /**
     * 新增下级部门
     *
     * @param deptmodel
     * @return
     * @throws OwnerException
     */
    boolean addDept(DeptModel deptmodel) throws OwnerException;


	List<Integer> queryDeptIds();

    /**
     * 同步钉钉信息
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean synDingUser(UserInfo info) throws OwnerException;

    /**
     * 根据部门id查询用户
     * @param pageNo
     * @param pageSize
     * @param realName
     * @param deptId
     * @return
     */
    PageModel<List<DeptUserInfo>> getUserDataByDeptId(int pageNo,int pageSize,String realName,Integer deptId);

    /**
     * 查询不存在当前部门的用户
     * @param pageNo
     * @param pageSize
     * @param realName
     * @param deptId
     * @return
     */
    PageModel<List<DeptUserInfo>> getSelectUserData(int pageNo,int pageSize,String realName,Integer deptId,String fullPath);

    /**
     * 部门添加用户
     * @param model
     * @return
     */
    boolean deptAddUser(InsertUserDeptModel model) throws OwnerException;

    /**
     * 部门批量添加负责人
     * @param model
     * @return
     */
    boolean deptAddPrincipal(InsertUserDeptModel model,Boolean addTo) throws OwnerException;

    /**
     * 批量删除部门下的用户
     * @param deptUserInfos
     * @return
     */
    boolean deleteDeptUser(List<DeptUserInfo> deptUserInfos);
}
