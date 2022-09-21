package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.EsLog;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.log.QuerySysLogModel;
import com.yskc.ms.models.log.SysLogModel;
import com.yskc.ms.models.user.MiniUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/10/27 10:25
 * @Description:
 * @author: hsx
 */
@Repository
public interface EsLogDao extends BaseMapper<EsLog> {

    /**
     * 获取es所有日志信息
     * @param page
     * @param query
     * @param perm
     * @return
     */
    List<SysLogModel> queryEsLog(@Param("page") Pagination page, @Param("query") QuerySysLogModel query, @Param("perm") DataPermModel perm);

    /**
     * 根据姓名(用户名)查询
     *
     * @param userName
     * @return
     */
    List<MiniUserModel> getDataByUserName (@Param("userName") String userName);
}
