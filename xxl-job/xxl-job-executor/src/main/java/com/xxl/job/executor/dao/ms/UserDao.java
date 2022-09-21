package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.UserEntity;
import com.xxl.job.executor.models.flowInstance.AuditUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 15:13
 * @Description: 用户Dao层
 */
@Repository
public interface UserDao extends BaseMapper<UserEntity> {
    /**
     * 通过id获取人员
     *
     * @param userIds
     * @return
     */
    List<AuditUserModel> getByUserIds(@Param("userIds") Set<Integer> userIds);

    /**
     * 更新rs最后操作时间
     * @param list
     * @return
     */
    int updateRsLastOperationTime(@Param("list") List<UserEntity> list);
}
