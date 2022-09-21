package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.UserDept;
import com.xxl.job.executor.models.userdept.UserDeptModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserDeptDao extends BaseMapper<UserDept> {
    /**
     * 获取用户dept
     * @param userIds
     * @return
     */
    List<UserDeptModel> getUserDept(@Param("userIds") Set<Integer> userIds);

    /**
     * 获取部门管理员
     * @param deptIds
     * @return
     */
    List<UserDeptModel> getAdminUser(@Param("deptIds") Set<Integer> deptIds);
}
