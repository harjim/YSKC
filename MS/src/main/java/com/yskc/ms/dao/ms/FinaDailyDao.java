package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.FinaDailyEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.finaDaily.FinaDailyModel;
import com.yskc.ms.models.finaDaily.QueryDailyModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/dao/ms
 * @Author: hm
 * @CreateTime: 2022/9/6
 * @Description: 服务日志对应Dao层
 */
@Repository
public interface FinaDailyDao extends BaseMapper< FinaDailyEntity > {

    List< FinaDailyModel> getList(@Param("moduleId") Integer moduleId, @Param("page") Pagination page, @Param("query") QueryDailyModel query, @Param("userInfo") UserInfo userInfo, @Param("perm") DataPermModel dataPerm);

}
