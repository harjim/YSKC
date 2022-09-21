package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.OperationLogEntity;
import com.yskc.ms.models.user.OperationLogUserModel;
import com.yskc.ms.models.user.QueryUserListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-01 10:07
 * @Description: 操作日志（统计）dao层
 */
@Repository
public interface OperationLogDao extends BaseMapper<OperationLogEntity> {
    /**
     * 获取日志统计
     *
     * @param query
     * @param list
     * @return
     */
    List<OperationLogEntity> getOperationCnt(@Param("query") QueryUserListModel query, @Param("list") List<OperationLogUserModel> list);
}
