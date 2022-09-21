package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.SysLog;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.QueryContractTraceabilityModel;
import com.yskc.ms.models.log.QuerySysLogModel;
import com.yskc.ms.models.log.SysLogModel;
import com.yskc.ms.models.user.MiniUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-12 16:28
 * @Description: 日志dao层
 */
@Repository
public interface SysLogDao extends BaseMapper<SysLog> {
    /**
     * 查询所有的日志信息
     * @param page
     * @return
     */
    List<SysLogModel> querySysLog(@Param("page") Pagination page, @Param("query") QuerySysLogModel query, @Param("perm") DataPermModel perm);


    /**
     * 根据姓名(用户名)查询
     * @param userName
     * @return
     */
    List<MiniUserModel> getDataByUserName(@Param("userName") String userName);

}
