package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.LogEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.log.QuerySysLogModel;
import com.yskc.ms.models.log.SysLogModel;
import com.yskc.ms.models.log.UserRsLogModel;
import com.yskc.ms.models.user.MiniUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-12-13 10:32:01
 */
@Repository
public interface LogDao extends BaseMapper<LogEntity> {
    /**
     * 获取rs所有日志信息
     *
     * @param page
     * @return
     */
    List<SysLogModel> queryRsLog(@Param("page") Pagination page, @Param("query") QuerySysLogModel query, @Param("perm") DataPermModel perm);

    /**
     * 根据姓名(用户名)查询
     *
     * @param userName
     * @return
     */
    List<MiniUserModel> getDataByRsUserName(@Param("userName") String userName);

    /**
     * 获取用户日志
     *
     * @param page
     * @param query
     * @return
     */
    List<UserRsLogModel> getUserLog(@Param("page") Pagination page, @Param("query") QuerySysLogModel query);
}
