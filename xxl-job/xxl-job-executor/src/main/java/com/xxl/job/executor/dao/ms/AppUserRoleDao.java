package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.AppUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-16 10:39
 * @Description: 用户角色dao层
 */
@Repository
public interface AppUserRoleDao extends BaseMapper<AppUserRole> {


    /**
     * 通过角色id获取用户角色
     * @param roleIds
     * @return
     */
    List<AppUserRole> getByRoleIds(@Param("roleIds") Set<Integer> roleIds);
}
