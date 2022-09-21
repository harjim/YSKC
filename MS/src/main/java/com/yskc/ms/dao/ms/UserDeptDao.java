package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.UserDept;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.models.user.UserDeptModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDeptDao extends BaseMapper<UserDept> {
    void deleteAll();

    void deleteByUserId(@Param("userId") Integer userId);

    void deleteByUserIds(@Param("userIds") List<Integer> userIds, @Param("deptIds") List<Integer> deptIds);

    @Options(useGeneratedKeys = true)
    int insertBatch(List<UserDept> userDepts);

    Boolean updateByUserIds(@Param("userIds") List<Integer> userIds,  @Param("deptId")Integer deptId,  @Param("isAdmin")Integer isAdmin);

    List<Integer> getPrincipal(@Param("deptId")Integer deptId);

    List<MiniUserModel> getUserByDeptId(@Param("deptId")Integer deptId);
    /**
     * 获取用户部门全路径
     *
     * @param userId
     * @return
     */
    List<String> getByUserId(@Param("userId") Integer userId);

    void deleteByUserIdAndDeptIds(@Param("userId") Integer userId, @Param("delIds") List<Integer> delIds);

    List<UserDeptModel> getByUserIds(@Param("userIds") List<Integer> userIds, @Param("fullPath") String fullPath);

    /**
     * 通过全路径查询用户id
     *
     * @param allFullPath
     * @return
     */
    List<Integer> getUserIdByFullPath(@Param("allFullPath") List<String> allFullPath);

    /**
     * 批量删除
     *
     * @param userIds
     * @param deptId
     * @return
     */
    boolean deleteDeptUser(@Param("userIds") List<Integer> userIds, @Param("deptId") Integer deptId);

    /**
     * 获取用户部门id
     *
     * @param userId
     * @return
     */
    Integer getUserDeptId(@Param("userId") Integer userId);

    Integer updateDDUserDept(@Param("retainIds") List<Integer> retainIds,@Param("list") List<UserDept> list);

    /**
     * 获取现有的钉钉用户部门数据
     * @return
     */
    List<UserDept> getDDUserDept();

    /**
     * 获取用户主管userIds
     * @param userIds
     * @return
     */
    List<Integer> getDeptManager(@Param("userIds") List<Integer> userIds);

    void updateDDUserAdmin(@Param("userIds")List<Integer> userIds, @Param("list")List<UserDept> adminUserDeptList);
}
